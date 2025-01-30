package ua.mykola.thoughtflow.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorMessage {
    private int status;
    private String message;
}
