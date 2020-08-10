package app.futured.arkitekt.rxusecases.test

import app.futured.arkitekt.rxusecases.disposables.MaybeDisposablesOwner
import app.futured.arkitekt.rxusecases.usecases.MaybeUseCase
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.reactivex.Maybe
import io.reactivex.disposables.Disposable

/**
 * Mock [MaybeDisposablesOwner.execute] method.
 *
 * When the execute method will be called then the argument passed in [resultBlock] will be used as a result of mocked use case
 * and corresponding methods for the given use case will be called.
 * So when `Maybe.just` will be passed then `onNext` will be called etc.
 *
 * Usage:
 * mockMaybeUseCase.mockExecute(args = ...) { Maybe.just(...) }
 */
inline fun <reified ARGS : Any, RETURN_VALUE, USE_CASE : MaybeUseCase<ARGS, RETURN_VALUE>> USE_CASE.mockExecute(args: ARGS, resultBlock: () -> Maybe<RETURN_VALUE>) {
    mockCurrentDisposable()
    every { this@mockExecute.create(args) } returns resultBlock()
}

/**
 * Mock [MaybeDisposablesOwner.execute] method with `any()` matcher argument used as input argument.
 *
 * When the execute method will be called then the argument passed in [resultBlock] will be used as a result of mocked use case
 * and corresponding methods for the given use case will be called.
 * So when `Maybe.just` will be passed then `onNext` will be called etc.
 *
 * Usage:
 * mockMaybeUseCase.mockExecute(args = ...) { Maybe.just(...) }
 */
inline fun <reified ARGS : Any, RETURN_VALUE, USE_CASE : MaybeUseCase<ARGS, RETURN_VALUE>> USE_CASE.mockExecute(resultBlock: () -> Maybe<RETURN_VALUE>) {
    mockCurrentDisposable()
    every { this@mockExecute.create(any()) } returns resultBlock()
}

/**
 * Mock [MaybeDisposablesOwner.execute] method for use cases with nullable input argument.
 *
 * When the execute method will be called then the argument passed in [resultBlock] will be used as a result of mocked use case
 * and corresponding methods for the given use case will be called.
 * So when `Maybe.just` will be passed then `onNext` will be called etc.
 *
 * Usage:
 * mockMaybeUseCase.mockExecuteNullable(args = ...) { Maybe.just(...) }
 */
inline fun <reified ARGS : Any?, RETURN_VALUE, USE_CASE : MaybeUseCase<ARGS?, RETURN_VALUE>> USE_CASE.mockExecuteNullable(args: ARGS, resultBlock: () -> Maybe<RETURN_VALUE>) {
    mockCurrentDisposable()
    every { this@mockExecuteNullable.create(args) } returns resultBlock()
}

/**
 * Mock [MaybeDisposablesOwner.execute] method for use cases with nullable input argument
 * and `any()` matcher argument used as input argument.
 *
 * When the execute method will be called then the argument passed in [resultBlock] will be used as a result of mocked use case
 * and corresponding methods for the given use case will be called.
 * So when `Maybe.just` will be passed then `onNext` will be called etc.
 *
 * Usage:
 * mockMaybeUseCase.mockExecuteNullable(args = ...) { Maybe.just(...) }
 */
inline fun <reified ARGS : Any, RETURN_VALUE, USE_CASE : MaybeUseCase<ARGS?, RETURN_VALUE>> USE_CASE.mockExecuteNullable(resultBlock: () -> Maybe<RETURN_VALUE>) {
    mockCurrentDisposable()
    every { this@mockExecuteNullable.create(any()) } returns resultBlock()
}

@PublishedApi
internal fun <RETURN_VALUE, USE_CASE : MaybeUseCase<*, RETURN_VALUE>> USE_CASE.mockCurrentDisposable() {
    every { this@mockCurrentDisposable getProperty "currentDisposable" } returns null
    every { this@mockCurrentDisposable setProperty "currentDisposable" value any<Disposable>() } just runs
}
