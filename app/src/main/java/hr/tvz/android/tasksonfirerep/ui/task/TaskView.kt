package hr.tvz.android.tasksonfirerep.ui.task

import hr.tvz.android.tasksonfirerep.model.Task

interface TaskView {
    fun getAllTask()
    fun onGetAllTaskSuccess(list: List<Task>)
    fun onError(code: Int, message: String)
    fun showProgress()
    fun hideProgress()
    fun onTaskCreatedSuccess(task: Task)
    fun onTaskUpdatedSuccess(task: Task)
    fun onTaskDeletedSuccess(taskId: String)
}