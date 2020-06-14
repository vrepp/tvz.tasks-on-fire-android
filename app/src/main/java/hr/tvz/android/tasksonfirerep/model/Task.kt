package hr.tvz.android.tasksonfirerep.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Task(
    var id: String,
    var title: String,
    var description: String,
    @SerializedName("created_at")
    var CreatedAt: Date) {

    fun clear() {
        id = ""
        title = ""
        description = ""
        CreatedAt = Date()
    }
}