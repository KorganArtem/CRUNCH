package ru.reasy.crunch.utils;

import ru.reasy.crunch.dto.OrderBook;

import java.math.BigDecimal;
import java.util.List;

public class Convertor {

    public static OrderBook getOrderBook(List<BigDecimal> arr){
        if(arr.size()!=2)
            return null;
        return OrderBook.builder()
                .price(arr.get(0))
                .size(arr.get(1))
                .build();
    }

}
