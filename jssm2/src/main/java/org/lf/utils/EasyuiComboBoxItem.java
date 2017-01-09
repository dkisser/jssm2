package org.lf.utils;

public class EasyuiComboBoxItem<K, V> {
	private K id;
	private V text;
	private boolean selected;
	

	public EasyuiComboBoxItem() {
	}

	public EasyuiComboBoxItem(K id, V text) {
		super();
		this.id = id;
		this.text = text;
	}

	public EasyuiComboBoxItem(K id, V text, boolean selected) {
		super();
		this.id = id;
		this.text = text;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	public V getText() {
		return text;
	}

	public void setText(V text) {
		this.text = text;
	}
}
