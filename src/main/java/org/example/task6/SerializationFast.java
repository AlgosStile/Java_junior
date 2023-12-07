package org.example.task6;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class SerializationFast {

    public static void main(String[] args) {
        try {
            Student student = new Student();
            student.name = "Oleg";
            student.age = 39;
            student.GPA = 7.5;

            serializXml(student, "student.xml");
            serializJson(student, "student.json");

            Student deserializedXml = deserializedXml();
            System.out.println("Десериализация из Xml = " + deserializedXml.getName() + ", Age: " + deserializedXml.getAge() + ", GPA: " + deserializedXml.getGPA());

            Student deserializedJson = deserializedJson();
            System.out.println("Десериализация из Json = " + deserializedJson.getName() + ", Age: " + deserializedJson.getAge() + ", GPA: " + deserializedJson.getGPA());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void serializXml(Student student, String fileName) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File(fileName), student);
            System.out.println("Сериализация в Xml выполнена успешно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void serializJson(Student student, String fileName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(fileName), student);
            System.out.println("Сериализация в Json выполнена успешно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Student deserializedXml() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(new File("student.xml"), Student.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Student deserializedJson() throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File("student.json"), Student.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

