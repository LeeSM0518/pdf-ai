package io.tutorial.pdfai.application.service

import io.tutorial.pdfai.application.port.out.AiSummaryPort
import io.tutorial.pdfai.domain.Pdf
import java.nio.file.Path
import kotlinx.coroutines.flow.reduce
import org.springframework.stereotype.Service

@Service
class PdfSummaryService(
    private val aiSummaryPort: AiSummaryPort,
) {

    suspend fun summarize(path: Path): String =
        Pdf(path)
            .contents
            .reduce { accumulator, value ->
                val text = accumulator + value
                if (text.length >= MAX_TOKEN_COUNT) aiSummaryPort.summarize(accumulator) + value
                else text
            }
            .let { aiSummaryPort.summarize(it) }

    companion object {
        const val MAX_TOKEN_COUNT = 4_000
    }
}
