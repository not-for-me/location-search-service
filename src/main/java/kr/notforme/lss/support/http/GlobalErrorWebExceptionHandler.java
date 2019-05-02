package kr.notforme.lss.support.http;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import kr.notforme.lss.support.exceptions.LssApiException;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(
            ErrorAttributes errorAttributes,
            ResourceProperties resourceProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);

        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, false);

        return handleError(errorPropertiesMap, request);
    }

    private Mono<ServerResponse> handleError(Map<String, Object> errorAttributes, ServerRequest request) {
        Throwable ex = getError(request);
        errorAttributes.put("exception", ex.getClass().getSimpleName());

        // Overwrite if the throwable is deined service exception.
        if (ex instanceof LssApiException) {
            LssApiException apiException = (LssApiException) ex;
            errorAttributes.put("error", apiException.getStatus().getReasonPhrase());
            errorAttributes.put("status", apiException.getStatus().value());

            if (apiException.getMessage() != null) {
                errorAttributes.put("message", apiException.getMessage());
            }
        }

        Integer statusCode = (Integer) errorAttributes.get("status");

        return ServerResponse.status(HttpStatus.valueOf(statusCode))
                             .contentType(MediaType.APPLICATION_JSON_UTF8)
                             .body(BodyInserters.fromObject(errorAttributes));
    }
}

