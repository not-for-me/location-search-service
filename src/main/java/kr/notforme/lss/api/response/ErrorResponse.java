package kr.notforme.lss.api.response;

import kr.notforme.lss.support.exceptions.LssApiException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends ApiResponse {
    private String detail;

    public static ErrorResponse fromApiException(LssApiException exception) {
        final ErrorResponse responseView = new ErrorResponse();
        responseView.setCode(exception.getStatus().value());
        responseView.setMessage(exception.getStatus().getReasonPhrase());
        responseView.setDetail(exception.getMessage());

        return responseView;
    }
}
