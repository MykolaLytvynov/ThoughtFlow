package ua.mykola.thoughtflow.api.dto.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshResponse {
    private String newAccessToken;
}
