package hr.tvz.android.tasksonfirerep.util

import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiError (error: Throwable) {
    var message = "An error occurred"
    var code = 0
     init {
         if (error is HttpException) {
             val errorJsonString = error.response()?.errorBody()?.string()
             this.message = JsonParser().parse(errorJsonString)
                 .asJsonObject["message"]
                 .asString
             this.code = error.code()
         } else {
             this.message = error.message ?: this.message
         }
     }
}