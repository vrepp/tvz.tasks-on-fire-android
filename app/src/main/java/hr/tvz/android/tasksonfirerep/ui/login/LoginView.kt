package hr.tvz.android.tasksonfirerep.ui.login

interface LoginView {
    fun showProgress()
    fun hideProgress()
    fun onSuccess()
    fun onError(code: Int, message: String)
}