package ru.gb.course1.kotlinlesson2.ui.login

import androidx.lifecycle.LiveData

class LoginContract {
    enum class LoginState {
        IDLE, SUCCESS, NO_INTERNET, WRONG_PASSWORD
    }

    interface ViewModel {
        val emailLiveData: LiveData<String>
        val emailValidationErrorLiveData: LiveData<Boolean>
        val loginEnabledLiveData: LiveData<Boolean>
        val screenStateLiveData: LiveData<LoginState>

        fun onEmailChanged(email: String)
        fun onLogin(email: String, password: String)
    }
}