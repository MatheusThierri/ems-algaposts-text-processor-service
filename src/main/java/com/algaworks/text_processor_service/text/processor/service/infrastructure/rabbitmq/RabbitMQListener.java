package com.algaworks.text_processor_service.text.processor.service.infrastructure.rabbitmq;

import com.algaworks.text_processor_service.text.processor.service.api.model.input.PostProcessingInput;
import com.algaworks.text_processor_service.text.processor.service.domain.service.TextProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final TextProcessorService textProcessorService;
    public static final String QUEUE_POST_PROCESSING = "text-processor-service.post-processing.v1.q";

    @RabbitListener(queues = QUEUE_POST_PROCESSING, concurrency = "2-3")
    @SneakyThrows
    public void handlePostProcessingResult(@Payload PostProcessingInput postProcessingInput) {
        textProcessorService.postProcessingResult(postProcessingInput);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
