package org.example.task1;

import java.util.Arrays;
import java.util.List;

public class MainStream {
    /**
     * Точка входа в программу, где выполняется вычисление среднего значения четных чисел.
     * Использует Stream API для фильтрации четных чисел, преобразования их в double и
     * вычисления среднего значения.
     *
     * @param args Массив строковых аргументов командной строки (не используются).
     */
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /**
         * Вычисление среднего значения четных чисел
         * 1. Создание потока данных из списка (stream()).
         * 2. Фильтрация потока для выборки только четных чисел (filter(num -> num % 2 == 0)).
         * 3. Преобразование элементов в double (mapToDouble(Integer::doubleValue)).
         * 4. Вычисление среднего значения элементов потока (average()).
         * 5. Если поток не содержит элементов, возвращается 0 (orElse(0)).
         */
        double average = numbers.stream()
                .filter(num -> num % 2 == 0)
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);

        System.out.println("Среднее значение четных чисел: " + average);
    }
}

