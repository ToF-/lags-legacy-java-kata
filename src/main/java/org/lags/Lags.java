package org.lags;

import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

public class Lags {
    private final List<Order> orders;

    public Lags(List<Order> orders) {
        this.orders = orders;
    }

    public int revenue_orders(List<Order> orders) {
        if(orders.size() == 0)
            return 0;
        Order order = orders.get(0);
        System.out.format("%10s %10d\n", order.getId(), order.revenue);
        if(order.revenue != -1)
            return order.revenue;
        List<Order>select = new ArrayList<Order>();
        int start = order.getStart();
        int end   = start + order.getDuration();
        int final_end;
        int year  = start / 1000;
        int year_end = year * 1000 + 365;
        if(end > year_end) {
            int days = end - year_end;
            end = (year + 1) * 1000 + days;
        }
        for (Order o : orders) {
            if(o.getStart() >= end) {
                select.add(o);
            }
        }
        List<Order> comp = select;
        int revenueA = order.getPrice() + revenue_orders(comp);
        List <Order>rem = new ArrayList(orders);
        rem.remove(0);
        int rev2 = revenue_orders(rem);
        order.setRevenue(Math.max(revenueA, rev2));
        return Math.max(rev2, revenueA);
    }

    public int revenue() {
        Comparator<Order> cm = new Comparator<Order>() {
            @Override
            public int compare(Order a, Order b) {
                return a.getStart() - b.getStart();
            }
        };
        Collections.sort(this.orders, cm);
        return revenue_orders(this.orders);
    }
};

