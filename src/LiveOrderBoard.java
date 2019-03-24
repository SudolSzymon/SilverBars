import java.util.*;

public class LiveOrderBoard {

    //To keep orders used maps between orders at certain price and price to make it  fast and easy to  sum quantity for each  price as requested
    //used tree map to keep entries sorted, it is less efficient than hash map the get summary is main functionality and it makes it easier and faster than sorting set of entries when ever we get summary
    private SortedMap<Double, List<Order>> buyOrders = new TreeMap<>((e1, e2) -> {
        if (e1.equals(e2))
            return 0;
        return e1 > e2 ? -1 : 1;
    });
    //Sell orders and buy  orders maps are separated to make summary generation faster
    private SortedMap<Double, List<Order>> sellOrders = new TreeMap<>((e1, e2) -> {
        if (e1.equals(e2))
            return 0;
        return e1 > e2 ? 1 : -1;
    });

    public void registerOrder(String userID, int orderQuantity, double pricePerKg, OrderType type) {
        SortedMap<Double, List<Order>> orders = type == OrderType.BUY ? buyOrders : sellOrders;
        List<Order> ordersList = orders.get(pricePerKg);
        boolean isNewOrderList = false;
        if (ordersList == null) {
            ordersList = new ArrayList<>();
            isNewOrderList = true;
        }
        ordersList.add(new Order(userID, orderQuantity, pricePerKg, type));
        if (isNewOrderList)  // No need to put it to map if it is already in the list as list will be updated, it saves time especially there wil be many orders for the same price most likely
            orders.put(pricePerKg, ordersList);
    }

    public boolean cancelOrder(String userID, int orderQuantity, double pricePerKg, OrderType type) {
        SortedMap<Double, List<Order>> orders = type == OrderType.BUY ? buyOrders : sellOrders;
        List<Order> ordersList = orders.get(pricePerKg);
        if (ordersList == null) {
            return false;
        }
        boolean result = ordersList.remove(new Order(userID, orderQuantity, pricePerKg, type));
        if (ordersList.isEmpty()) {
            orders.remove(pricePerKg);
        }
        return result;
    }

    // Summary takes type as argument as depending on type those need to be sorted differently
    public String getSummary(OrderType type) {
        SortedMap<Double, List<Order>> orders = type == OrderType.BUY ? buyOrders : sellOrders;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Double, List<Order>> entry : orders.entrySet()) {
            long totalQuantityPerPrice = 0;
            for (Order order : entry.getValue()) {
                totalQuantityPerPrice += order.getQuantity();
            }
            //format is as follows [1112Kg for 11.0£ per Kg]
            builder.append("[").append(totalQuantityPerPrice).append("Kg for ").append(entry.getKey()).append("£ per Kg]");
        }
        return builder.toString();
    }
}


