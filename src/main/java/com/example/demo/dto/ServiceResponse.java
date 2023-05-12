package com.example.demo.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

@Data

public class ServiceResponse<T> {

    private HttpStatus status;

    private T data;
}
