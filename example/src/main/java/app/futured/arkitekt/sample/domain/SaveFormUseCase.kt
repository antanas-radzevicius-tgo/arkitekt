package app.futured.arkitekt.sample.domain

import app.futured.arkitekt.crusecases.UseCase
import app.futured.arkitekt.sample.data.store.FormStore
import javax.inject.Inject

class SaveFormUseCase @Inject constructor(
    private val formStore: FormStore
) : UseCase<SaveFormUseCase.Data, Pair<String, String>>() {

    data class Data(val form: Pair<String, String>)

    override suspend fun build(args: Data): Pair<String, String> {
        formStore.saveForm(args.form)
        return args.form
    }
}
