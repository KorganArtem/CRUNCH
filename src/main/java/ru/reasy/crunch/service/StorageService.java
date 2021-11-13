package ru.reasy.crunch.service;

import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;

import static ru.reasy.crunch.utils.Tools.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class StorageService {
    private Map<String, OrderBook> storageAsk;
    private Map<String, OrderBook> storageBids;

    public StorageService() {
        storageBids = new HashMap<>();
        storageAsk = new HashMap<>();
    }

    public void addToStorage(OrderBook ask) throws NoSuchAlgorithmException {
        switch (ask.getType()){
            case ASK: storageAsk.put(getHash(ask.toString()), ask);break;
            case BID: storageBids.put(getHash(ask.toString()), ask);break;
        }
    }

    public Map<String, OrderBook> get(OrderBookType type){
        switch (type){
            case ASK: return storageAsk;
            case BID: return storageBids;
        }
        return null;
    }
}
