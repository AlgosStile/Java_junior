package org.example.task4;


import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Animal[] animals = new Animal[3];
        animals[0] = new Dog("Шарик", 3, "Лабрадор");
        animals[1] = new Cat("Барсик", 5, true, "Британская короткошёрстная");
        animals[2] = new Dog("Тузик", 2, "Пит-буль");

        System.out.println("Список животных: ");
        for (Animal animal : animals) {
            System.out.println("Кличка: " + animal.name + ", Возраст: " + animal.age + ", Вид: " + animal.getClass().getSimpleName() + ", Порода: " + animal.breed);
        }

        for (Animal animal : animals) {
            Class<?> animalClass = animal.getClass();
            try {
                Method makeSoundMethod = animalClass.getDeclaredMethod("makeSound");
                makeSoundMethod.invoke(animal);
                if (animal instanceof Dog) {
                    Method bitingMethod = animalClass.getDeclaredMethod("biting");
                    bitingMethod.invoke(animal);
                } else if (animal instanceof Cat) {
                    Method scratchMethod = animalClass.getDeclaredMethod("scratch");
                    scratchMethod.invoke(animal);
                }
            } catch (Exception e) {
                System.out.println("Method not found: " + e.getMessage());
            }
        }
    }
}