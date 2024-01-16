package com.borjali.domain.utils

data class StateMessage(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType {
    object TOAST : UIComponentType()
    object SNACKBAR : UIComponentType()
}

sealed class MessageType {
    object SUCCESS : MessageType()
    object ERROR : MessageType()
    object INFO : MessageType()
}
