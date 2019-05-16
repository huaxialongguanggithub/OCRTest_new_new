package com.lx;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Person {

    @JSONField(name = "AGE")
    private int age;

    @JSONField(name = "FULL NAME")
    private String fullName;

    @JSONField(name = "DATE OF BIRTH")
    private Date dateOfBirth;

    public Person(int age, String fullName, Date dateOfBirth) {
        super();
        this.age = age;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person||" +
                "age=" + age +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
    // 标准 getters & setters
}

