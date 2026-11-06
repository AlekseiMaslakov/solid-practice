package practice.solid.level2;

import java.util.ArrayList;
import java.util.List;

public class EmailNotifier implements Notifier {

    /** Отправленные письма (вместо настоящего почтового сервера). */
    public final List<String> sent = new ArrayList<>();

    @Override
    public void sendEmail(String email, String text) {
        System.out.println("EMAIL -> " + email + ": " + text);
        sent.add(email + ": " + text);
    }

    @Override
    public void sendSms(String phone, String text) {
        throw new UnsupportedOperationException("EmailNotifier не умеет отправлять SMS");
    }

    @Override
    public void sendPush(String deviceId, String text) {
        throw new UnsupportedOperationException("EmailNotifier не умеет отправлять push");
    }
}
