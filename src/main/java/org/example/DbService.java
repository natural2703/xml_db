package org.example;

import org.example.models.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DbService {
    private final String internalDirectoryPath;
    private final String externalDirectoryPath;

    public DbService(String internalDirectoryPath, String externalDirectoryPath) {
        this.internalDirectoryPath = this.getResourcePath(internalDirectoryPath);
        this.externalDirectoryPath = this.getResourcePath(externalDirectoryPath);
    }
    public List<Person> getAllPersons() {
        List<Person> allPersons = new ArrayList<>();
        allPersons.addAll(getPersonsFromDirectory(internalDirectoryPath));
        allPersons.addAll(getPersonsFromDirectory(externalDirectoryPath));
        return allPersons;
    }
    private String getResourcePath(String directoryName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(directoryName);
        if (resource == null) {
            throw new IllegalArgumentException("Katalog nie został znaleziony: " + directoryName);
        } else {
            return new File(resource.getFile()).getPath();
        }
    }
    private List<Person> getPersonsFromDirectory(String directoryPath) {
        List<Person> persons = new ArrayList<>();
        File directory = new File(directoryPath);
       // System.out.println(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist: " + directoryPath);
            return persons;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("No files found in directory: " + directoryPath);
            return persons;
        }
        //System.out.println(files);
        for (File file : files) {
            if (file.isFile()) {
                Person person = parseXMLFile(file);
                persons.add(person);
            }
        }
        return persons;
    }
    private Person parseXMLFile(File file) {
        List<Person> persons = new ArrayList<>();
        System.out.println(file.getName());

        try {
            //File inputFile = new File("employees.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("employee");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String personId = eElement.getElementsByTagName("personId").item(0).getTextContent();
                    String firstName = eElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = eElement.getElementsByTagName("lastName").item(0).getTextContent();
                    String mobile = eElement.getElementsByTagName("mobile").item(0).getTextContent();
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    String pesel = eElement.getElementsByTagName("pesel").item(0).getTextContent();
                    Person person = new Person(personId, firstName, lastName, mobile, email, pesel, Type.INTERNAL);
                    return person;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean delete(Person person) {
        String personId = person.getPersonId();
        String directoryPath = getDirectoryPath(person.getType());

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Katalog nie istnieje: " + directoryPath);
            return false;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Brak plików w katalogu: " + directoryPath);
            return false;
        }
        //System.out.println(files);
        for (File file : files) {
            System.out.println(file.getName());
            if (file.isFile() && file.getName().contains(personId)) {
                if (file.delete()) {
                    System.out.println("Usunięto plik: " + file.getName());
                    return true;
                } else {
                    System.out.println("Nie udało się usunąć pliku: " + file.getName());
                    return false;
                }
            }
        }

        System.out.println("Nie znaleziono pliku dla osoby o ID: " + personId);
        return false;
    }

    private String getDirectoryPath(Type type) {
        if (type == Type.INTERNAL) {
            return internalDirectoryPath;
        } else {
            return externalDirectoryPath;
        }
    }

}
