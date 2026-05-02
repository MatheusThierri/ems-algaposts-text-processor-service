package com.algaworks.text_processor_service.text.processor.service.api.model.output;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostProcessingResultOutput {
    private UUID id;
    private Long wordCount;
    private BigDecimal calculatedValue;
}
