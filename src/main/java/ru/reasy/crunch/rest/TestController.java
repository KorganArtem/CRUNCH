package ru.reasy.crunch.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.reasy.crunch.service.OrderBookProc;

import java.io.IOException;

@RestController
public class TestController {

    public final OrderBookProc proc;

    public TestController(OrderBookProc proc) {
        this.proc = proc;
    }

    //Универсальный контроллер для тестов.
    @RequestMapping("/test")
    public Object getSomeData() throws Exception {
        return proc.proc();
    }
}
