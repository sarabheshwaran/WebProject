package uub.cachelayer;

import java.util.LinkedHashMap;
import java.util.Map;

import uub.staticlayer.CustomBankException;

public class LRUCache<K, V> extends LinkedHashMap<K, V> implements Cache<K, V>{
    private static final long serialVersionUID = 1L;


    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 1f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

	@Override
	public void set(K key, V value) {
		this.put(key, value);

	}

	@Override
	public void close() {
		this.clear();

	}

	@Override
	public void rem(K key) {
		super.remove(key);

	}

}





