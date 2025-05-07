package dev.m1guel.glacial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

}
