package com.example.careroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careroutine.view.repository.LoginRepository

// LoginRepository 생성자
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

//  변수 선언
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(userid: String, password: String) {
        // 별도의 비동기 작업에서 시작할 수 있음
        val result = loginRepository.login(userid, password)

        if (result is Result<Any?>.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

//  id, password 유효성 검사 후 loginForm에 저장
    fun loginDataChanged(userid: String, password: String) {
        if (!isUserIdValid(userid)) {
            _loginForm.value = LoginFormState(useridError = R.string.invalid_userid)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }


//  id 유효성 검사
    private fun isUserIdValid(userid: String): Boolean {
//        return if (userid.contains('@')) {
//            Patterns.EMAIL_ADDRESS.matcher(userid).matches()
//        } else {
//            userid.isNotBlank()
//        }
    }

//  password 유효성 검사
    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 5
    }
}