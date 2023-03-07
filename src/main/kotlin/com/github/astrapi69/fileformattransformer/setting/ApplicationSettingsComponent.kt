package com.github.astrapi69.fileformattransformer.setting

import com.github.astrapi69.fileformattransformer.PluginBundle
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBRadioButton
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.ButtonGroup
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class ApplicationSettingsComponent {
    val panel: JPanel
    private val fillVerticallyPanel: JPanel
    private val lblRadioGroupLabel: JBLabel
    private val radioButtonGroup: ButtonGroup
    private val overwriteFileRadioButton: JBRadioButton
    private val newFileRadioButton: JBRadioButton
    private val myUserNameText = JBTextField()

    init {
        overwriteFileRadioButton = JBRadioButton(PluginBundle.message("setting.overwrite.existing.file.label"))
        newFileRadioButton = JBRadioButton(PluginBundle.message("setting.create.new.file.label"))
        radioButtonGroup = ButtonGroup()
        radioButtonGroup.add(overwriteFileRadioButton)
        radioButtonGroup.add(newFileRadioButton)
        lblRadioGroupLabel = JBLabel(PluginBundle.message("setting.transform.label"))
        fillVerticallyPanel = JPanel()
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(lblRadioGroupLabel, newFileRadioButton, 1, true)
            .addComponent(overwriteFileRadioButton, 1)
            .addComponentFillVertically(fillVerticallyPanel, 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = myUserNameText

    var newFileFlag: Boolean
        get() = newFileRadioButton.model.isSelected
        set(newFlag) {
            newFileRadioButton.model.isSelected = newFlag
        }
    var overwriteFileFlag: Boolean
        get() = overwriteFileRadioButton.model.isSelected
        set(newFlag) {
            overwriteFileRadioButton.model.isSelected = newFlag
        }
}