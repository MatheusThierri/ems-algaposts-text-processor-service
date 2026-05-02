package com.algaworks.text_processor_service.text.processor.service.api.model.input;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostProcessingInput {
    private UUID id;
    private String postBody;
}
