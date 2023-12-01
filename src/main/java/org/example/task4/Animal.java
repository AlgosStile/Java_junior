package org.example.task4;

public abstract class Animal {
    String name;
    int age;

    //Здесь добавил 2 поля в задании этого не было
    //Чуть усложнил код
    String breed;
    boolean isIndoor;

    public Animal(String name, int age, String breed, boolean isIndoor) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.isIndoor = isIndoor;

    }
    public abstract void makeSound();

}
