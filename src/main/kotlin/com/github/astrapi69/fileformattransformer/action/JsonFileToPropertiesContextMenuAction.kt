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
import io.github.astrapi69.collection.properties.PropertiesExtensions
import io.github.astrapi69.gson.JsonToPropertiesExtensions
import io.github.astrapi69.io.StringOutputStream
import io.github.astrapi69.io.file.FileExtension

class JsonFileToPropertiesContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val jsonString = VfsUtilCore.loadText(it)
            val properties = JsonToPropertiesExtensions.toProperties(jsonString)
            val outputStream = StringOutputStream()
            PropertiesExtensions.export(properties, outputStream)
            val propertiesAsString = outputStream.toString()

            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val nextAvailableName = VfsUtil.getNextAvailableName(it.parent, it.nameWithoutExtension, FileExtension.PROPERTIES.extensionOnly)
                    val createChildData = it.parent.createChildData(event.project, nextAvailableName)
                    VfsUtil.saveText(createChildData, propertiesAsString)
                } else {
                    VfsUtil.saveText(it, propertiesAsString)
                    it.rename(it, it.nameWithoutExtension + FileExtension.PROPERTIES.extension)
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