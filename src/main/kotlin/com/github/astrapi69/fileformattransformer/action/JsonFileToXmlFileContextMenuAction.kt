package com.github.astrapi69.fileformattransformer.action

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.json.JsonToXmlExtensions

class JsonFileToXmlFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val xml = JsonToXmlExtensions.toXml(loadText)

            if(ApplicationSettingsState.instance.newFile) {
                WriteAction.run<Throwable> {
                    val createChildSequent: VirtualFile = VfsUtil.createChildSequent(event.project,
                        it.parent, it.nameWithoutExtension, "xml")
                    VfsUtil.saveText(createChildSequent, xml) }
            } else {
                WriteAction.run<Throwable> {
                    VfsUtil.saveText(it, xml)
                    it.rename(it, it.nameWithoutExtension + ".xml")
                }
            }
        }
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            event.presentation.isEnabledAndVisible = it.extension.equals("json")
        }
    }
}