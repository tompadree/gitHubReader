package com.example.domain.executor;

/**
 * Created by Tom on 28.10.2016..
 */

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link com.example.domain.interactor.UseCase} out of the UI thread.
 */
public interface ThreadExecutor extends Executor {}
