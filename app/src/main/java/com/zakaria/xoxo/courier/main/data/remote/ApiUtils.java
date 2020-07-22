package com.zakaria.xoxo.courier.main.data.remote;

import com.zakaria.xoxo.courier.main.data.remote.APIService;
import com.zakaria.xoxo.courier.main.data.remote.RetrofitClient;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://exam.androwep.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
