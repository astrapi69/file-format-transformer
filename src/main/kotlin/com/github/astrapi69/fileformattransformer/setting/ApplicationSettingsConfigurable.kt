package com.github.astrapi69.fileformattransformer.setting

import com.github.astrapi69.fileformattransformer.setting.ApplicationSettingsState.Companion.instance
import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class ApplicationSettingsConfigurable : Configurable {
    private var mySettingsComponent: AppSettingsComponent? = null

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String? {
        return "SDK: Application Settings Example"
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = instance
        var modified = mySettingsComponent!!.userNameText != settings.userId
        modified = modified or (mySettingsComponent!!.ideaUserStatus != settings.ideaStatus)
        return modified
    }

    override fun apply() {
        val settings = instance
        settings.userId = mySettingsComponent!!.userNameText
        settings.ideaStatus = mySettingsComponent!!.ideaUserStatus
    }

    override fun reset() {
        val settings = instance
        mySettingsComponent!!.userNameText = settings.userId
        mySettingsComponent!!.ideaUserStatus = settings.ideaStatus
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}