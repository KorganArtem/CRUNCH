package ru.reasy.crunch;

import lombok.Getter;

@Getter
public enum Constants {

    HTTP_HOST("https://ftx.com"),
    SUBACCOUNT_URL("/api/subaccounts"),
    ORDER_BOOK_URL("/api/markets/BTC-PERP/orderbook?depth=100"),
    ORDERS_URL("/api/orders?market=BTC-PERP");

    private String URL;
    Constants(String url) {
        this.URL = url;
    }
}
