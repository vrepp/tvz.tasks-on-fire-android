package hr.tvz.android.tasksonfirerep.ui.task

import hr.tvz.android.tasksonfirerep.model.Task

class TaskPresenterImpl(private val view: TaskView): TaskPresenter {
    private val interactor: TaskInteractor =
        TaskInteractorImpl(this)

    override fun getAllTask() {
        view.showProgress()
        interactor.getAllTask()
    }

    override fun onGetAllTaskSuccess(list: List<Task>) {
        view.hideProgress()
        view.onGetAllTaskSuccess(list)
    }

    override fun onError(code: Int, message: String) {
        view.hideProgress()
        view.onError(code, message)
    }

    override fun createTask(title: String, description: String) {
        interactor.createTask(title, description)
    }

    override fun onTaskCreatedSuccess(task: Task) {
        view.onTaskCreatedSuccess(task)
    }

    override fun editTask(id: String, title: String, description: String) {
        interactor.updateTask(id, title, description)
    }

    override fun onTaskUpdatedSuccess(task: Task) {
        view.onTaskUpdatedSuccess(task)
    }

    override fun deleteTask(id: String) {
        interactor.deleteTask(id)
    }

    override fun onTaskDeletedSuccess(taskId: String) {
        view.onTaskDeletedSuccess(taskId)
    }
}