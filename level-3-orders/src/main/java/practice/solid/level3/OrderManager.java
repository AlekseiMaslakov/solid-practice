package practice.solid.level3;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Оформление и отмена заказов.
 */
public class OrderManager {

    private static int nextId = 1;

    /** Сбрасывает нумерацию заказов. Нужно для тестов. */
    public static void resetCounter() {
        nextId = 1;
    }

    /**
     * Оформляет заказ.
     *
     * @param items       список пар {артикул, количество}
     * @param paymentType CARD, WALLET или CASH
     * @param account     номер карты или кошелька (для CASH не нужен)
     * @return номер заказа или строка, начинающаяся с "ERROR: "
     */
    public String processOrder(String email, List<String[]> items, String paymentType, String account,
                               boolean express, boolean giftWrap, boolean sendSms, String phone) {
        // 1. проверки
        if (email == null || !email.matches("[\\w.+-]+@[\\w-]+\\.[\\w.]+")) {
            return "ERROR: Некорректный email";
        }
        if (items == null || items.isEmpty()) {
            return "ERROR: Пустой заказ";
        }
        if (express && paymentType.equals("CASH")) {
            return "ERROR: Экспресс-доставка недоступна при оплате наличными";
        }

        // 2. наличие и сумма
        Inventory inv = Inventory.getInstance();
        int total = 0;
        Map<String, Integer> orderItems = new LinkedHashMap<>();
        for (String[] item : items) {
            String sku = item[0];
            int qty = Integer.parseInt(item[1]);
            Integer price = inv.getPrice(sku);
            if (price == null) {
                return "ERROR: Неизвестный товар " + sku;
            }
            if (inv.getStock(sku) < qty) {
                return "ERROR: Недостаточно товара " + sku;
            }
            total += price * qty;
            orderItems.put(sku, qty);
        }

        // 3. скидки и доплаты
        if (total > 10000) {
            total = total - total * 5 / 100;
        }
        if (express) {
            total += 500;
        }
        if (giftWrap) {
            total += 150;
        }

        // 4. оплата
        boolean paid;
        if (paymentType.equals("CARD")) {
            BankCardProcessor processor = new BankCardProcessor();
            paid = processor.charge(account, total);
        } else if (paymentType.equals("WALLET")) {
            WalletProcessor processor = new WalletProcessor();
            paid = processor.charge(account, total);
        } else if (paymentType.equals("CASH")) {
            paid = new CashProcessor().charge(account, total);
        } else {
            return "ERROR: Неизвестный способ оплаты " + paymentType;
        }
        if (!paid) {
            return "ERROR: Оплата отклонена";
        }

        // 5. списание со склада
        for (Map.Entry<String, Integer> e : orderItems.entrySet()) {
            inv.setStock(e.getKey(), inv.getStock(e.getKey()) - e.getValue());
        }

        // 6. сохранение
        Order order = new Order();
        order.id = "ORD-" + nextId++;
        order.customerEmail = email;
        order.items = orderItems;
        order.total = total;
        order.paymentType = paymentType;
        order.paymentAccount = account;
        order.status = "PAID";
        order.createdAt = new Date();
        Database.getInstance().saveOrder(order);

        // 7. уведомления
        StringBuilder body = new StringBuilder();
        body.append("Здравствуйте!\n\n");
        body.append("Ваш заказ ").append(order.id).append(" оформлен.\n\n");
        for (Map.Entry<String, Integer> e : orderItems.entrySet()) {
            body.append("- ").append(e.getKey()).append(" x ").append(e.getValue()).append("\n");
        }
        body.append("\nИтого: ").append(total).append(" руб.\n");
        if (express) {
            body.append("Доставка: экспресс\n");
        }
        if (giftWrap) {
            body.append("Подарочная упаковка: да\n");
        }
        new SmtpMailServer().send(email, "Заказ " + order.id, body.toString());

        if (sendSms) {
            if (phone == null || !phone.matches("\\+7\\d{10}")) {
                System.out.println("WARN: некорректный телефон, SMS не отправлено");
            } else {
                new SmsGateway().send(phone, "Заказ " + order.id + " оформлен. Сумма " + total + " руб.");
            }
        }

        System.out.println("[" + new Date() + "] Заказ " + order.id + " обработан");
        return order.id;
    }

    /**
     * Отменяет заказ, возвращает деньги и товары на склад.
     *
     * @return "OK" или строка, начинающаяся с "ERROR: "
     */
    public String cancelOrder(String orderId) {
        Order order = Database.getInstance().findOrder(orderId);
        if (order == null) {
            return "ERROR: Заказ не найден";
        }
        if ("CANCELLED".equals(order.status)) {
            return "ERROR: Заказ уже отменён";
        }

        PaymentProcessor processor;
        if (order.paymentType.equals("CARD")) {
            processor = new BankCardProcessor();
        } else if (order.paymentType.equals("WALLET")) {
            processor = new WalletProcessor();
        } else {
            processor = new CashProcessor();
        }
        if (!(processor instanceof CashProcessor)) {
            processor.refund(order.paymentAccount, order.total);
        }

        Inventory inv = Inventory.getInstance();
        for (Map.Entry<String, Integer> e : order.items.entrySet()) {
            inv.setStock(e.getKey(), inv.getStock(e.getKey()) + e.getValue());
        }

        order.status = "CANCELLED";

        String body = "Ваш заказ " + order.id + " отменён. ";
        if (order.paymentType.equals("CASH")) {
            body += "Деньги можно получить в магазине.";
        } else {
            body += "Средства вернутся в течение 3 дней.";
        }
        new SmtpMailServer().send(order.customerEmail, "Заказ " + order.id + " отменён", body);

        System.out.println("[" + new Date() + "] Заказ " + order.id + " отменён");
        return "OK";
    }
}
