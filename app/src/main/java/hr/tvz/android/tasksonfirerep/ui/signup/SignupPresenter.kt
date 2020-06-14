package hr.tvz.android.tasksonfirerep.ui.signup

interface SignupPresenter {
    fun signup(username: String, password: String)
    fun onSuccess()
    fun onError(code: Int, error: String)
}