package ru.reasy.crunch.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;
import ru.reasy.crunch.dto.OrderBooks;
import ru.reasy.crunch.service.OrderBookProc;
import ru.reasy.crunch.service.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    public final StorageService storageService;

    public TestController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Универсальный контроллер для тестов.
    @RequestMapping("/test")
    public Answ getSomeData(){
        return new Answ(
                new ArrayList<>(storageService.get(OrderBookType.ASK).values()),
                new ArrayList<>(storageService.get(OrderBookType.BID).values())
        );
    }

    @Getter
    @AllArgsConstructor
    class Answ{
        List<OrderBook> ask;
        List<OrderBook> bids;
    }
}
