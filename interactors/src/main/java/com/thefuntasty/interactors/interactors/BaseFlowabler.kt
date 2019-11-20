package com.thefuntasty.interactors.interactors

import io.reactivex.Flowable

/**
 * Base interactor which wraps [Flowable]. Instance of this
 * interactor can be simply executed in cooperation with
 * [com.thefuntasty.interactors.disposables.FlowableDisposablesOwner] interface.
 *
 * Wrapped stream is subscribed on [io.reactivex.schedulers.Schedulers.io] and
 * observed on [io.reactivex.android.schedulers.AndroidSchedulers.mainThread]
 * by default. You may override these through [workScheduler] and
 * [resultScheduler] respectively.
 */
abstract class BaseFlowabler<ARGS, T> : BaseInteractor() {

    /**
     * Prepares whole wrapped [Flowable] Rx stream. This method does not
     * subscribe to the stream.
     */
    protected abstract fun prepare(args: ARGS): Flowable<T>

    /**
     * Creates internal [Flowable] Rx stream, applies requested work
     * & result schedulers and exposes this stream as a [Flowable]. This method
     * is handy when you want to combine streams of multiple interactors.
     * For example:
     *
     * interactor_A.create(Unit).flatMap {
     *     interactor_B.create(Unit)
     * }
     */
    fun create(args: ARGS): Flowable<T> = prepare(args).applySchedulers()

    private fun Flowable<T>.applySchedulers(): Flowable<T> {
        return compose { resultObservable ->
            resultObservable
                .subscribeOn(workScheduler)
                .observeOn(resultScheduler)
        }
    }
}
