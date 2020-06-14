package hr.tvz.android.tasksonfirerep.ui.signup

import android.annotation.SuppressLint
import hr.tvz.android.tasksonfirerep.repository.Repository
import hr.tvz.android.tasksonfirerep.util.ApiError

class SignupInteractorImpl(val presenter: SignupPresenter) :
    SignupInteractor {
    @SuppressLint("CheckResult")
    override fun signup(username: String, password: String) {
        Repository.register(username, password)
            .subscribe({ presenter.onSuccess() },
                { error ->
                    val httpError = ApiError(error)
                    presenter.onError(httpError.code, httpError.message)
                })
    }
}