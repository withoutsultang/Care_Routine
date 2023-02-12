package com.example.careroutine.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.careroutine.LoginViewModel
import com.example.careroutine.LoginViewModelFactory
import com.example.careroutine.R
import com.example.careroutine.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//      Login 데이터 바인딩
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userid = binding.userid
        val password = binding.password
        val login = binding.login


//      LoginActivty > onCreate() 의 ViewModel 인스턴스 생성
        LoginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)


//      LoginActivity > oncreate() 에서 loginFormState 관찰(observe)
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

//          id/password 모두 유효하지 않으면 로그인 버튼 비활성화
            login.isEnabled = loginState.isDataValid

//          id가 유효하지 않으면 에러 메시지 표시
            if (loginState.useridError != null) {
                userid.error = getString(loginState.usernameError)
            }

//          비밀번호가 유효하지 않으면 에러 메시지 표시
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })


//      LoginActivity > oncreate() 에서 loginResult 관찰(observe)
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

//          로그인 성공 시 로그인 활동 완료 및 삭제
            finish()
        })


//      userid에 입력된 값에 변화가 있으면 viewModel의 loginDataChanged 메소드를 호출해 유효한 값인지 확인
        userid.afterTextChanged {
            loginViewModel.loginDataChanged(
                userid.text.toString(),
                password.text.toString()
            )
        }


//      password에 입력된 값에 변화가 있으면 viewModel의 loginDataChanged 메소드를 호출해 유효한 값인지 확인
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    userid.text.toString(),
                    password.text.toString()
                )
            }


//         키보드 완료 버튼 눌렀을 때 동작 정의
           setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            userid.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }


//         login 버튼 클릭시 viewModel에서 로그인 시도
           login.setOnClickListener {
                loginViewModel.login(userid.text.toString(), password.text.toString())
            }
        }
    }

//  로그인 성공 시 토스트 메시지 표시, 로그인 액티비티 종료
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    //  로그인 실패 시 토스트 메시지 표시
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    // EditText 값에 변화가 있는지 확인
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
    }
}