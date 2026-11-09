package practice.solid.level3;

/**
 * Оплата наличными при получении.
 */
public class CashProcessor extends PaymentProcessor {

    @Override
    public boolean charge(String account, int amount) {
        return true; // деньги получит курьер
    }

    @Override
    public void refund(String account, int amount) {
        throw new UnsupportedOperationException("Наличные возвращаются только в магазине");
    }
}
