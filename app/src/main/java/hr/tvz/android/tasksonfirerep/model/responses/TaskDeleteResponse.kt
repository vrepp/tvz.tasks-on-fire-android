package hr.tvz.android.tasksonfirerep.model.responses

import hr.tvz.android.tasksonfirerep.model.Task

data class TaskDeleteResponse (
    val message: String,
    val task: Task
)