package gr.aueb.cf.edu9app.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequestDTO(@NotNull String username, @NotNull String password) {
}
