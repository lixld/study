package com.lixl.study.springcloudGateWay.util;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class FilterUtils {
    public static Mono<Void> invalidToken(ServerWebExchange exchange) {
        return null;
    }

    public static Mono<Void> invalidUrl(ServerWebExchange exchange) {
        return null;
    }
}
