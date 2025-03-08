package com.unrise.webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ASection implements Serializable {
    public abstract Object get();
}
