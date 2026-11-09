package practice.solid.level3;

public class SalesReport {

    /** Выручка по всем неотменённым заказам. */
    public int totalRevenue() {
        int sum = 0;
        for (Order order : Database.getInstance().findAllOrders()) {
            if (!"CANCELLED".equals(order.status)) {
                sum += order.total;
            }
        }
        return sum;
    }
}
