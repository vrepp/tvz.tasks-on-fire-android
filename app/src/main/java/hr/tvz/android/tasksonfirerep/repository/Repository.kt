package hr.tvz.android.tasksonfirerep.repository

import hr.tvz.android.tasksonfirerep.model.BasicTask
import hr.tvz.android.tasksonfirerep.model.Login
import hr.tvz.android.tasksonfirerep.model.responses.*
import hr.tvz.android.tasksonfirerep.network.API
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Repository {
    private val client by lazy { API.create() }

    fun login(username: String, password: String): Observable<LoginTokenResponse> {
        val data = Login(username, password)
        return client.login(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun register(username: String, password: String): Observable<BasicResponse> {
        val data = Login(username, password)
        return client.register(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllTask(): Observable<TaskListResponse> {
        return client.getAllTask()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createTask(title: String, description: String): Observable<TaskCreateResponse> {
        val data = BasicTask(title, description)
        return client.createTask(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateTask(id: Int, title: String, description: String): Observable<TaskUpdateResponse> {
        val data = BasicTask(title, description)
        return client.update(data, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteTask(id: Int): Observable<TaskDeleteResponse> {
        return client.delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}