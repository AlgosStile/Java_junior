package org.example.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart <T extends Food> {

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    public Cart(Class<T> clazz, UMarket market) {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs() {
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> {
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                    index.getAndIncrement(), food.getName(),
                    food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет",
                    food.getCarbohydrates() ? "Да" : "Нет");
        });
    }

    /**
     * Балансировка корзины
     */
    public void cardBalancing() {

        final boolean[] proteins = {foodstuffs.stream().anyMatch(Food::getProteins)};
        final boolean[] fats = {foodstuffs.stream().anyMatch(Food::getFats)};
        final boolean[] carbohydrates = {foodstuffs.stream().anyMatch(Food::getCarbohydrates)};

        if (proteins[0] && fats[0] && carbohydrates[0]) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        market.getThings(clazz).stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .filter(food -> (!proteins[0] && food.getProteins()) ||
                        (!fats[0] && food.getFats()) ||
                        (!carbohydrates[0] && food.getCarbohydrates()))
                .limit(3) // только 3, т.к.у нас всего 3 (Б/Ж/У)
                .forEach(food -> {
                    if (!proteins[0] && food.getProteins()) proteins[0] = true;
                    if (!fats[0] && food.getFats()) fats[0] = true;
                    if (!carbohydrates[0] && food.getCarbohydrates()) carbohydrates[0] = true;
                    foodstuffs.add(food);
                });

        String balanceMessage = (proteins[0] && fats[0] && carbohydrates[0]) ?
                "Корзина сбалансирована по БЖУ." :
                "Невозможно сбалансировать корзину по БЖУ.";
        System.out.println(balanceMessage);
    }
}
