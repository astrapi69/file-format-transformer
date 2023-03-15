package com.github.astrapi69.fileformattransformer.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

import static com.github.astrapi69.fileformattransformer.PluginBundle.message;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent
{

  private final JPanel myMainPanel;
  private final JPanel fillVerticallyPanel;
  private final JBLabel lblRadioGroupLabel;

  private final ButtonGroup radioButtonGroup;
  private final JBRadioButton overwriteFileRadioButton;
  private final JBRadioButton newFileRadioButton;
  private final JBTextField myUserNameText = new JBTextField();
  private final JBCheckBox myIdeaUserStatus = new JBCheckBox(message("setting.transform.label"));

  public AppSettingsComponent() {
    this.overwriteFileRadioButton =  new JBRadioButton(message("setting.overwrite.existing.file.label"));
    this.newFileRadioButton =  new JBRadioButton(message("setting.create.new.file.label"));
    this.radioButtonGroup = new ButtonGroup();
    this.radioButtonGroup.add(overwriteFileRadioButton);
    this.radioButtonGroup.add(newFileRadioButton);
    this.lblRadioGroupLabel = new JBLabel(message("setting.transform.label"));
    this.fillVerticallyPanel = new JPanel();
    myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(lblRadioGroupLabel, newFileRadioButton, 1, true)
            .addComponent(overwriteFileRadioButton, 1)
            .addComponentFillVertically(this.fillVerticallyPanel, 0)
            .getPanel();

  }

  public JPanel getPanel() {
    return myMainPanel;
  }

}