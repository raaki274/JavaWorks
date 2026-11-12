package org.example;

import java.util.*;

public class FxConverter {
    public record Rate(String toCurrency, double multiplier) {}

    public double getExchangeRate(Map<String, List<Rate>> graph, String start, String target) {
        if (!graph.containsKey(start) || !graph.containsKey(target)) return -1.0;
        if (start.equals(target)) return 1.0;

        Queue<Map.Entry<String, Double>> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.add(Map.entry(start, 1.0));
        visited.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            String currCurrency = current.getKey();
            double currRate = current.getValue();

            if (currCurrency.equals(target)) return currRate;

            for (Rate edge : graph.getOrDefault(currCurrency, List.of())) {
                if (visited.add(edge.toCurrency())) {
                    queue.add(Map.entry(edge.toCurrency(), currRate * edge.multiplier()));
                }
            }
        }
        return -1.0;
    }

    public static void main(String[] args) {
        FxConverter converter = new FxConverter();
        Map<String, List<Rate>> rates = Map.of(
                "USD", List.of(new Rate("EUR", 0.92)),
                "EUR", List.of(new Rate("GBP", 0.85)),
                "GBP", List.of(new Rate("JPY", 190.0)),
                "JPY", List.of()
        );

        System.out.printf("USD to GBP: %.4f%n", converter.getExchangeRate(rates, "USD", "GBP"));
        System.out.printf("USD to JPY: %.4f%n", converter.getExchangeRate(rates, "USD", "JPY"));
        System.out.printf("USD to AUD: %.1f%n", converter.getExchangeRate(rates, "USD", "AUD"));
    }
}

