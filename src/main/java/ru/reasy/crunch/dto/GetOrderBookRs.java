package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class GetOrderBookRs {

    private boolean success;
    private OrderBooks result;
    private String error;
}
