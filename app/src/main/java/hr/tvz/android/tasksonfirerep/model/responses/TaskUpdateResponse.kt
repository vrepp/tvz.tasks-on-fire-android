package hr.tvz.android.tasksonfirerep.model.responses

import hr.tvz.android.tasksonfirerep.model.Task

data class TaskUpdateResponse (
    val message: String,
    val task: Task
)