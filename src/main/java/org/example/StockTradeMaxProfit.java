package org.example;

public class StockTradeMaxProfit {

    int maxProfit (int[] prices) {
        int minPrice = Integer.MAX_VALUE, maxProfit = 0;
        for(int price:prices) {
            if(price<minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, (price-minPrice));
            }
        }
        return maxProfit;
    }

    public static void main (String[] args) {
        int[] prices = {9, 7, 4, 5, 2, 3, 8, 6, 1, 8, 9};
        int maxProfit = new StockTradeMaxProfit().maxProfit(prices);
        System.out.println(maxProfit);
    }

}
