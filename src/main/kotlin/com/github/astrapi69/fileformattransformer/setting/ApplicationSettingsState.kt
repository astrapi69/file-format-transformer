package com.github.astrapi69.fileformattransformer.setting

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(name = "com.github.astrapi69.fileformattransformer.setting.AppSettingsState", storages = [Storage("SdkSettingsPlugin.xml")])
class ApplicationSettingsState : PersistentStateComponent<ApplicationSettingsState?> {
    @JvmField
    var userId = ""
    @JvmField
    var ideaStatus = false
    override fun getState(): ApplicationSettingsState? {
        return this
    }

    override fun loadState(state: ApplicationSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        @JvmStatic
        val instance: ApplicationSettingsState
            get() = ApplicationManager.getApplication().getService(
                ApplicationSettingsState::class.java
            )
    }
}