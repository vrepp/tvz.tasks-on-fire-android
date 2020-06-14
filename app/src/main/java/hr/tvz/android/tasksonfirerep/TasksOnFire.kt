package hr.tvz.android.tasksonfirerep

import android.app.Application
import android.content.Context

class TasksOnFire : Application() {
    init {
       hr.tvz.android.tasksonfirerep.TasksOnFire.Companion.instance = this
    }

    companion object {
        private var instance: hr.tvz.android.tasksonfirerep.TasksOnFire? = null

        fun applicationContext(): Context {
            return hr.tvz.android.tasksonfirerep.TasksOnFire.Companion.instance!!.applicationContext
        }
    }
}