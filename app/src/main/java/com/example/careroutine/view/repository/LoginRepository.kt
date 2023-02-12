package com.example.careroutine.view.repository

import com.example.careroutine.LoggedInUser

class LoginRepository {
    fun login(userid: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(userid, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }
}