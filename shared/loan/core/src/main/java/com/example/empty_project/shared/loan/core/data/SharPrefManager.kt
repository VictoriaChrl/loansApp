package com.example.empty_project.shared.loan.core.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

interface SharPrefManager {

    fun saveToken(token: String?)

    fun saveName(name: String)

    fun savePassword(password: String)

    fun getName(): String?

    fun getPassword(): String?

    fun getToken(): String?
}

class SharPrefManagerImpl @Inject constructor(
    context: Context
) : SharPrefManager {

    companion object {
        private const val AUTH_FILE = "Auth"
        private const val TOKEN = "Token"
        private const val NAME = "Name"
        private const val PASSWORD = "Password"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        AUTH_FILE,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(token: String?) {
            sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    override fun saveName(name: String) {
        sharedPreferences.edit().putString(NAME, name).apply()
    }

    override fun savePassword(password: String) {
        sharedPreferences.edit().putString(PASSWORD, password).apply()
    }

    override fun getName(): String? {
        return sharedPreferences.getString(NAME, null)
    }

    override fun getPassword(): String? {
        return sharedPreferences.getString(PASSWORD, null)
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

}
