package practice.solid.level2;

import java.util.List;

/**
 * Оформление покупки: считает скидку и доставку, печатает чек, уведомляет покупателя.
 */
public class Checkout {

    private final Notifier emailNotifier;
    private final Notifier smsNotifier;

    public Checkout(Notifier emailNotifier, Notifier smsNotifier) {
        this.emailNotifier = emailNotifier;
        this.smsNotifier = smsNotifier;
    }

    /**
     * @param deliveryType PICKUP, COURIER или POST
     */
    public Receipt checkout(Customer customer, List<Product> items, String deliveryType) {
        int subtotal = 0;
        for (Product p : items) {
            subtotal += p.getPrice();
        }

        // скидка зависит от типа покупателя
        int discount;
        switch (customer.getType()) {
            case "REGULAR":
                discount = subtotal >= 5000 ? subtotal * 5 / 100 : 0;
                break;
            case "VIP":
                discount = subtotal * 10 / 100;
                break;
            case "EMPLOYEE":
                discount = Math.min(subtotal * 30 / 100, 3000);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип покупателя: " + customer.getType());
        }

        // вес посылки
        double weight = 0;
        boolean hasPhysical = false;
        for (Product p : items) {
            if (p instanceof DigitalProduct) {
                continue; // у цифровых товаров нет веса
            }
            weight += p.getWeight();
            hasPhysical = true;
        }

        // доставка
        int delivery;
        if (!hasPhysical) {
            delivery = 0;
        } else {
            switch (deliveryType) {
                case "PICKUP":
                    delivery = 0;
                    break;
                case "COURIER":
                    delivery = 300;
                    if (weight > 5) {
                        delivery += (int) Math.ceil(weight - 5) * 50;
                    }
                    break;
                case "POST":
                    delivery = 200 + (int) Math.ceil(weight) * 30;
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный способ доставки: " + deliveryType);
            }
            if (customer.getType().equals("VIP") && deliveryType.equals("COURIER")) {
                delivery = 0; // курьер для VIP бесплатно
            }
        }

        int total = subtotal - discount + delivery;
        Receipt receipt = new Receipt(subtotal, discount, delivery, total);

        // печать чека
        System.out.println("===== ЧЕК =====");
        System.out.println("Покупатель: " + customer.getName());
        for (Product p : items) {
            System.out.println("  " + p.getName() + " — " + p.getPrice() + " руб.");
        }
        System.out.println("Сумма:    " + subtotal);
        System.out.println("Скидка:   " + discount);
        System.out.println("Доставка: " + delivery);
        System.out.println("Итого:    " + total);
        System.out.println("===============");

        // уведомления
        String text = "Заказ на сумму " + total + " руб. оформлен";
        emailNotifier.sendEmail(customer.getEmail(), text);
        if (customer.getType().equals("VIP")) {
            smsNotifier.sendSms(customer.getPhone(), text);
        }

        return receipt;
    }
}
