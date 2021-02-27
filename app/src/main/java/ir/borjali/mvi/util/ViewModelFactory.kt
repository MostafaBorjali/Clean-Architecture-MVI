package ir.borjali.mvi.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.borjali.mvi.data.api.ApiUser
import ir.borjali.mvi.data.repository.UserRepository
import ir.borjali.mvi.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiUser: ApiUser) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(UserRepository(apiUser)) as T
        }
        throw IllegalArgumentException("unknow class name")
    }
}