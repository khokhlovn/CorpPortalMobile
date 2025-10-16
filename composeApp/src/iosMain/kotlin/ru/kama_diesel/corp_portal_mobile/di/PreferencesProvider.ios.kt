package ru.kama_diesel.corp_portal_mobile.di

import platform.Foundation.NSUserDefaults
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IOsPreferences
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

fun getPreferences(userDefaults: NSUserDefaults): IPreferences {
    return IOsPreferences(userDefaults)
}