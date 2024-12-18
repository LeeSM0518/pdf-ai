package io.tutorial.pdfai.application.port.out

interface AiSummaryPort {

    suspend fun summarize(content: String): String

}
