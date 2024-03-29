package com.github.astrapi69.fileformattransformer.action

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.json.XmlToJsonExtensions


class XmlFileToJsonFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val json = XmlToJsonExtensions.toJson(loadText)

            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val createChildSequent: VirtualFile = VfsUtil.createChildSequent(event.project,
                        it.parent, it.nameWithoutExtension, "json")
                    VfsUtil.saveText(createChildSequent, json)
                } else {
                    VfsUtil.saveText(it, json)
                    it.rename(it, it.nameWithoutExtension + ".json")
                }
            }
        }
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            event.presentation.isEnabledAndVisible = it.extension.equals("xml")
        }
    }
}