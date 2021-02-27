package ir.borjali.mvi.ui.main.intent

sealed class MainIntent {
    object fetchUser : MainIntent()
}