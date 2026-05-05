package com.algaworks.text_processor_service.text.processor.service.domain.service;

import com.algaworks.text_processor_service.text.processor.service.api.model.input.PostProcessingInput;
import com.algaworks.text_processor_service.text.processor.service.api.model.output.PostProcessingResultOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.algaworks.text_processor_service.text.processor.service.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_POST_PROCESSING_RESULT;

@Service
@RequiredArgsConstructor
public class TextProcessorService {
    private final RabbitTemplate rabbitTemplate;

    public void postProcessingResult(PostProcessingInput postProcessInput) {
        long wordCount = Arrays.stream(postProcessInput.getPostBody().split("\\s+"))
                .filter(p -> !p.isEmpty())
                .count();

        BigDecimal calculatedValue = BigDecimal.valueOf(wordCount)
                .multiply(new BigDecimal("0.10"));

        PostProcessingResultOutput postProcessOutput = PostProcessingResultOutput.builder()
                .id(postProcessInput.getId())
                .wordCount(wordCount)
                .calculatedValue(calculatedValue)
                .build();

        rabbitTemplate.convertAndSend(QUEUE_POST_PROCESSING_RESULT, postProcessOutput);
    }
}
