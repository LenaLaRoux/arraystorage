package com.unrise.webapp.storage;

import com.unrise.webapp.model.*;
import com.unrise.webapp.storage.utils.ResumeTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Month;

public class ResumeDataTest {

    @Test
    public void testData() {
        Resume resume = new Resume("uuid1", "Григорий Кислин");
        resume.addContact("Тел.", "+7(921) 855-0482");
        resume.addContact("Skype", "skype:grigory.kislin");
        resume.addContact("Почта", "gkislin@yandex.ru");

        resume.addSection(SectionType.OBJECTIVE, new ListSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));

        resume.addSection(SectionType.PERSONAL, new ListSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new TextList()
                .add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет")
                .add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.")
                .add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.")
                .add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));

        resume.addSection(SectionType.QUALIFICATIONS, new TextList()
                .add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2")
                .add("Version control: Subversion, Git, Mercury, ClearCase, Perforce")
                .add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB")
                .add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy")
                .add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts"));


        resume.addSection(SectionType.EXPERIENCE, new CompanySection()
                .add(ResumeTestUtils.createParticipant(2013, Month.OCTOBER, "Java Online Projects", null, "Создание, организация и проведение Java онлайн проектов и стажировок.", "Автор проекта."))
                .add(ResumeTestUtils.createParticipant(2014, 2016, Month.OCTOBER, Month.JANUARY, "Wrike", null, "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", "Старший разработчик (backend)"))
                .add(ResumeTestUtils.createParticipant(2012, 2014, Month.APRIL, Month.OCTOBER, "RIT Center", null, "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", "Java архитектор"))
        );

        resume.addSection(SectionType.EDUCATION, new CompanySection()
                .add(ResumeTestUtils.createParticipant(2013, 2013, Month.MARCH, Month.MAY, "Coursera", null, "'Functional Programming Principles in Scala' by Martin Odersky", null))
                .add(ResumeTestUtils.createParticipant(2011, 2011, Month.MARCH, Month.APRIL, "Luxoft", null, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null)));

        Assertions.assertEquals("+7(921) 855-0482", resume.getContact("Тел."));
        Assertions.assertEquals("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.", resume.getSection(SectionType.OBJECTIVE).get());

        TextList section = (TextList) resume.getSection(SectionType.ACHIEVEMENT);
        Assertions.assertEquals(4, section.get().size());
        section = (TextList) resume.getSection(SectionType.QUALIFICATIONS);
        Assertions.assertEquals(5, section.get().size());
        CompanySection partySection = (CompanySection) resume.getSection(SectionType.EXPERIENCE);
        Assertions.assertEquals(3, partySection.get().size());
        partySection = (CompanySection) resume.getSection(SectionType.EDUCATION);
        Assertions.assertEquals(2, partySection.get().size());
    }
}
