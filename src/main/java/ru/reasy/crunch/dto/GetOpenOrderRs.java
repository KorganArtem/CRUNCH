package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class GetOpenOrderRs {

        private boolean success;
        private List<OpenedOrder> result;
        private String error;
}
