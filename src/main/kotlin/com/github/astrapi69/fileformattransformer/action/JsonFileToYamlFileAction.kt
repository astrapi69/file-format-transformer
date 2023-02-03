package com.github.astrapi69.fileformattransformer.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Consumer
import io.github.astrapi69.json.JsonToYamlExtensions

public class JsonFileToYamlFileAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        showFileDialog(event)
    }

    private fun showFileDialog(event: AnActionEvent) {

        val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("json")
            FileChooserDescriptor(
            true,
            false,
            false,
            false,
            false,
            false
        )
        fileChooserDescriptor.title = "Choose File to transform"
        fileChooserDescriptor.description = "json to yaml"

        FileChooser.chooseFile(fileChooserDescriptor, event.project, null, Consumer {
            val loadText = VfsUtilCore.loadText(it)
            val yaml = JsonToYamlExtensions.toYaml(loadText)
            var createChildSequent: VirtualFile = VfsUtil.createChildSequent(event.project,
                it.parent, it.nameWithoutExtension, "yaml")
            VfsUtil.saveText(createChildSequent, yaml)

            Messages.showMessageDialog(event.project, "The json file is successfully transformed", "Transform to yaml",
                Messages.getInformationIcon())
        })
    }

}