package com.unrise.webapp.storage;

import com.unrise.webapp.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

public class ResumeDataTest {

    @Test
    public void testData (){
        Resume resume = new Resume("uuid1", "Григорий Кислин");
        resume.addContact("Тел.", "+7(921) 855-0482");
        resume.addContact("Skype", "skype:grigory.kislin");
        resume.addContact("Почта", "gkislin@yandex.ru");

        resume.addSection(SectionType.OBJECTIVE, new Text("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));

        resume.addSection(SectionType.PERSONAL, new Text("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

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


        resume.addSection(SectionType.EXPERIENCE, new ParticipationList()
                .add( createParticipant(2013, Month.OCTOBER, "Java Online Projects", "Создание, организация и проведение Java онлайн проектов и стажировок.", "Автор проекта."))
                .add(createParticipant(2014, 2016, Month.OCTOBER,Month.JANUARY,"Wrike","Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.","Старший разработчик (backend)"))
                .add(createParticipant(2012,2014, Month.APRIL,Month.OCTOBER,"RIT Center","Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", "Java архитектор"))
        );

        resume.addSection(SectionType.EDUCATION, new ParticipationList()
                .add(createParticipant(2013, 2013, Month.MARCH, Month.MAY, "Coursera", "'Functional Programming Principles in Scala' by Martin Odersky", null))
                .add(createParticipant(2011, 2011, Month.MARCH, Month.APRIL, "Luxoft", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null)));

        Assertions.assertEquals("+7(921) 855-0482", resume.getContact("Тел."));
        Assertions.assertEquals("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.", resume.getSection(SectionType.OBJECTIVE).get());

        TextList section = (TextList) resume.getSection(SectionType.ACHIEVEMENT);
        Assertions.assertEquals(4, section.get().size());
        section = (TextList) resume.getSection(SectionType.QUALIFICATIONS);
        Assertions.assertEquals(5, section.get().size());
        ParticipationList partySection = (ParticipationList) resume.getSection(SectionType.EXPERIENCE);
        Assertions.assertEquals(3, partySection.get().size());
        partySection = (ParticipationList) resume.getSection(SectionType.EDUCATION);
        Assertions.assertEquals(2, partySection.get().size());
    }

    private Participation createParticipant (Integer yearFrom, Integer yearTo, Month monthFrom, Month monthTo, String companyName, String description, String role)
    {
        Participation participation;
        if (yearTo == null){
            participation = new Participation(LocalDate.of(yearFrom, monthFrom,1), companyName);
        }else{
            participation = new Participation(LocalDate.of(yearFrom, monthFrom,1),
                    LocalDate.of(yearTo, Optional.ofNullable(monthTo).orElse(Month.JANUARY),1),
                    companyName);
        }
        participation.setDescription(description);
        participation.setRole(role);
        return participation;
    }
    private Participation createParticipant (Integer yearFrom, Month monthFrom, String companyName, String description, String role)
    {
       return this.createParticipant(yearFrom, null, monthFrom, null, companyName,description,role);
    }
}
