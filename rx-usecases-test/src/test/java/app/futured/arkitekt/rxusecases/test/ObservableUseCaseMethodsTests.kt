package app.futured.arkitekt.rxusecases.test

import app.futured.arkitekt.rxusecases.test.mockExecute
import app.futured.arkitekt.rxusecases.test.mockExecuteNullable
import app.futured.arkitekt.rxusecases.test.testutils.BaseTest
import app.futured.arkitekt.rxusecases.test.testutils.TestDisposablesOwner
import app.futured.arkitekt.rxusecases.usecases.ObservableUseCase
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test

class ObservableUseCaseMethodsTests : BaseTest() {

    class TestUseCase : ObservableUseCase<String, String>() {
        override fun prepare(args: String): Observable<String> {
            throw IllegalStateException("THIS SHOULD NOT BE CALLED")
        }
    }

    class TestUseCaseNullable : ObservableUseCase<String?, String>() {
        override fun prepare(args: String?): Observable<String> {
            throw IllegalStateException("THIS SHOULD NOT BE CALLED")
        }
    }

    private val mockUseCase: TestUseCase = mockk()
    private val mockUseCaseNullable: TestUseCaseNullable = mockk()

    private val args: String = "INPUT"
    private val argsNullable: String? = "INPUT"
    private val expectedResult = "RESULT"
    private val initialValue = "INITIAL VALUE"

    @Test
    fun `when use case is mocked with just value then expected value should be returned`() {
        // GIVEN
        mockUseCase.mockExecute(args) { Observable.just(expectedResult) }

        // WHEN
        val result = executeAndReturnResult()

        // THEN
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when use case is mocked with just value and without args then expected value should be returned`() {
        // GIVEN
        mockUseCase.mockExecute { Observable.just(expectedResult) }

        // WHEN
        val result = executeAndReturnResult()

        // THEN
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when nullable use case is mocked with just value then expected value should be returned`() {
        // GIVEN
        mockUseCaseNullable.mockExecuteNullable(argsNullable) { Observable.just(expectedResult) }

        // WHEN
        val result = executeNullableAndReturnResult()

        // THEN
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when nullable use case is mocked with just value and without args then expected value should be returned`() {
        // GIVEN
        mockUseCaseNullable.mockExecuteNullable { Observable.just(expectedResult) }

        // WHEN
        val result = executeNullableAndReturnResult()

        // THEN
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when nullable use case is mocked with null value then expected value should be returned`() {
        // GIVEN
        mockUseCaseNullable.mockExecuteNullable(null) { Observable.just(expectedResult) }

        // WHEN
        val result = executeNullAndReturnResult()

        // THEN
        assertEquals(expectedResult, result)
    }

    private fun executeAndReturnResult(): String? {
        var result: String? = initialValue
        with(TestDisposablesOwner()) {
            mockUseCase.execute(args) {
                onNext { result = it }
                onError { result = it.localizedMessage }
            }
        }
        return result
    }

    private fun executeNullableAndReturnResult(): String? {
        var result: String? = initialValue
        with(TestDisposablesOwner()) {
            mockUseCaseNullable.execute(argsNullable) {
                onNext { result = it }
                onError { result = it.localizedMessage }
            }
        }
        return result
    }

    private fun executeNullAndReturnResult(): String? {
        var result: String? = initialValue
        with(TestDisposablesOwner()) {
            mockUseCaseNullable.execute(null) {
                onNext { result = it }
                onError { result = it.localizedMessage }
            }
        }
        return result
    }
}
