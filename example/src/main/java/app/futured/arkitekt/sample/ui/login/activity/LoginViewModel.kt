package app.futured.arkitekt.sample.ui.login.activity

import app.futured.arkitekt.rxusecases.BaseRxViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    override val viewState: LoginViewState
) : BaseRxViewModel<LoginViewState>() {

    fun sendToastEvent(message: String) {
        sendEvent(ShowToastEvent("LoginActivity test toast: $message"))
    }
}
