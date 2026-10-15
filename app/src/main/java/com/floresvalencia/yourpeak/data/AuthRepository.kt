package com.floresvalencia.yourpeak.data

import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(fullName: String, email: String, password: String): Result<Unit>
    suspend fun sendResetCode(email: String): Result<Unit>
    suspend fun verifyCode(email: String, code: String): Result<Unit>
    suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
}

/** Implementación simulada; reemplázala por tu backend (Retrofit, Firebase, etc.). */
object FakeAuthRepository : AuthRepository {
    private const val NETWORK_DELAY = 900L
    private const val DEMO_CODE = "123456"

    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(NETWORK_DELAY)
        return Result.success(Unit)
    }

    override suspend fun register(fullName: String, email: String, password: String): Result<Unit> {
        delay(NETWORK_DELAY)
        return Result.success(Unit)
    }

    override suspend fun sendResetCode(email: String): Result<Unit> {
        delay(NETWORK_DELAY)
        return Result.success(Unit)
    }

    override suspend fun verifyCode(email: String, code: String): Result<Unit> {
        delay(NETWORK_DELAY)
        return if (code == DEMO_CODE) Result.success(Unit)
        else Result.failure(IllegalArgumentException("Código incorrecto (demo: $DEMO_CODE)"))
    }

    override suspend fun resetPassword(email: String, newPassword: String): Result<Unit> {
        delay(NETWORK_DELAY)
        return Result.success(Unit)
    }
}
