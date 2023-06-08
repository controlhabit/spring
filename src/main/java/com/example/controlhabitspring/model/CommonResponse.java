package com.example.controlhabitspring.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse {
    private int count;
    private T data;
    private HttpStatus code;

    public CommonResponse(T data) {
        this.code = HttpStatus.OK;
        this.data = data;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else if (data instanceof Page) {
			this.count = ((Page<?>) data).getNumberOfElements();
		} else {
            this.count = 1;
        }
    }
    
}
