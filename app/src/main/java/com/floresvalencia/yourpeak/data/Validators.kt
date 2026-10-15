package com.floresvalencia.yourpeak.data

import android.util.Patterns

object Validators {
    fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    fun isValidPassword(password: String) = password.length >= 6
}
