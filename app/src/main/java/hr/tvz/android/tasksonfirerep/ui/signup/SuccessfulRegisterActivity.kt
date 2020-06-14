package hr.tvz.android.tasksonfirerep.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.ui.login.LoginActivity

class SuccessfulRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfull_register)
    }

    fun backToSignin(view: View) {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
