package hr.tvz.android.tasksonfirerep.util

import com.google.android.material.textfield.TextInputLayout
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.TasksOnFire

class Validator {
    var validations = mutableListOf<TextInputLayout>()

    fun add(value: TextInputLayout): Validator{
        validations.add(value)
        return this
    }
    private fun validateForNotEmpty(): Boolean {
        val check = validations.filter {
            it.editText?.text.isNullOrEmpty()
        }

        check.map {
            it.error = TasksOnFire.applicationContext().resources.getString(
                R.string.error_empty)
        }

        return check.isEmpty()
    }

    fun result(): Boolean {
        validations.map { it.error = null }
        return validateForNotEmpty()
    }

}