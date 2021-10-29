package ru.gb.course1.kotlinlesson2.ui.login

class LoginContract {
    enum class ErrorCode {
        NO_INTERNET, WRONG_PASSWORD
    }

    interface View {
        fun showError(errorCode: ErrorCode)
        fun showEmailValidationError()
        fun enableLogin(enable: Boolean)
        fun showLoginSuccess()
        fun setEmail(email: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onEmailChanged(email: String)
        fun onLogin(email: String, password: String)
    }
}