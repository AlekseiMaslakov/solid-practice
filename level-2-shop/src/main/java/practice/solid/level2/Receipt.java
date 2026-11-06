package practice.solid.level2;

public class Receipt {

    private final int subtotal;
    private final int discount;
    private final int delivery;
    private final int total;

    public Receipt(int subtotal, int discount, int delivery, int total) {
        this.subtotal = subtotal;
        this.discount = discount;
        this.delivery = delivery;
        this.total = total;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public int getDiscount() {
        return discount;
    }

    public int getDelivery() {
        return delivery;
    }

    public int getTotal() {
        return total;
    }
}
