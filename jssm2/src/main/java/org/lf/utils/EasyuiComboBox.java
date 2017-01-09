package org.lf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EasyuiComboBox<K, V> {
	private Map<K, V> values;

	public EasyuiComboBox(TreeMap<K, V> values) {
		this.values = values;
	}
	
	public List<EasyuiComboBoxItem<K,V>> getRecords() {
		Iterator<K> it = values.keySet().iterator();
		EasyuiComboBoxItem<K,V> r;
		List<EasyuiComboBoxItem<K,V>> rList = new ArrayList<>();
		K key;
		while (it.hasNext()) {
			key = it.next();
			r = new EasyuiComboBoxItem<K,V>(key, values.get(key));
			rList.add(r);
		}
		
		return rList;
	}

}
