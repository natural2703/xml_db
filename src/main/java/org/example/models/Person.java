package org.example.models;


import org.example.Type;

import java.util.Objects;

public class Person {
    private String personId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String pesel;
    private Type type;

    public Person() {
    }

    public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel, Type type) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
        this.type = type;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", pesel='" + pesel + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return personId.equals(person.personId) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(mobile, person.mobile) && Objects.equals(email, person.email) && Objects.equals(pesel, person.pesel) && type == person.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, mobile, email, pesel, type);
    }
}
