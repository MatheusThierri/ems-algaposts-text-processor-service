package com.algaworks.text_processor_service.text.processor.service.infrastructure.rabbitmq;

import com.algaworks.text_processor_service.text.processor.service.api.model.input.PostProcessingInput;
import com.algaworks.text_processor_service.text.processor.service.domain.service.TextProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.algaworks.text_processor_service.text.processor.service.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_POST_PROCESSING;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final TextProcessorService textProcessorService;

    @RabbitListener(queues = QUEUE_POST_PROCESSING, concurrency = "2-3")
    @SneakyThrows
    public void handlePostProcessingResult(@Payload PostProcessingInput postProcessingInput) {
        textProcessorService.postProcessingResult(postProcessingInput);
        Thread.sleep(Duration.ofSeconds(5));
    }
}
