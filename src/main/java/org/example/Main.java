package org.example;

import org.example.models.Person;

public class Main {
    public static void main(String[] args) {
        DbService dbService = new DbService("internal", "external");
        dbService.getAllPersons();
        Person person = new Person("1", "John", "Doe", "123456789", "john.doe@example.com", "12345678901", Type.EXTERNAL);
        dbService.add(person);
        dbService.delete(person);
    }
}