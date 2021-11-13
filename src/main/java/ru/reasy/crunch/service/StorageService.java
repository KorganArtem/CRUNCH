package ru.reasy.crunch.service;

import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;

import static ru.reasy.crunch.utils.Tools.*;
import static ru.reasy.crunch.utils.Tools.getHash;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StorageService {
    private ConcurrentMap<String, OrderBook> storageAsk;
    private ConcurrentMap<String, OrderBook> storageBids;

    public StorageService() {
        storageBids = new ConcurrentHashMap<>();
        storageAsk = new ConcurrentHashMap<>();
    }

    public void addToStorage(OrderBook ask) throws NoSuchAlgorithmException {
        switch (ask.getType()){
            case ASK: {
                storageAsk.putIfAbsent(getHash(ask.toString()), ask);
                sortAndCutBits();
                break;
            }
            case BID: {
                storageBids.putIfAbsent(getHash(ask.toString()), ask);
                sortAndCutBits();
                break;
            }
        }

    }

    public Map<String, OrderBook> get(OrderBookType type){
        switch (type){
            case ASK: return storageAsk;
            case BID: return storageBids;
        }
        return null;
    }

    private void sortAndCutBits() throws NoSuchAlgorithmException {
        if(storageBids.size()>10) {
            List<OrderBook> list = storageBids
                    .values()
                    .parallelStream()
                    .sorted(Comparator.comparing(OrderBook::getPrice))
                    .collect(Collectors.toList());
            ConcurrentMap<String, OrderBook> newMap = new ConcurrentHashMap<>();
            for (OrderBook orderbook : list) {
                newMap.put(getHash(orderbook.toString()), orderbook);
            }
            storageBids = newMap;
        }
    }
}
