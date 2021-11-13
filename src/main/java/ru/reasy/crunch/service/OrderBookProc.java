package ru.reasy.crunch.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.GetOrderBookRs;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;
import ru.reasy.crunch.dto.OrderBooks;
import ru.reasy.crunch.rest.RestAccountClient;
import ru.reasy.crunch.utils.Convertor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderBookProc {

    private final StorageService storageService;
    private final RestAccountClient client;


    public OrderBookProc(StorageService storageService, RestAccountClient client) {
        this.storageService = storageService;
        this.client = client;
    }

    public Object proc() throws IOException {

        GetOrderBookRs booksRes = client.getOrderBooks();
        OrderBooks books = booksRes.getResult();
        List<List<BigDecimal>> bids = sorter(books.getBids());
        List<OrderBook> bidsList = new ArrayList<>();
        for (List<BigDecimal>bid : bids){
            bidsList.add(Convertor.getOrderBook(bid, OrderBookType.BID));
            if (bidsList.size()==10)
                break;
        }

        List<List<BigDecimal>> asks = sorter(books.getAsks());
        List<OrderBook> asksList = new ArrayList<>();
        for (int ind = asks.size();  ind > asks.size()-10; ind--){
            asksList.add(Convertor.getOrderBook(asks.get(ind-1), OrderBookType.ASK));
        }


        return new Answ(asksList, bidsList);
    }

    private List<List<BigDecimal>> sorter(List<List<BigDecimal>> arr){
        return arr.parallelStream()
                .sorted(Comparator.comparing(a -> a.get(0)))
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class Answ{

        List ask;
        List bid;
    }
}
