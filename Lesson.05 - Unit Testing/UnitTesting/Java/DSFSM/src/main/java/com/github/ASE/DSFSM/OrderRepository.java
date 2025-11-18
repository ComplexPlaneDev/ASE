package com.github.ASE.DSFSM;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    List<Order> findAll();
}
