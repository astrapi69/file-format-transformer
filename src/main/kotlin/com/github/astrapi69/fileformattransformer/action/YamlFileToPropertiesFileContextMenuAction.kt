package com.github.astrapi69.fileformattransformer.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.github.astrapi69.collection.properties.PropertiesExtensions
import io.github.astrapi69.io.StringOutputStream
import io.github.astrapi69.json.JsonToYamlExtensions
import io.github.astrapi69.yaml.YamlToPropertiesExtensions
import io.github.astrapi69.yaml.YamlToPropertiesExtensions.toTreeMap
import java.io.OutputStream


class YamlFileToPropertiesFileContextMenuAction: AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val it: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            val loadText = VfsUtilCore.loadText(it)
            val properties = YamlToPropertiesExtensions.toPropertiesFromYamlString(loadText);
            val outputStream = StringOutputStream()
            PropertiesExtensions.export(properties, outputStream)

            WriteAction.run<Throwable> {
                val createChildSequent: VirtualFile = VfsUtil.createChildSequent(event.project,
                    it.parent, it.nameWithoutExtension, "properties")
                VfsUtil.saveText(createChildSequent, outputStream.toString()) }
        }
    }

    override fun update(event: AnActionEvent) {
        val it = event.getData(CommonDataKeys.VIRTUAL_FILE)
        if(it != null) {
            event.presentation.isEnabledAndVisible = true
        }
    }
}