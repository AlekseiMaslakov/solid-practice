package practice.solid.level3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * "База данных" магазина. Пока хранит всё в памяти.
 */
public class Database implements DataStore {

    private static Database instance;

    private final Map<String, Order> orders = new LinkedHashMap<>();
    private final Map<String, String> customers = new LinkedHashMap<>();

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /** Очищает базу. Нужно для тестов. */
    public void clear() {
        orders.clear();
        customers.clear();
    }

    @Override
    public void saveOrder(Order order) {
        System.out.println("SQL: INSERT INTO orders VALUES ('" + order.id + "', ...)");
        orders.put(order.id, order);
    }

    @Override
    public Order findOrder(String id) {
        System.out.println("SQL: SELECT * FROM orders WHERE id = '" + id + "'");
        return orders.get(id);
    }

    @Override
    public List<Order> findAllOrders() {
        System.out.println("SQL: SELECT * FROM orders");
        return new ArrayList<>(orders.values());
    }

    @Override
    public void deleteOrder(String id) {
        orders.remove(id);
    }

    @Override
    public void saveCustomer(String email, String name) {
        customers.put(email, name);
    }

    @Override
    public String exportToExcel() {
        throw new UnsupportedOperationException("Экспорт в Excel пока не реализован");
    }

    @Override
    public void backup(String path) {
        throw new UnsupportedOperationException("Резервное копирование пока не реализовано");
    }
}
