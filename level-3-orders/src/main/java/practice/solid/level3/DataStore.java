package practice.solid.level3;

import java.util.List;

/**
 * Хранилище данных магазина.
 */
public interface DataStore {

    void saveOrder(Order order);

    Order findOrder(String id);

    List<Order> findAllOrders();

    void deleteOrder(String id);

    void saveCustomer(String email, String name);

    String exportToExcel();

    void backup(String path);
}
