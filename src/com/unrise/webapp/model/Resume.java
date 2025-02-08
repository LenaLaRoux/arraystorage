package com.unrise.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private String fullName;

    private Map<String, String> contacts = new HashMap<>();
    private Map<SectionType, ISection<?>> sections = new HashMap<>();


    public Resume() {
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this(uuid);
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private Map<String, String> getContacts() {
        return contacts;
    }

    public Map<String, String> getContactMap() {
        return new HashMap<>(contacts);
    }

    private void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public void addContact(String contactType, String value) {
        contacts.put(contactType, value);
    }

    public String getContact(String contactType) {
        return contacts.get(contactType);
    }

    private Map<SectionType, ISection<?>> getSections() {
        return sections;
    }

    public Map<SectionType, ISection<?>> getSectionMap() {
        return new HashMap<>(sections);
    }

    private void setSections(Map<SectionType, ISection<?>> sections) {
        this.sections = sections;
    }

    public void addSection(SectionType sectionType, ISection<?> value) {
        sections.put(sectionType, value);
    }

    public ISection<?> getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    @Override
    public int compareTo(Resume o) {
        if (o == null)
            return 1;
        return uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }
}
