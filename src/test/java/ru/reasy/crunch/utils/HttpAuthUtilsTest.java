package ru.reasy.crunch.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.testng.Assert.*;

@Slf4j
public class HttpAuthUtilsTest {

    public static final String API_SUBACCOUNTS = "/api/subaccounts";
    //Подпись сгенерирована скриптом из примера документации
    private static final String RIGHT_SIGN ="63ed32f398f399f640de9b0a9477bece2a9d4dda1048008794296f5005300bc3";
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
//YOUR_API_SECRET1636803185625GET/api/subaccounts
//YOUR_API_SECRET1636803185625GET/api/subaccounts

//1636803185625GET/api/subaccounts
//1636803185625GET/api/subaccounts

//Result: 8bc8f088057308e5436a3ca7382a5d6aea02c3f522858257a1a1ff1788ae5fe2

//757d766c99b6a989b190d62c97ebc4bdc46d71ac58191406240d44d9eb9765a3
//63ed32f398f399f640de9b0a9477bece2a9d4dda1048008794296f5005300bc3