package ir.borjali.mvi.data.api

import ir.borjali.mvi.data.model.User

interface ApiUser {
    suspend fun getUsers() : List<User>
}