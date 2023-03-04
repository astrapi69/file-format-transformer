package com.github.astrapi69.fileformattransformer.setting

import com.intellij.execution.console.ConsoleEncodingComboBox
import com.intellij.execution.console.ConsoleFoldingSettings
import com.intellij.execution.impl.ConsoleBuffer
import com.intellij.ide.ui.UISettings
import com.intellij.openapi.application.ApplicationBundle
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.vfs.encoding.EncodingManager
import com.intellij.ui.JBColor
import java.awt.BorderLayout
import javax.swing.*


public class ApplicationSettingsConfigurable: SearchableConfigurable, Configurable {

    private var myMainComponent: JPanel? = null
    private var myCbUseSoftWrapsAtConsole: JCheckBox? = null
    private var myCommandsHistoryLimitField: JTextField? = null
    private var myCbOverrideConsoleCycleBufferSize: JCheckBox? = null
    private var myEncodingComboBox: ConsoleEncodingComboBox? = null
    private var myConsoleCycleBufferSizeField: JTextField? = null
    private var myConsoleBufferSizeWarningLabel: JLabel? = null

    private val mySettings = ConsoleFoldingSettings.getSettings()
    override fun createComponent(): JComponent? {
        TODO("Not yet implemented")
        if (myMainComponent == null) {
            myMainComponent = JPanel(BorderLayout())
            myCbUseSoftWrapsAtConsole =
                JCheckBox(ApplicationBundle.message("checkbox.use.soft.wraps.at.console"), false)
            myCommandsHistoryLimitField = JTextField(4)
            myCbOverrideConsoleCycleBufferSize = JCheckBox(
                ApplicationBundle.message(
                    "checkbox.override.console.cycle.buffer.size",
                    (ConsoleBuffer.getLegacyCycleBufferSize() / 1024).toString()
                ), false
            )
            myCbOverrideConsoleCycleBufferSize!!.addChangeListener { e ->
                myConsoleCycleBufferSizeField?.setEnabled(myCbOverrideConsoleCycleBufferSize!!.isSelected())
                myConsoleBufferSizeWarningLabel?.setVisible(myCbOverrideConsoleCycleBufferSize!!.isSelected())
            }
            myConsoleCycleBufferSizeField = JTextField(4)
            myConsoleBufferSizeWarningLabel = JLabel()
            myConsoleBufferSizeWarningLabel!!.setForeground(JBColor.red)
        }
        return myMainComponent
    }

    override fun isModified(): Boolean {
        TODO("Not yet implemented")

        val editorSettings: EditorSettingsExternalizable = EditorSettingsExternalizable.getInstance()
        var isModified: Boolean = false

        return isModified
    }

    override fun apply() {
        TODO("Not yet implemented")
        val editorSettings = EditorSettingsExternalizable.getInstance()
        val settingsManager = UISettings.getInstance()
        val uiSettings = settingsManager.state
        val encodingManager: EncodingManager = EncodingManager.getInstance()
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

    override fun getId(): String {
        TODO("Not yet implemented")
    }
}