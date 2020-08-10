package app.futured.arkitekt.rxusecases

import app.futured.arkitekt.core.error.UseCaseErrorHandler
import app.futured.arkitekt.rxusecases.disposables.withDisposablesOwner
import app.futured.arkitekt.rxusecases.usecases.CompletableUseCase
import app.futured.arkitekt.rxusecases.usecases.RxMockitoJUnitRunner
import io.reactivex.Completable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UseCaseHandlerTest : RxMockitoJUnitRunner() {

    @Before
    fun setUp() {
        UseCaseErrorHandler.globalOnErrorLogger = {}
    }

    @After
    fun tearDown() {
        UseCaseErrorHandler.globalOnErrorLogger = {}
    }

    @Test
    fun `use case handler called when exception is thrown`() {
        val testException = RuntimeException("Test error")
        var globalLoggedException: Throwable? = null
        var builderException: Throwable? = null

        UseCaseErrorHandler.globalOnErrorLogger = {
            globalLoggedException = it
        }

        withDisposablesOwner {
            object : CompletableUseCase<Unit>() {
                override fun prepare(args: Unit): Completable {
                    return Completable.error(testException)
                }
            }.execute(Unit) {
                onError { builderException = it }
            }

            assertEquals(testException, globalLoggedException)
            assertEquals(testException, builderException)
        }
    }
}
