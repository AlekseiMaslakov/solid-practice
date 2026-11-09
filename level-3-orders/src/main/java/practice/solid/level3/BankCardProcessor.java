package practice.solid.level3;

import java.util.ArrayList;
import java.util.List;

/**
 * Оплата банковской картой. Вместо настоящего банка записывает операции в список.
 */
public class BankCardProcessor extends PaymentProcessor {

    public static final List<String> TRANSACTIONS = new ArrayList<>();

    @Override
    public boolean charge(String cardNumber, int amount) {
        System.out.println("Подключение к bank.example.com:443 ...");
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            return false;
        }
        if (cardNumber.startsWith("0000")) {
            return false; // банк отклонил операцию
        }
        TRANSACTIONS.add("CHARGE *" + cardNumber.substring(12) + " " + amount);
        return true;
    }

    @Override
    public void refund(String cardNumber, int amount) {
        System.out.println("Подключение к bank.example.com:443 ...");
        TRANSACTIONS.add("REFUND *" + cardNumber.substring(12) + " " + amount);
    }
}
