package com.borjali.data.datasource.cache.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

import com.borjali.data.DataConstants.PREF_NAME
import com.borjali.domain.preference.CleanAppPreferences

/**
 * Implementation class of [CleanAppPreferences]
 */
@SuppressLint("CommitPrefEdits")
open class CleanAppPreferencesImpl(
    context: Context,
    prefName: String = PREF_NAME,
    isSecure: Boolean = true
) : CleanAppPreferences {

    private val securePref: SharedPreferences = if (isSecure) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            prefName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } else {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getString(key: String): String? {
        return securePref.getString(key, null)
    }

    override fun getString(key: String, default: String): String {
        return securePref.getString(key, null) ?: default
    }

    override fun setString(key: String, value: String) {
        securePref.edit().putString(key, value).apply()
    }

    override fun getBool(key: String, default: Boolean): Boolean {
        return securePref.getBoolean(key, default)
    }

    override fun setBool(key: String, value: Boolean) {
        securePref.edit().putBoolean(key, value).apply()
    }

    override fun getInt(key: String, default: Int): Int {
        return securePref.getInt(key, default)
    }

    override fun setInt(key: String, value: Int) {
        securePref.edit().putInt(key, value).apply()
    }

    override fun getLong(key: String, default: Long): Long {
        return securePref.getLong(key, default)
    }

    override fun setLong(key: String, value: Long) {
        securePref.edit().putLong(key, value).apply()
    }

    override fun remove(key: List<String>) {
        val edit = securePref.edit()
        key.forEach {
            edit.remove(it).apply()
        }
    }

    override fun clear() {
        securePref.edit().clear().apply()
    }

}
