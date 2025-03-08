package com.unrise.webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    // Unique identifier
    private String uuid;
    private String fullName;

    private Map<String, String> contacts = new HashMap<>();
    private Map<SectionType, ASection> sections = new HashMap<>();


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

    public Map<String, String> getContacts() {
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

    private Map<SectionType, ASection> getSections() {
        return sections;
    }

    public Map<SectionType, ASection> getSectionMap() {
        return new HashMap<>(sections);
    }

    private void setSections(Map<SectionType, ASection> sections) {
        this.sections = sections;
    }

    public void addSection(SectionType sectionType, ASection value) {
        sections.put(sectionType, value);
    }

    public ASection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    @Override
    public int compareTo(Resume o) {
        if (o == null)
            return 1;
        int cmp = fullName == o.fullName ? 0 :
                fullName == null ? 1 :
                        o.fullName == null ? -1 :
                                fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
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
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
