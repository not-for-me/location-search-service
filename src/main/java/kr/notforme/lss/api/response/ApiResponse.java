package kr.notforme.lss.api.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponse<T> {
    protected int code;
    protected String message;
    private T data;

    public static <T> ApiResponse<T> of(T data) {
        final ApiResponse<T> responseView = new ApiResponse<>();
        responseView.setCode(HttpStatus.OK.value());
        responseView.setMessage(HttpStatus.OK.getReasonPhrase());
        responseView.setData(data);

        return responseView;
    }
}
