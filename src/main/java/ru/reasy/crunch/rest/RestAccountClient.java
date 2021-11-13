package ru.reasy.crunch.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.GetAccountRs;
import ru.reasy.crunch.dto.GetOpenOrderRs;
import ru.reasy.crunch.dto.GetOrderBookRs;
import ru.reasy.crunch.utils.HttpAuthUtils;

import java.io.IOException;
import java.util.Date;

import static ru.reasy.crunch.Constants.*;

@Slf4j
@Component
public class RestAccountClient {

    @Value("${crunch.api.key}")
    private String apiKey;
    @Value("${crunch.api.secret}")
    private String apiSecret;

    public GetAccountRs getAccount() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = getRequest(SUBACCOUNT_URL.getURL(),  "GET",null);
        log.info(request.headers().toString());
        ResponseBody response = client.newCall(request).execute().body();
        GetAccountRs rs = new ObjectMapper().readValue(response.string(), GetAccountRs.class);
        if(!rs.isSuccess())
            log.error(rs.getError());
        return rs;
    }

    public GetOpenOrderRs getOrders() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = getRequest(ORDERS_URL.getURL(),  "GET",null);
        Response response = client.newCall(request).execute();

        return new ObjectMapper().readValue(response.body().string(), GetOpenOrderRs.class);
    }


    public GetOrderBookRs getOrderBooks() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = getRequest(ORDER_BOOK_URL.getURL(),  "GET",null);
        Response response = client.newCall(request).execute();

        return new ObjectMapper().readValue(response.body().string(), GetOrderBookRs.class);
    }

    public Request getRequest(String url, String method, RequestBody body){
        String timeStamp = String.valueOf(new Date().getTime());
        return new Request.Builder()
                .url(String.format("%s%s", HTTP_HOST.getURL(), url))
                .method(method, body)
                .addHeader("FTX-KEY", apiKey)
                .addHeader("FTX-TS", timeStamp) //+ / 100
                .addHeader(
                        "FTX-SIGN",
                        HttpAuthUtils.getSign(
                                apiSecret,
                                timeStamp,
                                method,
                                url,
                                null
                        )
                )
                .build();
    }

}
