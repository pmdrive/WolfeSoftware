package com.wolfesoftware.sailfish.concurrency.worker.factory;

import com.wolfesoftware.sailfish.worker.Worker;

/*
 * A worker factory returns a worker which a series of parallel units of work as a runnable thread
 * It also can specify whether there is any more work to be done
 */
public abstract class WorkerFactory {

	private boolean isThereAnyMoreWorkToDo = true;

	public abstract Runnable getWorker();

	public boolean isThereAnyMoreWorkToDo() {
		return isThereAnyMoreWorkToDo;
	}

	public void setIsThereAnyMoreWorkToDo(boolean finished) {
		this.isThereAnyMoreWorkToDo = finished;
	}

	protected Runnable getAsThread(final Worker worker) {
		return new Thread() {
			@Override
			public void run() {
				worker.go();
			}
		};
	}

}
