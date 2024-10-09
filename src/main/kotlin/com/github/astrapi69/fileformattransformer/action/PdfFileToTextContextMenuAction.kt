package com.github.astrapi69.fileformattransformer.action

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.io.file.FileExtension
import org.apache.pdfbox.Loader
import java.io.ByteArrayOutputStream
import org.apache.pdfbox.text.PDFTextStripper
import java.io.*

class PdfFileToTextContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {

            val pdfBytes = VfsUtilCore.loadBytes(it)
            val pdfToTextAsByteArray = pdfToText(pdfBytes)

            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val nextAvailableName = VfsUtil.getNextAvailableName(it.parent, it.nameWithoutExtension, FileExtension.TXT.extensionOnly)
                    val createChildData = it.parent.createChildData(event.project, nextAvailableName)
                    createChildData.getOutputStream(this).use { outputStream ->
                        outputStream.write(pdfToTextAsByteArray)
                    }
                } else {
                    it.getOutputStream(this).use { outputStream ->
                        outputStream.write(pdfToTextAsByteArray)
                    }
                    it.rename(it, it.nameWithoutExtension + FileExtension.TXT.extension)
                }
            }
        }
    }

    /**
     * Converts the entire PDF byte array to a text byte array.
     * The extraction starts from the first page and ends on the last page.
     *
     * @param pdfBytes the input PDF as a byte array
     * @return the generated text content as a byte array
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun pdfToText(pdfBytes: ByteArray): ByteArray {
        val textByteArrayOutputStream = ByteArrayOutputStream()

        Loader.loadPDF(pdfBytes).use { document ->
            val stripper = PDFTextStripper().apply {
                startPage = 1 // Start from the first page
                endPage = document.numberOfPages // Extract until the last page
            }

            BufferedWriter(OutputStreamWriter(textByteArrayOutputStream)).use { writer ->
                stripper.writeText(document, writer)
            }
        }

        return textByteArrayOutputStream.toByteArray()
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        val pdfExtension = FileExtension.PDF.extensionOnly
        if (it != null && pdfExtension == it.extension) {
            event.presentation.isEnabledAndVisible = true
        } else {
            event.presentation.isEnabledAndVisible = false
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

}