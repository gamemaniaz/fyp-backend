package foodbank.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockFactory {
	
	private static final Map<String, Object> LOCKFACTORY = new ConcurrentHashMap<String, Object>();
	
	public static ReadWriteLock getWriteLock(String lockName) {
		
		ReadWriteLock lock = (ReadWriteLock) LOCKFACTORY.putIfAbsent(lockName, new ReentrantReadWriteLock());
		if(lock == null) { lock = (ReadWriteLock) LOCKFACTORY.get(lockName); }
		return lock;
		
	}

}
