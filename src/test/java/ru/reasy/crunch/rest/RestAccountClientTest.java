package ru.reasy.crunch.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.reasy.crunch.dto.GetAccountRs;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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

}