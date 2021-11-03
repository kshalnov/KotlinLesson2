package ru.gb.course1.kotlinlesson2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel(), LoginContract.ViewModel {
    private val _emailValidationErrorLiveData = MutableLiveData<Boolean>()
    override val emailValidationErrorLiveData: LiveData<Boolean> = _emailValidationErrorLiveData

    private val _loginEnabledLiveData = MutableLiveData<Boolean>()
    override val loginEnabledLiveData: LiveData<Boolean> = _loginEnabledLiveData

    private val _emailLiveData = MutableLiveData<String>()
    override val emailLiveData: LiveData<String> = _emailLiveData

    private val _screenStateLiveData = MutableLiveData<LoginContract.LoginState>()
    override val screenStateLiveData: LiveData<LoginContract.LoginState> = _screenStateLiveData

    override fun onEmailChanged(email: String) {
        _emailLiveData.postValue(email)
        val isValid = isEmailValid(email)
        _loginEnabledLiveData.postValue(isValid)
        _emailValidationErrorLiveData.postValue(!isValid)
    }

    override fun onLogin(email: String, password: String) {
        val state = if (checkCredentials(email, password)) {
            LoginContract.LoginState.SUCCESS
        } else {
            LoginContract.LoginState.WRONG_PASSWORD
        }
        _screenStateLiveData.postValue(state)
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        return email == password
    }
}
