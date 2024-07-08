package com.innoprog.android.network.data.cryptography

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

object RSAHelper {

    private const val KEY_ALIAS = "my_rsa_key" // Уникальный алиас для хранения ключа в хранилище
    private const val ANDROID_KEYSTORE = "AndroidKeyStore" // Имя хранилища ключей Android
    private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding" // Алгоритм шифрования

    // Функция для генерации пары ключей RSA (открытый и закрытый ключ)
    fun generateKeyPair() {
        val keyPairGenerator =
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .setKeySize(2048) // Размер ключа в битах
            .build()
        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.generateKeyPair() // Генерация и сохранение пары ключей в хранилище
    }

    // Получение закрытого ключа RSA из хранилища
    private fun getPrivateKey(): PrivateKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(KEY_ALIAS, null) as PrivateKey
    }

    // Получение открытого ключа RSA из хранилища
    private fun getPublicKey(): PublicKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getCertificate(KEY_ALIAS).publicKey
    }

    // Шифрование данных с использованием RSA
    fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())
        return cipher.doFinal(data) // Возвращаем зашифрованные данные
    }

    // Расшифровка данных с использованием RSA
    fun decrypt(encryptedData: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())
        return cipher.doFinal(encryptedData) // Возвращаем расшифрованные данные
    }
}
