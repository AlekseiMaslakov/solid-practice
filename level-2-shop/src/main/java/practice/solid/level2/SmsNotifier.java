package practice.solid.level2;

import java.util.ArrayList;
import java.util.List;

public class SmsNotifier implements Notifier {

    /** Отправленные SMS (вместо настоящего SMS-шлюза). */
    public final List<String> sent = new ArrayList<>();

    @Override
    public void sendEmail(String email, String text) {
        throw new UnsupportedOperationException("SmsNotifier не умеет отправлять email");
    }

    @Override
    public void sendSms(String phone, String text) {
        System.out.println("SMS -> " + phone + ": " + text);
        sent.add(phone + ": " + text);
    }

    @Override
    public void sendPush(String deviceId, String text) {
        throw new UnsupportedOperationException("SmsNotifier не умеет отправлять push");
    }
}
