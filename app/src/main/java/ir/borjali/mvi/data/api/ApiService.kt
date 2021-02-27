package ir.borjali.mvi.data.api

import ir.borjali.mvi.data.model.User
import retrofit2.http.GET

interface ApiService {
@GET("users")
suspend fun getUsers(): List<User>
}
