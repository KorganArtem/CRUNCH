package ru.reasy.crunch.service;

import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.OrderBook;
import ru.reasy.crunch.dto.OrderBookType;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static ru.reasy.crunch.utils.Tools.getHash;

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
                sortAndCutAsks();
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
                if(newMap.size()>=10)
                    break;
                newMap.put(getHash(orderbook.toString()), orderbook);
            }
            storageBids = newMap;
        }
    }

    private void sortAndCutAsks() throws NoSuchAlgorithmException {
        if(storageAsk.size()>10) {
            List<OrderBook> list = storageAsk
                    .values()
                    .parallelStream()
                    .sorted(Comparator.comparing(OrderBook::getPrice))
                    .collect(Collectors.toList());
            ConcurrentMap<String, OrderBook> newMap = new ConcurrentHashMap<>();
            for (int ind = list.size();  ind > list.size()-10; ind--){
                if(newMap.size()>=10)
                    break;
                OrderBook book = list.get(ind-1);
                newMap.put(getHash(book.toString()), book);
            }
            storageAsk = newMap;
        }
    }

    public void writeState(){
        //TODO Сохранение состояния
    }
}
