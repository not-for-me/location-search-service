package kr.notforme.lss.support.http;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientFilters {
    public static ExchangeFilterFunction requestLogFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                         .forEach((name, values) -> values
                                 .forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    public static ExchangeFilterFunction responseLogFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response Status {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                          .forEach((name, values) -> values
                                  .forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientResponse);
        });
    }
}
