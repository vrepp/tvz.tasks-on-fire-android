package hr.tvz.android.tasksonfirerep.network

import android.content.Intent
import com.google.gson.GsonBuilder
import hr.tvz.android.tasksonfirerep.TasksOnFire
import hr.tvz.android.tasksonfirerep.model.BasicTask
import hr.tvz.android.tasksonfirerep.model.Login
import hr.tvz.android.tasksonfirerep.model.responses.*
import hr.tvz.android.tasksonfirerep.ui.login.LoginActivity
import hr.tvz.android.tasksonfirerep.util.BASE_URL
import hr.tvz.android.tasksonfirerep.util.MySharedPreferences
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface API {
    @Headers("No-Authentication: true")
    @POST("/api/v1/auth/login")
    fun login(@Body login: Login) : Observable<LoginTokenResponse>

    @Headers("No-Authentication: true")
    @POST("/api/v1/auth/register")
    fun register(@Body register: Login) : Observable<BasicResponse>

    @GET("/api/v1/tasks/")
    fun getAllTask() : Observable<TaskListResponse>

    @POST("/api/v1/tasks/")
    fun createTask(@Body basicTask: BasicTask) : Observable<TaskCreateResponse>

    @PUT("/api/v1/tasks/{id}")
    fun update(@Body task: BasicTask, @Path("id") id: String) : Observable<TaskUpdateResponse>

    @DELETE("/api/v1/tasks/{id}")
    fun delete(@Path("id") id: String) : Observable<TaskDeleteResponse>

    companion object {
        fun create(): API {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor {
                    val header= it.request().header("No-Authentication")
                    val token = MySharedPreferences.getToken()
                    val newRequest =
                        if (header == null) {
                            it.request().newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                                .build()
                        } else {
                            it.request()
                        }

                    it.proceed(newRequest)
                }
                .addInterceptor{
                    val request = it.request()
                    val response = it.proceed(request)
                    val intent = Intent(TasksOnFire.applicationContext(), LoginActivity::class.java)
                    if (request.header("No-Authentication") == null) {
                        when (response.code()) {
                            401 -> {
                                TasksOnFire.applicationContext().startActivity(intent)
                                MySharedPreferences.clearToken()
                            }
                        }
                    }
                    response
                }
                .build()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(API::class.java)
        }
    }
}