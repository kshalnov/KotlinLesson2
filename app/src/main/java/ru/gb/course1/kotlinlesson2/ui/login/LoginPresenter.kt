package ru.gb.course1.kotlinlesson2.ui.login

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var lastEmail: String = ""

    override fun attach(view: LoginContract.View) {
        this.view = view
        view.setEmail(lastEmail)
    }

    override fun detach() {
        this.view = null
    }

    override fun onEmailChanged(email: String) {
        lastEmail = email

        if (isEmailValid(email)) {
            view?.enableLogin(true)
        } else {
            view?.showEmailValidationError()
            view?.enableLogin(false)
        }
    }

    override fun onLogin(email: String, password: String) {
        if (checkCredentials(email, password)) {
            view?.showLoginSuccess()
        } else {
            view?.showError(LoginContract.ErrorCode.WRONG_PASSWORD)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        return email == password
    }
}