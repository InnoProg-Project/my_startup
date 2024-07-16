package com.innoprog.android.network.domain.cryptography

interface RSAHelper {
    fun generateKeyPair()

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(encryptedData: ByteArray): ByteArray
}