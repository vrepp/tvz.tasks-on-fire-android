package hr.tvz.android.tasksonfirerep.ui.task

interface TaskInteractor {
    fun getAllTask()
    fun createTask(title: String, description: String)
    fun updateTask(id: String, title: String, description: String)
    fun deleteTask(id: String)
}