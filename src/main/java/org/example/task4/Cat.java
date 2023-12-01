package org.example.task4;

class Cat extends Animal {

    public Cat(String name, int age, boolean isIndoor, String breed) {
        super(name, age, breed, isIndoor);
    }

    public void makeSound() {
        System.out.println("Мур-мяу \uD83D\uDC31");
    }

    public void scratch() {
        System.out.println("Цап-царап...: Oй, поцарапала");
    }
}