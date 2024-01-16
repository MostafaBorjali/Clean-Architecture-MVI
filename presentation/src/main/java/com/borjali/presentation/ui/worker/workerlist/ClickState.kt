package com.borjali.presentation.ui.worker.workerlist


sealed class ClickState {

    data class ItemClicked(val position: Int): ClickState()
    data class ShowLogClicked(val position: Int): ClickState()
    data class EditUserAvatarClicked(val position: Int): ClickState()

}
