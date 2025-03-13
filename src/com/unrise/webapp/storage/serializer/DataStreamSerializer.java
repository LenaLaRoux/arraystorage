package com.unrise.webapp.storage.serializer;

import com.unrise.webapp.exception.StorageException;
import com.unrise.webapp.model.*;
import com.unrise.webapp.storage.IDataStreamConsumer;
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
            writeStr(dos, r.getUuid());
            writeStr(dos, r.getFullName());
            Map<String, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writeStr(dos, entry.getKey());
                writeStr(dos, entry.getValue());
            }

            for (SectionType sectionType : SectionType.values()) {

                ASection section = r.getSection(sectionType);
                if (section == null)
                    continue;

                writeStr(dos, sectionType.name());

                switch (section) {
                    case ListSection listSection -> writeStr(dos, listSection.get());
                    case TextList list -> writeSectionList(dos, list.get(), dos::writeUTF);
                    case CompanySection cSections -> writeSectionList(dos, cSections.getCompanies(), c -> {
                        writeStr(dos, c.getName());
                        writeStr(dos, c.getWebsite());
                        writeSectionList(dos, c.getPeriods(), p -> {
                            writeDateToLong(dos, p.getDateFrom());
                            writeDateToLong(dos, p.getDateTo());
                            writeStr(dos, p.getRole());
                            writeStr(dos, p.getDescription());
                        });
                    });
                    default -> throw new StorageException("Unexpected value: " + section.get());
                }
            }
        }
    }

    private <T> void writeSectionList(DataOutputStream dos, List<T> listItem, IDataStreamConsumer<T> writer) throws IOException {
        dos.writeInt(listItem.size());
        listItem.forEach(writer::acceptWrapper);
    }

    private void writeDateToLong(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeLong(ld == null ? 0 : TimeUnit.DAYS.toMillis(ld.toEpochDay()));
    }

    private void writeStr(DataOutputStream dos, String str) throws IOException {
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
                                    LocalDate dateStart = readLongToDate(dis.readLong());
                                    LocalDate dateEnd = readLongToDate(dis.readLong());
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

    public static LocalDate readLongToDate(long millis) {
        if (millis == 0)
            return null;
        Instant instant = Instant.ofEpochMilli(millis);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String readStr(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return StringUtils.isBlank(str) ? null : str;
    }

    private <T> void readSectionList(DataInputStream dis, Resume resume, SectionType type, ASection section, Callable<T> callable) throws Exception {
        resume.addSection(type, section);
        List<T> l = (List<T>) section.get();
        readList(dis, l, callable);
    }

    private <T> void readList(DataInputStream dis, List<T> list, Callable<T> callable) throws Exception {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            list.add(callable.call());
        }
    }
}
