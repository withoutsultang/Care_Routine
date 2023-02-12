package com.example.careroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.careroutine.view.repository.LoginRepository

/*
* 비어 있지 않은 생성자를 가진 LoginViewModel을 인스턴스화 함
* ViewModel Provider
*/

class LoginViewModelFactory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(
                    loginRepository = LoginRepository(
                        dataSource = LoginDataSource()
                    )
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}