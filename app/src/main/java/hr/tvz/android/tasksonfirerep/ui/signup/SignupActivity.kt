package hr.tvz.android.tasksonfirerep.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.util.Validator
import hr.tvz.android.tasksonfirerep.util.color
import kotlinx.android.synthetic.main.activity_singup.*

class SignupActivity : AppCompatActivity(), SignupView {
    lateinit var presenter: SignupPresenter
    private val validator = Validator()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        auth = FirebaseAuth.getInstance()

        validator
            .add(inputUsername)
            .add(inputPassword)

        presenter =
            SignupPresenterImpl(this)
        setupToolbar()
        setupListener()
    }

    fun setupToolbar() {
        setSupportActionBar(toolbarSignup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.signup)
    }

    private fun setupListener() {
        inputPassword.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signup(v)
                true
            }
            false
        }
    }

    fun signup(view: View) {
        val email = inputUsername.editText?.text.toString()
        val password = inputPassword.editText?.text.toString()

        if (validator.result()) {
//            presenter.signup(username, password)
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        presenter.onSuccess()
                    } else {
                        presenter.onError(401, "Error auth")
                    }
                }
        }
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun onSuccess() {
        val intent = Intent(this, SuccessfulRegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onError(code: Int, message: String) {
        Snackbar.make(signupCoordinator, message, Snackbar.LENGTH_LONG)
            .color(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }
}
