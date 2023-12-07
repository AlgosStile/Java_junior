package org.example.task5.task1;

import java.io.*;

/**
 * 1.
 * ========
 *
 * Создайте класс UserData с полями String name, int age, transient String password.
 * Поле password должно быть отмечено ключевым словом transient.
 * Реализуйте интерфейс Serializable в вашем классе.
 * В методе main создайте экземпляр класса UserData и инициализируйте его данными.
 * Сериализуйте этот объект в файл, используя ObjectOutputStream в сочетании с FileOutputStream
 *
 * 2.
 * ========
 *
 * Десериализуйте объект из ранее созданного файла обратно в объект Java,
 * используя ObjectInputStream в сочетании с FileInputStream.
 * Выведите данные десериализованного объекта UserData.
 * Сравните данные до сериализации и после десериализации, особенно обратите внимание на поле,
 * помеченное как transient.
 * Обсудите, почему это поле не было сохранено после десериализации.
 */
public class Program {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserData user = new UserData("Олег", 39, "secret_password");

        try(FileOutputStream fileOutputStream = new FileOutputStream("userdata.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(user);
            System.out.println("Объект UserData сериализован.");
        }

        try(FileInputStream fileInputStream = new FileInputStream("userdata.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            user = (UserData)objectInputStream.readObject();
            System.out.println("Объект UserData десериализован.");
        }

        System.out.println("Объект UserData десериализован.");
        System.out.println("Имя: " + user.getName());
        System.out.println("Возраст: " + user.getAge());
        System.out.println("Пароль (должен быть null, так как transient): " + user.getPassword());



    }

}
