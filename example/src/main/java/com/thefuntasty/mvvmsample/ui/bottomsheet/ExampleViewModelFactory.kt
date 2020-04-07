package com.thefuntasty.mvvmsample.ui.bottomsheet

import app.futured.arkitekt.core.factory.BaseViewModelFactory
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ExampleViewModelFactory @Inject constructor(
    override val viewModelProvider: Provider<ExampleViewModel>
) : BaseViewModelFactory<ExampleViewModel>() {
    override val viewModelClass: KClass<ExampleViewModel> = ExampleViewModel::class
}
