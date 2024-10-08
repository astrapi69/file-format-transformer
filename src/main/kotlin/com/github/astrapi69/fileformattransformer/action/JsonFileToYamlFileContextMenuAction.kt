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
import io.github.astrapi69.json.JsonToYamlExtensions

class JsonFileToYamlFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val yaml = JsonToYamlExtensions.toYaml(loadText)
            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val nextAvailableName = VfsUtil.getNextAvailableName(it.parent, it.nameWithoutExtension, FileExtension.YAML.extensionOnly)
                    val createChildData = it.parent.createChildData(event.project, nextAvailableName)
                    VfsUtil.saveText(createChildData, yaml)
                } else {
                    VfsUtil.saveText(it, yaml)
                    it.rename(it, it.nameWithoutExtension + FileExtension.YAML.extension)
                }
            }
        }
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        val jsonExtension = FileExtension.JSON.extensionOnly
        if (it != null && jsonExtension == it.extension) {
            event.presentation.isEnabledAndVisible = true
        } else {
            event.presentation.isEnabledAndVisible = false
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

}