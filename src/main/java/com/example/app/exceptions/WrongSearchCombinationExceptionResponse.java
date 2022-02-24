package com.example.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class WrongSearchCombinationExceptionResponse {

    private String wrongSearchQuery;

}
