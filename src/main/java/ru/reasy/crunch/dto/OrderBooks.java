package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class OrderBooks {

    private List<List<BigDecimal>> asks;
    private List<List<BigDecimal>> bids;

}
