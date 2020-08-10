package app.futured.arkitekt.sample.ui.coroutinesresult

import app.futured.arkitekt.crusecases.BaseCrViewModel
import app.futured.arkitekt.crusecases.getOrCancel
import app.futured.arkitekt.crusecases.getOrElse
import app.futured.arkitekt.crusecases.getOrThrow
import app.futured.arkitekt.crusecases.map
import app.futured.arkitekt.crusecases.recover
import app.futured.arkitekt.sample.domain.dummy.ConfirmDataSavedSuccessfullyUseCase
import app.futured.arkitekt.sample.domain.dummy.GetDataFromDeviceUseCase
import app.futured.arkitekt.sample.domain.dummy.SaveDataToFirstServerUseCase
import app.futured.arkitekt.sample.domain.dummy.SaveDataToSecondServerUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoroutinesResultViewModel @Inject constructor(
    private val getDataFromDeviceUseCase: GetDataFromDeviceUseCase,
    private val saveDataToFirstServerUseCase: SaveDataToFirstServerUseCase,
    private val saveDataToSecondServerUseCase: SaveDataToSecondServerUseCase,
    private val confirmDataSavedSuccessfullyUseCase: ConfirmDataSavedSuccessfullyUseCase,
    override val viewState: CoroutinesResultViewState
) : BaseCrViewModel<CoroutinesResultViewState>() {

    private companion object {
        const val RESULT_DELAY = 500L
    }

    fun onStartLoadingClicked() = launchWithHandler {
        showLoading()

        // If the use case fails then result of `getOrElse` is returned
        val deviceData: String = getDataFromDeviceUseCase.execute().getOrElse { "Default data" }
        setLoadingState(step = "1")

        // If the use case fails then `showError` is called and the coroutine is canceled with CancellationException
        val firstSave: String = saveDataToFirstServerUseCase.execute(deviceData).getOrCancel { showError(step = "2") }
        setLoadingState(step = "2")

        // If the use case fails then result of `recover` is returned
        val secondSave = saveDataToSecondServerUseCase.execute(deviceData).map { "OK" }.recover { "Ignored error" }.getOrThrow()
        setLoadingState(step = "3")

        // The use case returns either (result, null) or (null, Throwable)
        val (result, _) = confirmDataSavedSuccessfullyUseCase.execute(firstSave to secondSave)

        setLoadingState(step = "4")
        delay(RESULT_DELAY)

        if (result != null) {
            showResult(result)
        } else {
            showError("4")
        }
    }

    fun onBack() = sendEvent(NavigateBackEvent)

    private fun setLoadingState(step: String) {
        viewState.contentState.value = CoroutinesResultViewState.State.LOADING
        viewState.contentStateDescription.value = "$step. step: OK"
    }

    private fun showLoading() {
        viewState.contentState.value = CoroutinesResultViewState.State.LOADING
        viewState.contentStateDescription.value = "Loading..."
    }

    private fun showResult(result: String) {
        viewState.contentState.value = CoroutinesResultViewState.State.RESULT
        viewState.contentStateDescription.value = result
    }

    private fun showError(step: String) {
        viewState.contentState.value = CoroutinesResultViewState.State.ERROR
        viewState.contentStateDescription.value = "$step. step: FAILED"
    }
}
