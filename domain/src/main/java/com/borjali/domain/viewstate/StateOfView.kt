@file:Suppress("unused")

package com.borjali.domain.viewstate

sealed class StateOfView {
    object None : StateOfView()
    object UserLogin : StateOfView()
    object UserLogOut : StateOfView()
    object UserSignOut : StateOfView()
    object ForgetPassword : StateOfView()
    object ChangePassword : StateOfView()
    object GetWorker : StateOfView()
    object UploadWorkerAvatar : StateOfView()
    object GetProductsOfOrder : StateOfView()
    object GetOrdersOfWorker : StateOfView()
    object CreateWorkLog : StateOfView()
    object CreateRestWorkLog : StateOfView()
    object FinishRestWorkLog : StateOfView()
    object UpdateWorkLog : StateOfView()
    object DeleteWorkLog : StateOfView()
    object GetWorkLogsOfUser : StateOfView()
    object TokenSignOut : StateOfView()
}
