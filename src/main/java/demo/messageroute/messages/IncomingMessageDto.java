package demo.messageroute.messages;

import jakarta.validation.constraints.NotBlank;

public record IncomingMessageDto(
        @NotBlank(message = "Customer field can not be empty.")
        String customer,
        String tier
) {}
