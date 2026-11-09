package practice.solid.level3;

import java.util.ArrayList;
import java.util.List;

/**
 * Отправка писем. Вместо настоящего SMTP-сервера складывает письма в OUTBOX.
 */
public class SmtpMailServer {

    /** Каждое письмо: {кому, тема, текст} */
    public static final List<String[]> OUTBOX = new ArrayList<>();

    public void send(String to, String subject, String body) {
        System.out.println("Подключение к smtp.shop.local:25 ...");
        OUTBOX.add(new String[]{to, subject, body});
    }
}
