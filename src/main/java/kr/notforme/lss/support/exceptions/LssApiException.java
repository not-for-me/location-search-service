package kr.notforme.lss.support.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base api exception for LSS
 */
public class LssApiException extends RuntimeException {
    private static final long serialVersionUID = -5257844291705268850L;
    private final HttpStatus status;

    public LssApiException(HttpStatus status) {
        this.status = status;
    }

    public LssApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
