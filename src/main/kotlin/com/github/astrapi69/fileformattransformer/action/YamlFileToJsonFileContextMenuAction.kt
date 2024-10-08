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
import io.github.astrapi69.yaml.YamlToJsonExtensions

class YamlFileToJsonFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val json = YamlToJsonExtensions.toJson(loadText, true)

            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val nextAvailableName = VfsUtil.getNextAvailableName(it.parent, it.nameWithoutExtension, FileExtension.JSON.extensionOnly)
                    val createChildData = it.parent.createChildData(event.project, nextAvailableName)
                    VfsUtil.saveText(createChildData, json)
                } else {
                    VfsUtil.saveText(it, json)
                    it.rename(it, it.nameWithoutExtension + FileExtension.JSON.extension)
                }
            }
        }
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        val ymlExtension = FileExtension.YML.extensionOnly
        val yamlExtension = FileExtension.YAML.extensionOnly
        if(it != null) {
            event.presentation.isEnabledAndVisible = it.extension.equals(ymlExtension) || it.extension.equals(yamlExtension)
        }
    }
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

}