package ru.kama_diesel.corp_portal_mobile.di

import android.content.Context
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.AndroidPreferences
import ru.kama_diesel.corp_portal_mobile.common.data.preferences.IPreferences

fun getPreferences(appContext: Context): IPreferences {
    val preferencesName = "${appContext.packageName}_preferences"
    val sharedPreferences = appContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    return AndroidPreferences(sharedPreferences)
}