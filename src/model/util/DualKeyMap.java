package model.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

	public Set<String> get(V v){
		return map.entrySet()
				.stream()
				.filter(entry -> Objects.equals(v, entry.getValue()))
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	public void remove(K k, L l){
		String key = k.hashCode() + "_" + l.hashCode();
		map.remove(key);
	}

	public boolean containsKey(K k, L l){
		String key = k.hashCode() + "_" + l.hashCode();
		return map.containsKey(key);
	}

	public int size(){
		return map.size();
	}

	public void clear(){
		map.clear();
	}

}
