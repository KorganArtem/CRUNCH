package ru.reasy.crunch.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Slf4j
public class HttpAuthUtilsTest {

    public static final String API_SUBACCOUNTS = "/api/orders?market=BTC-PERP";
    //Подпись сгенерирована скриптом из примера документации
    private static final String RIGHT_SIGN ="55a80dcb896ee97b0988c7d77b8e90bebebaee5300d3fc72330b101e421e2ef8";
    @Test
    public void getSignTest(){

        String timeStamp = "1636803185625";
        String sign = HttpAuthUtils.getSign(
                "YOUR_API_SECRET",
                timeStamp,
                "POST",
                API_SUBACCOUNTS,
                "{someField: \"someData\"}"
        );
        log.info("Result: {}", sign);
        assertNotNull(sign);

        sign = HttpAuthUtils.getSign(
                "YOUR_API_SECRET",
                timeStamp,
                "POST",
                API_SUBACCOUNTS,
                null
        );

        log.info("Result: {}", sign);
        assertNull(sign);

        sign = HttpAuthUtils.getSign(
                "YOUR_API_SECRET",
                timeStamp,
                "GET",
                API_SUBACCOUNTS,
                null
        );

        log.info("Result: {}", sign);
        assertNotNull(sign);
        assertEquals(sign, RIGHT_SIGN);
    }


}

//55a80dcb896ee97b0988c7d77b8e90bebebaee5300d3fc72330b101e421e2ef8