package practice.solid.level3;

import java.util.HashMap;
import java.util.Map;

/**
 * Оплата электронным кошельком.
 */
public class WalletProcessor extends PaymentProcessor {

    public static final Map<String, Integer> BALANCES = new HashMap<>();

    static {
        resetBalances();
    }

    /** Восстанавливает начальные балансы. Нужно для тестов. */
    public static void resetBalances() {
        BALANCES.clear();
        BALANCES.put("W-100", 50000);
        BALANCES.put("W-200", 1000);
    }

    @Override
    public boolean charge(String wallet, int amount) {
        Integer balance = BALANCES.get(wallet);
        if (balance == null || balance < amount) {
            return false;
        }
        BALANCES.put(wallet, balance - amount);
        return true;
    }

    @Override
    public void refund(String wallet, int amount) {
        BALANCES.merge(wallet, amount, Integer::sum);
    }
}
