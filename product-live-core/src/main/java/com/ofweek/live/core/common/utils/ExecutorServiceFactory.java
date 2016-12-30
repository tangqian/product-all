package com.ofweek.live.core.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceFactory {

	private static ExecutorServiceFactory executorFactory = new ExecutorServiceFactory();

	private ExecutorService executors;

	private ExecutorServiceFactory() {

	}

	public static ExecutorServiceFactory getInstance() {
		return executorFactory;
	}

	public ExecutorService createCachedThreadPool() {
		executors = Executors.newCachedThreadPool(getThreadFactory());
		return executors;
	}

	public ExecutorService createFixedThreadPool(int count) {
		executors = Executors.newFixedThreadPool(count, getThreadFactory());
		return executors;
	}

	public ExecutorService createSingleThreadExecutor() {
		executors = Executors.newSingleThreadExecutor(getThreadFactory());
		return executors;
	}

	public ExecutorService createScheduledThreadPool() {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		executors = Executors.newScheduledThreadPool(availableProcessors * 10, getThreadFactory());
		return executors;
	}

	private ThreadFactory getThreadFactory() {
		return new ThreadFactory() {
			AtomicInteger sn = new AtomicInteger();
			public Thread newThread(Runnable r) {
				SecurityManager s = System.getSecurityManager();
				ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
				Thread t = new Thread(group, r);
				t.setName("任务线程 - " + sn.incrementAndGet());
				return t;
			}
		};
	}
}
