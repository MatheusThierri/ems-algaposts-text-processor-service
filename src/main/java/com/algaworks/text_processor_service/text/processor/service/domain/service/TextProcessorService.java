package com.algaworks.text_processor_service.text.processor.service.domain.service;

import com.algaworks.text_processor_service.text.processor.service.api.model.input.PostProcessingInput;
import com.algaworks.text_processor_service.text.processor.service.api.model.output.PostProcessingResultOutput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TextProcessorService {
    private final RabbitTemplate rabbitTemplate;
    public static final String FANOUT_EXCHANGE_POST_PROCESSING_RESULT_RECEIVED = "post-service.post-processing-result-received.v1.e";

    @Transactional
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

        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("postId", postProcessOutput.getId());
            return message;
        };

        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_POST_PROCESSING_RESULT_RECEIVED, "", postProcessOutput, messagePostProcessor);
    }
}
