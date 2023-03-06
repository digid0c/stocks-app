package org.example.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RestResponseExceptionModel {

    private final List<String> errors;
}
