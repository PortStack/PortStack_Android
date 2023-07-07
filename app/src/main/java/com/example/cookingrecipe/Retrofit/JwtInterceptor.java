package com.example.cookingrecipe.Retrofit;
import com.example.cookingrecipe.Util.TokenUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JwtInterceptor implements Interceptor {

    private String jwtToken;

    public JwtInterceptor() {
        this.jwtToken = TokenUtil.getAccessToken("none");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        System.out.println("jwtInterceptor : " + jwtToken);

        // If JWT token is available, add it to the Authorization header
        if (jwtToken != null && !jwtToken.isEmpty()) {
            Request modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + jwtToken)
                    .build();

            return chain.proceed(modifiedRequest);
        }

        return chain.proceed(originalRequest);
    }
}

