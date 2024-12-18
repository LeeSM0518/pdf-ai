package io.tutorial.pdfai.adapter.out

import io.tutorial.pdfai.application.port.out.AiSummaryPort
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class OpenAiSummaryAdapter(
    builder: ChatClient.Builder
): AiSummaryPort {

    private val chatClient =
        builder
            .defaultSystem(ClassPathResource("open-ai-summary-prompt.txt"))
            .defaultAdvisors(SimpleLoggerAdvisor())
            .build()

    override suspend fun summarize(content: String): String =
        chatClient
            .prompt()
            .user(content)
            .call()
            .content()
            ?: ""

}
