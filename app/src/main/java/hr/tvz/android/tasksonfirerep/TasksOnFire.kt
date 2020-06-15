package hr.tvz.android.tasksonfirerep

import android.app.Application
import android.content.Context

class TasksOnFire : Application() {
    init {
       instance = this
    }

    companion object {
        private var instance: TasksOnFire? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}