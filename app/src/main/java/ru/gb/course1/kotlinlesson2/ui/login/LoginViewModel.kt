package ru.gb.course1.kotlinlesson2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel(), LoginContract.ViewModel {
    override val emailValidationErrorLiveData = MutableLiveData<Boolean>()
    override val loginEnabledLiveData = MutableLiveData<Boolean>()
    override val emailLiveData = MutableLiveData<String>()
    override val screenStateLiveData = MutableLiveData<LoginContract.LoginState>()

    override fun onEmailChanged(email: String) {
        emailLiveData.postValue(email)
        val isValid = isEmailValid(email)
        loginEnabledLiveData.postValue(isValid)
        emailValidationErrorLiveData.postValue(!isValid)
    }

    override fun onLogin(email: String, password: String) {
        val state = if (checkCredentials(email, password)) {
            LoginContract.LoginState.SUCCESS
        } else {
            LoginContract.LoginState.WRONG_PASSWORD
        }
        screenStateLiveData.postValue(state)
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        return email == password
    }

//    private fun <T> LiveData<T>.post(value: T) {
//        if (this is MutableLiveData<T>) {
//            this.postValue(value)
//        } else {
//            throw IllegalStateException("It is note Mutable!")
//        }
//    }

}
