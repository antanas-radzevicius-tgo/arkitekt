package app.futured.arkitekt.sample.ui.detail

import app.futured.arkitekt.core.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    override val viewState: DetailViewState
) : BaseViewModel<DetailViewState>() {

    override fun onStart() {
        viewState.number.observeWithoutOwner { viewState.stringNumber.value = it.toString() }
    }

    fun incrementNumber() {
        viewState.number.value = viewState.number.value + 1
    }

    fun onBack() {
        sendEvent(NavigateBackEvent)
    }
}
