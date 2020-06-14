package hr.tvz.android.tasksonfirerep.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.ui.signup.SignupActivity
import hr.tvz.android.tasksonfirerep.ui.task.TasksActivity
import hr.tvz.android.tasksonfirerep.util.MySharedPreferences
import hr.tvz.android.tasksonfirerep.util.Validator
import hr.tvz.android.tasksonfirerep.util.color
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var presenter: LoginPresenter
    private val validator = Validator()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        validator
            .add(inputUsername)
            .add(inputPassword)

        presenter = LoginPresenterImpl(this)
        checkLoginToken()
        setupToolbar()
        setupListener()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarLogin)
        supportActionBar?.title = resources.getString(R.string.wellcome)
    }

    private fun setupListener() {
        inputPassword.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signin(v)
                true
            }
            false
        }
    }

    private fun checkLoginToken() {
        val token = MySharedPreferences.getToken()
        if(token!!.isNotEmpty()) {
            val intent = Intent(this, TasksActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun onSuccess() {
        val intent = Intent(this, TasksActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onError(code: Int, message: String) {
        Snackbar.make(loginCoordinator, message, Snackbar.LENGTH_LONG)
            .color(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }

    fun signin(view: View) {
        val email = inputUsername.editText?.text.toString()
        val password = inputPassword.editText?.text.toString()

        if (validator.result()) {
//            presenter.login(username, password)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.getIdToken(true)?.addOnSuccessListener{
                            MySharedPreferences.saveToken(it.token!!)
                            Log.d("TOKEN", it.token!!)
                            presenter.onSuccess()
                        }
                    } else {
                        presenter.onError(401, "Authentication failed")
                    }
                }
        }
    }

    fun signup(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}
