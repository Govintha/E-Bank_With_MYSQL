package com.easybank.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.swing.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String apiPath; // what is API path that client trying to invoke
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;


}
