package ru.reasy.crunch.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.reasy.crunch.dto.GetAccountRs;
import ru.reasy.crunch.utils.HttpAuthUtils;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class RestAccountClient {

    @Value("${crunch.api.key}")
    private String apiKey;
    @Value("${crunch.api.secret}")
    private String apiSecret;

    private final String baseURL = "https://ftx.com";

    private final String SUBACCOUNT_URL = "/api/subaccounts";

    public GetAccountRs getAccount() throws IOException {
        String timeStamp = String.valueOf(new Date().getTime());
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(String.format("%s%s", baseURL, SUBACCOUNT_URL))
                .method("GET", null)
                .addHeader("FTX-KEY", "UU6tYWP6kSKdHLYjCzB_75SzSSU4_72KP_MFXsyN")
                .addHeader("FTX-TS", timeStamp) //+ / 100
                .addHeader(
                        "FTX-SIGN",
                        HttpAuthUtils.getSign(
                                apiSecret,
                                timeStamp,
                                "GET",
                                SUBACCOUNT_URL,
                                null
                        )
                )
                .build();

        log.info(request.headers().toString());
        ResponseBody response = client.newCall(request).execute().body();
        GetAccountRs rs = new ObjectMapper().readValue(response.string(), GetAccountRs.class);
        if(!rs.isSuccess())
            log.error(rs.getError());
        return rs;
    }


    public void test() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://ftx.com/api/subaccounts")
                .method("GET", null)
                .addHeader("FTX-KEY", "UU6tYWP6kSKdHLYjCzB_75SzSSU4_72KP_MFXsyN")
                .addHeader("FTX-TS", "16368014519")
                .addHeader("FTX-SIGN", "jury-md0VzKNsBxJT6z6m6sG3_J1ofMc4jgdAzxK")
                .build();
        Response response = client.newCall(request).execute();
    }

}
