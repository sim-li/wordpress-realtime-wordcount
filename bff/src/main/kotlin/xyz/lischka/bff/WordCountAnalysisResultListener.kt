package xyz.lischka.bff

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.messaging.simp.SimpMessagingTemplate


@Component
class WordCountAnalysisResultListener {
    @Autowired
    private lateinit var template: SimpMessagingTemplate

    @KafkaListener(topics = ["analysis-json"])
    fun processMessage(ev: WordCountAnalysisResult) {
        System.out.println("==> Sending a fancy message")
        System.out.println(ev)
        template.convertAndSend("/topic/group", ev)
    }
}
