package app.futured.arkitekt.sample.ui.main

import app.futured.arkitekt.core.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    override val viewState: MainViewState
) : BaseViewModel<MainViewState>() {

    fun onDetail() {
        sendEvent(ShowDetailEvent)
    }

    fun onForm() {
        sendEvent(ShowFormEvent)
    }

    fun onLogin() {
        sendEvent(ShowLoginEvent)
    }

    fun onBottomSheet() {
        sendEvent(ShowBottomSheetEvent)
    }

    fun onLoad() {
        sendEvent(ShowLoadEvent)
    }
}
