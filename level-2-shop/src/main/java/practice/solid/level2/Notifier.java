package practice.solid.level2;

/**
 * Отправка уведомлений покупателю.
 */
public interface Notifier {

    void sendEmail(String email, String text);

    void sendSms(String phone, String text);

    void sendPush(String deviceId, String text);
}
