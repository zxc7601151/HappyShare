package com.happy.share.network.retrofit;

import java.util.concurrent.TimeUnit;

/**
 * desc: 获取时间 <br/>
 * time: 2017/9/7 10:40 <br/>
 * author: Vincent <br/>
 * since V1.0 <br/>
 */
public class TimeWatch {

	private long startTime;
	private long endTime;
	private long elapsedTime;

	public TimeWatch() {
		//empty
	}

	private void reset() {
		startTime = 0;
		endTime = 0;
		elapsedTime = 0;
	}

	public void start() {
		reset();
		startTime = System.nanoTime();
	}

	public void stop() {
		if (startTime != 0) {
			endTime = System.nanoTime();
			elapsedTime = endTime - startTime;
		} else {
			reset();
		}
	}

	public double getTotalTime(int type) {
		if (type == 1) {
			return getTotalTimeMillis();
		} else {
			return getTotalTimeMicros();
		}
	}

	public double getTotalTimeMicros() {
		return (elapsedTime != 0) ? (endTime - startTime) / 1000.0 : 0;
	}

	public double getTotalTimeMillis() {
		return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMicros(endTime - startTime) / 1000.0 : 0;
	}
}