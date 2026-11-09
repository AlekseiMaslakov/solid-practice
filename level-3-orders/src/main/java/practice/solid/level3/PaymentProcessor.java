package practice.solid.level3;

public abstract class PaymentProcessor {

    /** @return true, если оплата прошла */
    public abstract boolean charge(String account, int amount);

    public abstract void refund(String account, int amount);
}
