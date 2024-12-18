package io.tutorial.pdfai.application.service

import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource

@SpringBootTest
class PdfSummaryServiceTest @Autowired constructor(
    private val service: PdfSummaryService,
) {

    @Test
    fun `PDF 파일을 요약할 수 있다`(): Unit = runBlocking {
        val content = service.summarize(ClassPathResource("test.pdf").file.toPath())
        println(content)
        assertThat(content).isNotEmpty()
    }

}
