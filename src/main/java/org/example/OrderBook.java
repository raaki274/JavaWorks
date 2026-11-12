package org.example;

import java.util.*;

public class OrderBook {
    public record Order(String id, boolean isBuy, double price, int quantity) {}

    private final PriorityQueue<Order> buyOrders = new PriorityQueue<>(
            Comparator.comparingDouble(Order::price).reversed()
    );
    private final PriorityQueue<Order> sellOrders = new PriorityQueue<>(
            Comparator.comparingDouble(Order::price)
    );

    public List<String> placeOrder(Order incoming) {
        List<String> trades = new ArrayList<>();
        int remainingQty = incoming.quantity();

        if (incoming.isBuy()) {
            while (remainingQty > 0 && !sellOrders.isEmpty() && sellOrders.peek().price() <= incoming.price()) {
                Order bestSell = sellOrders.poll();
                int tradedQty = Math.min(remainingQty, bestSell.quantity());
                trades.add("Matched " + tradedQty + " @ " + bestSell.price());
                remainingQty -= tradedQty;
                if (bestSell.quantity() > tradedQty) {
                    sellOrders.add(new Order(bestSell.id(), false, bestSell.price(), bestSell.quantity() - tradedQty));
                }
            }
            if (remainingQty > 0) {
                buyOrders.add(new Order(incoming.id(), true, incoming.price(), remainingQty));
            }
        } else {
            while (remainingQty > 0 && !buyOrders.isEmpty() && buyOrders.peek().price() >= incoming.price()) {
                Order bestBuy = buyOrders.poll();
                int tradedQty = Math.min(remainingQty, bestBuy.quantity());
                trades.add("Matched " + tradedQty + " @ " + bestBuy.price());
                remainingQty -= tradedQty;
                if (bestBuy.quantity() > tradedQty) {
                    buyOrders.add(new Order(bestBuy.id(), true, bestBuy.price(), bestBuy.quantity() - tradedQty));
                }
            }
            if (remainingQty > 0) {
                sellOrders.add(new Order(incoming.id(), false, incoming.price(), remainingQty));
            }
        }
        return trades;
    }

    public static void main(String[] args) {
        OrderBook book = new OrderBook();
        System.out.println("Order 1: " + book.placeOrder(new Order("S1", false, 150.00, 100)));
        System.out.println("Order 2: " + book.placeOrder(new Order("S2", false, 148.00, 50)));
        System.out.println("Order 3: " + book.placeOrder(new Order("B1", true, 150.00, 120)));
    }
}
