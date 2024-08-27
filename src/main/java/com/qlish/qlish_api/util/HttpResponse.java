package com.qlish.qlish_api.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Africa/Lagos")
    //TODO: find an international implementation
    private Date timestamp = new Date();
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message){
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.reason = reason;
        this.message = message;
    }
}
