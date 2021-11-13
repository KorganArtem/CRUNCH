package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class OrderBook {

    private OrderBookType type;
    BigDecimal price;
    BigDecimal size;
}
