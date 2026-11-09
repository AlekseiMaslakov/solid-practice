package practice.solid.level3;

import java.util.ArrayList;
import java.util.List;

/**
 * Отправка SMS. Вместо настоящего шлюза складывает сообщения в SENT.
 */
public class SmsGateway {

    public static final List<String> SENT = new ArrayList<>();

    public void send(String phone, String text) {
        System.out.println("Подключение к sms.provider.ru ...");
        SENT.add(phone + ": " + text);
    }
}
