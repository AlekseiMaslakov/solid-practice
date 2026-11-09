package practice.solid.level3;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {

    public String id;
    public String customerEmail;
    /** артикул -> количество */
    public Map<String, Integer> items = new LinkedHashMap<>();
    public int total;
    /** CARD, WALLET или CASH */
    public String paymentType;
    public String paymentAccount;
    /** PAID или CANCELLED */
    public String status;
    public Date createdAt;
}
