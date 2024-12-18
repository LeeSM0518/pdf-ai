package io.tutorial.pdfai.domain

import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper

class Pdf(
    private val path: Path,
) {

    val contents: Flow<String> =
        flow {
            path.inputStream().use { inputStream ->
                PDDocument.load(inputStream).use { document ->
                    val stripper = PDFTextStripper()
                    val totalPages = document.numberOfPages
                    (1..totalPages).forEach { page ->
                        stripper.startPage = page
                        stripper.endPage = page
                        emit(stripper.getText(document).filter())
                    }
                }
            }
        }

    private fun String.filter(): String =
        this.replace(combinedRegex) { matchResult ->
            when {
                matchResult.groups[1] != null -> ""
                matchResult.groups[2] != null -> "\n"
                matchResult.groups[3] != null -> "\r"
                matchResult.groups[4] != null -> " "
                else -> matchResult.value
            }
        }.trim()

    companion object {
        private val combinedRegex = Regex("([^\\p{L}\\p{N}\\p{P}\\p{S}\\p{Z}\n\r])|(\\n+)|(\\r+)|( {2,})")
    }
}
