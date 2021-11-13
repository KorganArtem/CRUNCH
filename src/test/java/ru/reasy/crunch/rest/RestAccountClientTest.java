package ru.reasy.crunch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.reasy.crunch.dto.GetAccountRs;
import ru.reasy.crunch.dto.GetOpenOrderRs;
import ru.reasy.crunch.dto.GetOrderBookRs;

import java.io.IOException;

import static org.testng.Assert.*;

@SpringBootTest(classes = RestAccountClient.class)
public class RestAccountClientTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RestAccountClient accountClient;


    @Test
    public void chetGetAccountData() throws IOException {
        GetAccountRs response = accountClient.getAccount();
        assertNotNull(response);
        assertNull(response.getError());
    }

    @Test
    public void getOrdersTest() throws IOException {

        GetOpenOrderRs rs = accountClient.getOrders();
        logger.info(rs.toString());
        assertTrue(rs.isSuccess());
        assertNull(rs.getError());
    }
    @Test
    public void getOrderBooksTest() throws IOException {

        GetOrderBookRs rs = accountClient.getOrderBooks();
        logger.info(rs.toString());
        assertTrue(rs.isSuccess());
        assertNull(rs.getError());
    }

}