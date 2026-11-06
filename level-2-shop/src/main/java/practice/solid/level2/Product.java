package practice.solid.level2;

public class Product {

    private final String name;
    private final int price;
    private final double weight;

    public Product(String name, int price, double weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    /** Цена в рублях. */
    public int getPrice() {
        return price;
    }

    /** Вес в килограммах. */
    public double getWeight() {
        return weight;
    }
}
