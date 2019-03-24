import static java.util.Objects.requireNonNull;

public class Order {

    // Decided to use simple int instead of  Quantity class as unit is always Kg if it can be different should crate class to keep unit as well and provide some  unit conversion
    private int quantity;
    private String userID;
    private OrderType type;
    // Decided to use simple double instead of Price class as unit is always GBP if it can be different should crate class to keep currency as well and provide some  currency conversion
    private double pricePerKg;

    public Order(String userID, int quantity, double price, OrderType type) {
        //require all these field to be  not nul  as are needed for  valid order
        requireNonNull(userID, "user id");
        requireNonNull(type, "type of order");
        this.type = type;
        this.userID = userID;
        this.quantity = quantity;
        this.pricePerKg = price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Order)) {
            return false;
        }
        Order otherOrder = (Order) other;
        if (!otherOrder.userID.equals(userID))
            return false;
        if (!(otherOrder.type == type))
            return false;
        if (!(otherOrder.quantity == quantity))
            return false;
        if (!(otherOrder.pricePerKg == pricePerKg))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + userID.hashCode();
        result = 37 * result + quantity;
        result = 41 * result + Double.valueOf(pricePerKg).hashCode();
        result = 43 * result + (type == OrderType.BUY ? 1 : 2);
        return result;
    }
}
