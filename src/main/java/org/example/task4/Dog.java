package org.example.task4;

class Dog extends Animal {


    public Dog(String name, int age, String breed) {
        super(name, age, breed, true);
    }

    public void makeSound() {
        System.out.println("Гав-гав " + "\uD83D\uDC36");
    }

    public void biting() {
        System.out.println("Покусаю...");
    }
}