package cn.nextop.thorin.rcp.support.util.collection.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Jingqi Xu
 */
public final class LruMap<K, V> extends LinkedHashMap<K, V> {
	//
	private static final long serialVersionUID = -366538766679345583L;
	
	//
	private final int capacity;
	
	/**
	 * 
	 */
	public LruMap(int capacity) {
		this(capacity, true);
	}
	
	public LruMap(int capacity, boolean accessOrder) {
		super(capacity + 1, 1, accessOrder);
		if(capacity < 1) throw new IllegalArgumentException("invalid parameter capacity: " + capacity);
		this.capacity = capacity;
	}

	/**
	 * 
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * 
	 */
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > this.capacity;
	}
}
