package practice.solid.level3;

import java.util.HashMap;
import java.util.Map;

/**
 * Склад: цены и остатки товаров.
 */
public class Inventory {

    private static Inventory instance;

    private final Map<String, Integer> prices = new HashMap<>();
    private final Map<String, Integer> stock = new HashMap<>();

    private Inventory() {
        reset();
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    /** Восстанавливает начальные остатки. Нужно для тестов. */
    public void reset() {
        prices.clear();
        stock.clear();
        add("BOOK-1", 1200, 10);
        add("PEN-1", 50, 100);
        add("LAMP-1", 3500, 3);
        add("CHAIR-1", 9000, 2);
    }

    private void add(String sku, int price, int quantity) {
        prices.put(sku, price);
        stock.put(sku, quantity);
    }

    public Integer getPrice(String sku) {
        return prices.get(sku);
    }

    public int getStock(String sku) {
        return stock.getOrDefault(sku, 0);
    }

    public void setStock(String sku, int quantity) {
        stock.put(sku, quantity);
    }
}
