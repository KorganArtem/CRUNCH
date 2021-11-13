package ru.reasy.crunch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.GetOrderBookRs;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;
import ru.reasy.crunch.dto.OrderBooks;
import ru.reasy.crunch.rest.RestClient;
import ru.reasy.crunch.utils.Convertor;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderBookProc {

    private final StorageService storageService;
    private final RestClient client;


    public OrderBookProc(StorageService storageService, RestClient client) {
        this.storageService = storageService;
        this.client = client;
    }

    // TODO вызывать в отдельном потоке срзу после завершения сеанса
    @Scheduled(fixedRate = 5000)
    public void proc() throws IOException, NoSuchAlgorithmException {
        GetOrderBookRs booksRes = client.getOrderBooks();
        OrderBooks books = booksRes.getResult();
        List<List<BigDecimal>> bids = sorter(books.getBids());
        int counter = 1;
        for (List<BigDecimal>bid : bids){
            OrderBook book = Convertor.getOrderBook(bid, OrderBookType.BID);
            storageService.addToStorage(book);
            if (counter==10)
                break;
            counter++;
        }

        List<List<BigDecimal>> asks = sorter(books.getAsks());
        for (int ind = asks.size();  ind > asks.size()-10; ind--){
            OrderBook book = Convertor.getOrderBook(asks.get(ind-1), OrderBookType.ASK);
            storageService.addToStorage(book);
        }
    }

    private List<List<BigDecimal>> sorter(List<List<BigDecimal>> arr){
        return arr.parallelStream()
                .sorted(Comparator.comparing(a -> a.get(0)))
                .collect(Collectors.toList());
    }

}
