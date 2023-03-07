package com.github.astrapi69.fileformattransformer.action

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.json.JsonToYamlExtensions

class JsonFileToYamlFileAction: AnAction() {

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
        fileChooserDescriptor.title = "Choose File To Transform"
        fileChooserDescriptor.description = "Json to yaml"

        FileChooser.chooseFile(fileChooserDescriptor, event.project, null) {

            WriteAction.run<Throwable> {
                val loadText = VfsUtilCore.loadText(it)
                val yaml = JsonToYamlExtensions.toYaml(loadText)

                if(ApplicationSettingsState.instance.newFile) {
                    val createChildSequent: VirtualFile = VfsUtil.createChildSequent(
                        event.project,
                        it.parent, it.nameWithoutExtension, "yaml"
                    )
                    VfsUtil.saveText(createChildSequent, yaml)
                } else {
                    VfsUtil.saveText(it, yaml)
                }
            }

            Messages.showMessageDialog(
                event.project, "The json file is successfully transformed", "Transform To Yaml",
                Messages.getInformationIcon()
            )
        }
    }

}