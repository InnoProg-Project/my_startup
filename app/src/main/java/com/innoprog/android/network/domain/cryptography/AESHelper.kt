package com.innoprog.android.network.domain.cryptography

interface AESHelper {
    fun generateKey()

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(encryptedData: ByteArray): ByteArray
}