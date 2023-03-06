package com.github.astrapi69.fileformattransformer.setting;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static com.github.astrapi69.fileformattransformer.PluginBundle.message;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

  private final JPanel myMainPanel;
  private final JBLabel lblRadioGroupLabel;
  private final JBRadioButton overwriteFileRadioButton;
  private final JBRadioButton newFileRadioButton;
  private final JBTextField myUserNameText = new JBTextField();
  private final JBCheckBox myIdeaUserStatus = new JBCheckBox(message("setting.transform.label"));

  public AppSettingsComponent() {
    this.overwriteFileRadioButton =  new JBRadioButton(message("setting.overwrite.existing.file.label"));
    this.newFileRadioButton =  new JBRadioButton(message("setting.create.new.file.label"));
    this.lblRadioGroupLabel = new JBLabel(message("setting.transform.label"));
    myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(lblRadioGroupLabel, myUserNameText, 1, false)
            .addComponent(myIdeaUserStatus, 1)
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
  }

  public JPanel getPanel() {
    return myMainPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return myUserNameText;
  }

  @NotNull
  public String getUserNameText() {
    return myUserNameText.getText();
  }

  public void setUserNameText(@NotNull String newText) {
    myUserNameText.setText(newText);
  }

  public boolean getIdeaUserStatus() {
    return myIdeaUserStatus.isSelected();
  }

  public void setIdeaUserStatus(boolean newStatus) {
    myIdeaUserStatus.setSelected(newStatus);
  }

}