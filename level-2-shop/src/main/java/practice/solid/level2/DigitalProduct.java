package practice.solid.level2;

/**
 * Электронная книга, подписка, ключ активации и т.п.
 */
public class DigitalProduct extends Product {

    public DigitalProduct(String name, int price) {
        super(name, price, 0);
    }

    @Override
    public double getWeight() {
        throw new UnsupportedOperationException("У цифрового товара нет веса");
    }
}
