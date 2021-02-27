package ir.borjali.mvi.data.api

import ir.borjali.mvi.data.model.User

class ApiUserProvider (private val apiService: ApiService) : ApiUser {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}