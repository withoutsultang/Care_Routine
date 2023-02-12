package com.example.careroutine

import java.io.IOException
import java.util.*

// 로그인 자격 증명을 포함한 인증 처리, 사용자 정보 검색
class LoginDataSource {
    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "genie")
            // Result에 반환
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            // Result에 반환
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}