package com.innoprog.android.network.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.innoprog.android.network.domain.AuthorizationDataRepository
import com.innoprog.android.network.domain.cryptography.AESHelper
import com.innoprog.android.network.domain.cryptography.RSAHelper
import com.innoprog.android.network.domain.cryptography.RootChecker
import java.util.Base64
import javax.inject.Inject

class AuthorizationDataRepositoryImpl @Inject constructor(
    private val context: Context,
    private val aesHelper: AESHelper,
    private val rsaHelper: RSAHelper,
    private val rootChecker: RootChecker
) : AuthorizationDataRepository {
    override fun execute(): String {
        return AuthHeader.data
    }

    override fun setData(login: String, password: String) {
        val authData = "$login:$password"
        saveCredentials(authData)
        AuthHeader.data = "Basic " + Base64.getEncoder().encodeToString(authData.toByteArray())
    }

    fun saveCredentials(credentials: String) {
        // Генерация ключей, если они еще не сгенерированы
        aesHelper.generateKey()
        rsaHelper.generateKeyPair()

        // Шифрование данных с использованием AES
        val aesEncryptedData = aesHelper.encrypt(credentials.toByteArray())

        // Шифрование AES-данных с использованием RSA
        val rsaEncryptedData = rsaHelper.encrypt(aesEncryptedData)

        // Сохранение данных в EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            AUTH_PREFS,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with(sharedPreferences.edit()) {
            putString(CREDENTIALS, rsaEncryptedData.toBase64())
            apply() // Применяем изменения
        }

        //Сохранение даты последней попытки входа
        val privateSharedPreferences =
            context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
        privateSharedPreferences.edit().putLong(LAST_LOGIN_TIME, System.currentTimeMillis()).apply()
    }

    override fun loadCredentials(): Pair<String, String>? {
        // Загрузка данных из EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            "auth_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val encryptedData = sharedPreferences.getString("credentials", null) ?: return null

        // Расшифровка данных с использованием RSA
        val rsaDecryptedData = rsaHelper.decrypt(encryptedData.fromBase64())

        // Расшифровка данных с использованием AES
        val aesDecryptedData = aesHelper.decrypt(rsaDecryptedData)

        // Разделение строки на логин и пароль
        val credentials = String(aesDecryptedData).split(":")
        return Pair(credentials[0], credentials[1]) // Возвращаем логин и пароль как пару
    }

    fun clearCredentials() {
        // Очистка данных в EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            AUTH_PREFS,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }

    override fun checkLastLoginTime() {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE)
        val lastLoginTime = sharedPreferences.getLong(LAST_LOGIN_TIME, 0L)
        if (rootChecker.isDeviceRooted(context)
            || (System.currentTimeMillis() - lastLoginTime > 30L * 24 * 60 * 60 * 1000)
        ) { // 30 дней
            sharedPreferences.edit().clear().apply() // Удаление данных
            clearCredentials()
        }
    }

    // Вспомогательные функции для конвертации в Base64 и из Base64
    fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)
    fun String.fromBase64(): ByteArray = Base64.getDecoder().decode(this)

    companion object {
        const val CREDENTIALS = "credentials"
        const val AUTH_PREFS = "auth_prefs"
        const val LAST_LOGIN_TIME = "last_login_time"
    }
}
