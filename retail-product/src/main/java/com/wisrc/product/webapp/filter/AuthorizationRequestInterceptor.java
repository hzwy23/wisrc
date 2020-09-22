package com.wisrc.product.webapp.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//@Configuration
public class AuthorizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjI5OTc5NTUsImlzcyI6Imh6d3kyMyIsInVzZXJuYW1lIjoiYWRtaW4ifQ.eWOEipABcZzxomzUPR_1oYCCt9uw8DYnpy2lCrVWvOM");
    }
}
