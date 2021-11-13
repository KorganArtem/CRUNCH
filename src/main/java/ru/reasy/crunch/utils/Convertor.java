package ru.reasy.crunch.utils;

import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;

import java.math.BigDecimal;
import java.util.List;

public class Convertor {

    public static OrderBook getOrderBook(List<BigDecimal> arr, OrderBookType type){
        if(arr.size()!=2)
            return null;
        return OrderBook.builder()
                .price(arr.get(0))
                .size(arr.get(1))
                .type(type)
                .build();
    }

}
