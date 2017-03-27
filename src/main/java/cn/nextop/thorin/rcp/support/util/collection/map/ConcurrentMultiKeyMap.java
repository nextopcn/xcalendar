package cn.nextop.thorin.rcp.support.util.collection.map;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * @author Jingqi Xu
 * @param <K>
 * @param <V>
 */
public class ConcurrentMultiKeyMap<K1, K2, V> {
	//
	private final ConcurrentMap<K1, ConcurrentMap<K2, V>> map;
	
	/**
	 * 
	 */
	public ConcurrentMultiKeyMap() {
		this.map = new ConcurrentHashMap<>();
	}
	
	public ConcurrentMultiKeyMap(int capacity) {
		this.map = new ConcurrentHashMap<>(capacity);
	}
	
	/**
	 * 
	 */
	public int size() {
		return map.size();
	}
	
	public void clear() {
		this.map.clear();
	}
	
	public Set<K1> keySet() {
    	return this.map.keySet();
    }
	
	public boolean isEmpty() {
		return this.map.isEmpty();
	}
	
	public V get(final K1 k1, final K2 k2) {
		ConcurrentMap<K2, V> m = this.map.get(k1);
		return m == null ? null : m.get(k2);
	}
	
	public boolean containsKey(K1 k1, K2 k2) {
		ConcurrentMap<K2, V> m = this.map.get(k1);
		return m == null ? false : m.containsKey(k2);
	}
	
	public ConcurrentMap<K2, V> get(final K1 k1) {
		return this.map.get(k1);
	}
	
	public ConcurrentMap<K2, V> remove(final K1 k1) {
		return this.map.remove(k1);
	}
	
	public void puts(final K1 k1, Map<K2, V> values) {
		this.map.put(k1, new ConcurrentHashMap<>(values));
	}
	
	/**
	 * 
	 */
	public V remove(final K1 k1, final K2 k2) {
		AtomicReference<V> r = new AtomicReference<>(null);
		this.map.computeIfPresent(k1, (k, v) -> {
			r.set(v.remove(k2)); return v.size() == 0 ? null : v;
		});
		return r.get();
	}
	
	public V put(final K1 k1, final K2 k2, final V value) {
		AtomicReference<V> r = new AtomicReference<>(null);
		this.map.compute(k1, (k, v) -> {
			if(v == null) v = new ConcurrentHashMap<>();
			r.set(v.put(k2, value)); return v;
		});
		return r.get();
	}
	
	public V putIfAbsent(final K1 k1, final K2 k2, final V value) {
		AtomicReference<V> r = new AtomicReference<>(null);
		this.map.compute(k1, (k, v) -> {
			if(v == null) v = new ConcurrentHashMap<>();
			r.set(v.putIfAbsent(k2, value)); return v;
		});
		return r.get();
	}
	
	public boolean remove(final K1 k1, final K2 k2, final V value) {
		AtomicBoolean r = new AtomicBoolean(false);
		this.map.computeIfPresent(k1, (k, v) -> {
			r.set(v.remove(k2, value)); return v.size() == 0 ? null : v;
		});
		return r.get();
	}
}