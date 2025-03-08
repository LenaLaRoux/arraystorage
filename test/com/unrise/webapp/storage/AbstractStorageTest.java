package com.unrise.webapp.storage;

import com.unrise.webapp.exception.ExistStorageException;
import com.unrise.webapp.exception.NotExistStorageException;
import com.unrise.webapp.model.*;
import com.unrise.webapp.storage.utils.ResumeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

abstract public class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\topjava\\storage");
    protected final Storage storage;
    protected static final String UUID1 = "uuid1";
    protected static final String UUID2 = "uuid2";
    protected static final String UUID2_FIO1 = "uuid2_fio1";
    protected static final String UUID2_FIO2 = "uuid2_fio2";
    protected static final String UUID3_FIO2 = "uuid3_fio2";
    protected static final String UUID3 = "uuid3";
    protected static final String UUID4 = "uuid4";
    protected static final String UUID5 = "uuid5";
    protected static final Resume r1 = new Resume(UUID1);
    protected static final Resume r2 = new Resume(UUID2);
    protected static final Resume r2Fio1 = new Resume(UUID2_FIO1, "Fio1");
    protected static final Resume r2Fio2 = new Resume(UUID2_FIO2, "Fio2");
    protected static final Resume r3Fio1 = new Resume(UUID3_FIO2, "Fio1");
    protected static final Resume r3 = new Resume(UUID3);
    protected static final Resume r4 = new Resume(UUID4);
    protected static final Resume r5 = new Resume(UUID5);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID2);
        resume.addContact("Тел.", "+7(921) 855-0482");
        resume.addContact("Skype", "skype:grigory.kislin");
        resume.addContact("Почта", "gkislin@yandex.ru");
        resume.addSection(SectionType.OBJECTIVE, new ListSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));
        resume.addSection(SectionType.PERSONAL, new ListSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSection(SectionType.ACHIEVEMENT, new TextList()
                .add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет")
                .add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников."));

        resume.addSection(SectionType.QUALIFICATIONS, new TextList()
                .add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")
                .add("Version control: Subversion, Git, Mercury, ClearCase, Perforce"));

        resume.addSection(SectionType.EXPERIENCE, new CompanySection()
                .add(ResumeTestUtils.createParticipant(2013, Month.OCTOBER, "Java Online Projects", null, "Создание, организация и проведение Java онлайн проектов и стажировок.", "Автор проекта."))
                .add(ResumeTestUtils.createParticipant(2014, 2016, Month.OCTOBER, Month.JANUARY, "Wrike", null, "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", "Старший разработчик (backend)"))
                .add(ResumeTestUtils.createParticipant(2012, 2014, Month.APRIL, Month.OCTOBER, "RIT Center", null, "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", "Java архитектор"))
        );

        resume.addSection(SectionType.EDUCATION, new CompanySection()
                .add(ResumeTestUtils.createParticipant(2013, 2013, Month.MARCH, Month.MAY, "Coursera", null, "'Functional Programming Principles in Scala' by Martin Odersky", null))
                .add(ResumeTestUtils.createParticipant(2011, 2011, Month.MARCH, Month.APRIL, "Luxoft", null, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null)));


        assertNotSame(resume, storage.get(UUID2));
        storage.update(resume);
        assertGet(resume);
    }

    void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test
    void updateNotExist() {
        Resume resume = new Resume("dummy");
        assertThrowsExactly(NotExistStorageException.class, () -> storage.update(resume));
    }

    @Test
    void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
        assertEquals(size, storage.getAll().length);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(storage.getAll(), new Resume[0]);
    }

    @Test
    void getAll() {
        assertSize(3);
        assertGet(r1);
        assertGet(r2);
        assertGet(r3);
    }

    @Test
    void get() {
        for (Resume r : storage.getAll()) {
            assertGet(r);
        }
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void save() {
        assertSize(3);

        storage.save(r5);
        assertGet(r5);
        assertSize(4);

        storage.save(r4);
        assertGet(r4);
        assertSize(5);

        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID1)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID2)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID3)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID4)));
        assertThrowsExactly(ExistStorageException.class, () -> storage.save(new Resume(UUID5)));
        assertSize(5);
    }

    @Test
    void delete() {
        assertSize(3);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete("dummy"));

        storage.delete(UUID1);
        assertSize(2);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID1));

        storage.delete(UUID2);
        assertSize(1);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID2));


        storage.delete(UUID3);
        assertSize(0);
        assertThrowsExactly(NotExistStorageException.class, () -> storage.get(UUID3));

        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID1));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID2));
        assertThrowsExactly(NotExistStorageException.class, () -> storage.delete(UUID3));
    }

    @Test
    void getAllSorted() {
        assertSize(3);
        storage.save(r3Fio1);
        storage.save(r5);
        storage.save(r2Fio1);
        storage.save(r2Fio2);
        storage.save(r4);
        Resume[] expected = {r2Fio1, r3Fio1, r2Fio2, r1, r2, r3, r4, r5};
        Resume[] sorted = storage.getAllSorted();
        assertArrayEquals(expected, sorted);
    }

}
