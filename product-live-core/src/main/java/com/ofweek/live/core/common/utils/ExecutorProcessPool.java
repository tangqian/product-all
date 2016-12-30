package com.ofweek.live.core.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ExecutorProcessPool {

	private ExecutorService executor;
	
	private static ExecutorProcessPool pool;
	
	private static final int norm = 50;
	
	private ExecutorProcessPool() {
		
	}
	
	private ExecutorProcessPool(String poolType) {
		switch (poolType) {
			case "Cached" : 
				executor = ExecutorServiceFactory.getInstance().createCachedThreadPool();
				break;
			case "Fixed" : 
				executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(norm);
				break;
			case "Single" : 
				executor = ExecutorServiceFactory.getInstance().createSingleThreadExecutor();
				break;
			case "Scheduled" : 
				executor = ExecutorServiceFactory.getInstance().createScheduledThreadPool();
				break;

			default :
				break;
		}
	}

	public static ExecutorProcessPool getInstance(String poolType) {
		if (pool == null) {
			pool = new ExecutorProcessPool(poolType);
		}
		return pool;
	}

	public void shutdown() {
		executor.shutdown();
	}

	public Future<?> submit(Runnable task) {
		return executor.submit(task);
	}

	public Future<?> submit(Callable<?> task) {
		return executor.submit(task);
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}

}
