package com.wolfesoftware.sailfish.uptime;

import java.util.HashMap;
import java.util.Map;

public class UptimeHistory {
	
	Map<String,Current> history = new HashMap<String,Current>();
	
	public synchronized void update(String url, Long milliseconds) {
		Current current = history.get(url);
		if (current==null){
			history.put(url, new Current(1,0));
		} else {
			current.updateCount();
			current.updateMilliseconds(milliseconds);
		}
		System.out.println("***Uptime***");
		for (Map.Entry<String,Current> entry : history.entrySet()) {
			System.out.println("URL:" + entry.getKey());
			entry.getValue().output();
		}
		System.out.println("******");
	}

}

class Current{
	Long count;
	Long milliseconds;
	
	Current(long count, long millis){
		this.count = count;
		milliseconds = millis;
	}
	
	void updateCount(){
		count ++;
	}
	void updateMilliseconds(Long millis){
		milliseconds+=millis;
	}
	void output(){
		System.out.println("Count:" + count + " Average Milliseconds:" + milliseconds/count);
	}
}