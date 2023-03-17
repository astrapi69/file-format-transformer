package com.github.astrapi69.fileformattransformer.action

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.yaml.PropertiesToYamlExtensions

class PropertiesFileToYamlFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val yaml = PropertiesToYamlExtensions.toYamlString(loadText)
            WriteAction.run<Throwable> {
                if (ApplicationSettingsState.instance.newFile) {
                    val createChildSequent: VirtualFile = VfsUtil.createChildSequent(event.project,
                        it.parent, it.nameWithoutExtension, "yaml")
                    VfsUtil.saveText(createChildSequent, yaml)
                } else {
                    VfsUtil.saveText(it, yaml)
                    it.rename(it, it.nameWithoutExtension + ".yaml")
                }
            }
        }

    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            event.presentation.isEnabledAndVisible = it.extension.equals("properties")
        }
    }
}