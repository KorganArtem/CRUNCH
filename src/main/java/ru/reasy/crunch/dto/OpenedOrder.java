package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@Setter
@Builder
@Jacksonized
public class OpenedOrder {

    private Date createdAt;
    private int filledSize;
    private String future;
    private int id;
    private String market;
    private double price;
    private double avgFillPrice;
    private int remainingSize;
    private String side;
    private int size;
    private String status;
    private String type;
    private boolean reduceOnly;
    private boolean ioc;
    private boolean postOnly;
    private Object clientId;
}
