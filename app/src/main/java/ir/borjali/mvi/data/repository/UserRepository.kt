package ir.borjali.mvi.data.repository

import ir.borjali.mvi.data.api.ApiUser

class UserRepository (private val apiUser: ApiUser){
    suspend fun getUsers() = apiUser.getUsers()
}