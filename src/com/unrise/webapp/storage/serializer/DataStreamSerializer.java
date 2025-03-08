package com.unrise.webapp.storage.serializer;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.*;
import com.unrise.webapp.storage.IStreamSerializer;
import org.junit.platform.commons.util.StringUtils;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class DataStreamSerializer implements IStreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeString(dos, r.getUuid());
            writeString(dos, r.getFullName());
            Map<String, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writeString(dos, entry.getKey());
                writeString(dos, entry.getValue());
            }

            for (SectionType sectionType : SectionType.values()) {

                ASection section = r.getSection(sectionType);
                if (section == null)
                    continue;

                writeString(dos, sectionType.name());
                writeData(dos, section.get());
            }
        }
    }

    private void writeData(DataOutputStream dos, Object sectionObject) throws IOException {
        switch (sectionObject) {
            case String str -> writeString(dos, str);
            case List list -> {
                int count = list.size();
                dos.writeInt(count);
                for (Object o : list) {
                    writeData(dos, o);
                }
            }
            case Company c -> {
                writeString(dos, c.getName());
                writeString(dos, c.getWebsite());
                writeData(dos, c.getPeriods());
            }
            case Period p -> {
                writeDateToLong(dos, p.getDateFrom());
                writeDateToLong(dos, p.getDateTo());
                writeString(dos, p.getRole());
                writeString(dos, p.getDescription());
            }
            default -> throw new StorageException("Unexpected value: " + sectionObject);
        }
    }

    private void writeDateToLong(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeLong(ld == null ? 0 : TimeUnit.DAYS.toMillis(ld.toEpochDay()));
    }

    private void writeString(DataOutputStream dos, String str) throws IOException {
        dos.writeUTF(Optional.ofNullable(str).orElse(""));
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = readStr(dis);
            String fullName = readStr(dis);
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(readStr(dis), readStr(dis));
            }
            while (dis.available() > 0) {
                String sectionTypeStr = readStr(dis);
                SectionType sectionType = SectionType.valueOf(sectionTypeStr);
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new ListSection(readStr(dis)));

                    case ACHIEVEMENT, QUALIFICATIONS ->
                            readSectionList(dis, resume, sectionType, new TextList(), () -> readStr(dis));

                    case EDUCATION, EXPERIENCE ->
                            readSectionList(dis, resume, sectionType, new CompanySection(), () -> {
                                Company c = new Company(readStr(dis));
                                c.setWebsite(readStr(dis));

                                readList(dis, c.getPeriods(), () -> {
                                    LocalDate dateStart = millsToLocalDate(dis.readLong());
                                    LocalDate dateEnd = millsToLocalDate(dis.readLong());
                                    Period p = new Period(dateStart, dateEnd);
                                    p.setRole(readStr(dis));
                                    p.setDescription(readStr(dis));
                                    return p;
                                });
                                return c;
                            });

                    case null, default -> throw new StorageException("Unexpected value: " + sectionType);
                }
            }
            return resume;
        } catch (IOException e) {
            throw (e);
        } catch (Exception e) {
            throw new StorageException("Data serializer read error", e);
        }
    }

    public static LocalDate millsToLocalDate(long millis) {
        if (millis == 0)
            return null;
        Instant instant = Instant.ofEpochMilli(millis);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String readStr(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return StringUtils.isBlank(str) ? null : str;
    }

    private void readSectionList(DataInputStream dis, Resume resume, SectionType type, ASection section, Callable callable) throws Exception {
        resume.addSection(type, section);
        List l = (List) section.get();
        readList(dis, l, callable);
    }

    private void readList(DataInputStream dis, List list, Callable callable) throws Exception {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            list.add(callable.call());
        }
    }
}
