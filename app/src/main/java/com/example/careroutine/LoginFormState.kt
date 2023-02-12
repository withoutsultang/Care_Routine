package com.example.careroutine

data class LoginFormState(
    val useridError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
