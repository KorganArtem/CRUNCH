package ru.reasy.crunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;

@SpringBootTest(classes = OrderBookProc.class)
public class OrderBookProcTest extends AbstractTestNGSpringContextTests {

    @Autowired
    OrderBookProc proc;

    @Test
    public void test() throws IOException {
        proc.proc();
    }

}