package model.util;

import java.util.HashMap;
import java.util.Map;

public class DualKeyMap<K,L,V>{

	private Map<String, V> map;

	public DualKeyMap(){
		this.map = new HashMap<>();
	}

	public void put(K k, L l, V v){
		String key = k.hashCode() + "_" + l.hashCode();
		map.put(key, v);
	}

	public V get(K k, L l){
		String key = k.hashCode() + "_" + l.hashCode();
		return map.get(key);
	}

	public boolean containsKey(K k, L l){
		String key = k.hashCode() + "_" + l.hashCode();
		return map.containsKey(key);
	}

}
