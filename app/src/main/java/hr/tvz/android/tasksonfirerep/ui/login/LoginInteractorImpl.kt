package hr.tvz.android.tasksonfirerep.ui.login

import android.annotation.SuppressLint
import hr.tvz.android.tasksonfirerep.repository.Repository
import hr.tvz.android.tasksonfirerep.util.ApiError
import hr.tvz.android.tasksonfirerep.util.MySharedPreferences

class LoginInteractorImpl(private val presenter: LoginPresenter):
    LoginInteractor {
    @SuppressLint("CheckResult")
    override fun login(username: String, password: String) {
        Repository.login(username, password)
            .subscribe({ result ->
                presenter.onSuccess()
                MySharedPreferences.saveToken(result.token)
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError.code, apiError.message)
            })
    }
}