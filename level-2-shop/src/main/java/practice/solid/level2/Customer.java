package practice.solid.level2;

public class Customer {

    private final String name;
    /** REGULAR, VIP или EMPLOYEE */
    private final String type;
    private final String email;
    private final String phone;

    public Customer(String name, String type, String email, String phone) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
