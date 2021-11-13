package ru.reasy.crunch.service;

import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.GetOrderBookRs;
import ru.reasy.crunch.rest.RestAccountClient;
import ru.reasy.crunch.utils.Convertor;

import java.io.IOException;

@Component
public class OrderBookProc {

    private final StorageService storageService;
    private final RestAccountClient client;


    public OrderBookProc(StorageService storageService, RestAccountClient client) {
        this.storageService = storageService;
        this.client = client;
    }

    public void proc() throws IOException {

        GetOrderBookRs books = client.getOrderBooks();

        books.getResult().getAsks().forEach(
                Convertor::getOrderBook
        );

        books.getResult().getBids().forEach(
                Convertor::getOrderBook
        );
    }


}
