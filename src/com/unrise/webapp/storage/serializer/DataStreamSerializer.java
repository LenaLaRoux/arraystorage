package com.unrise.webapp.storage.serializer;

import com.unrise.webapp.model.Resume;
import com.unrise.webapp.storage.IStreamSerializer;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements IStreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<String, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(dis.readUTF(), dis.readUTF());
            }
            // TODO implements sections
            return resume;
        }
    }
}
