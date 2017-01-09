package org.lf.jssm.service.model;

import java.util.List;

public class JzStatistics {
	private Integer year;
	private List<Integer> ygd;
	private List<Integer> wgd;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<Integer> getYgd() {
		return ygd;
	}
	public void setYgd(List<Integer> ygd) {
		this.ygd = ygd;
	}
	public List<Integer> getWgd() {
		return wgd;
	}
	public void setWgd(List<Integer> wgd) {
		this.wgd = wgd;
	}

}
