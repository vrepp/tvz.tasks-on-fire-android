package hr.tvz.android.tasksonfirerep.ui.task

import android.annotation.SuppressLint
import hr.tvz.android.tasksonfirerep.repository.Repository
import hr.tvz.android.tasksonfirerep.util.ApiError

class TaskInteractorImpl(private val presenter: TaskPresenter) : TaskInteractor {
    @SuppressLint("CheckResult")
    override fun getAllTask() {
        Repository.getAllTask()
            .subscribe({ list ->
                presenter.onGetAllTaskSuccess(list.tasks)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi.code, errorApi.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun createTask(title: String, description: String) {
        Repository.createTask(title, description)
            .subscribe({ taskCreated ->
                presenter.onTaskCreatedSuccess(taskCreated.task)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi.code, errorApi.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun updateTask(id: String, title: String, description: String) {
        Repository.updateTask(id, title, description)
            .subscribe({ taskUpdated ->
                presenter.onTaskUpdatedSuccess(taskUpdated.task)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi.code, errorApi.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun deleteTask(id: String) {
        Repository.deleteTask(id)
            .subscribe({ taskDeleted ->
                presenter.onTaskDeletedSuccess(taskDeleted.taskId)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi.code, errorApi.message)
            })
    }
}