package org.lf.jssm.service.model;

import java.util.List;


public class AJJYOuterStaticModel {
	private Integer year;
	
	private List<Integer> permit;
	
	private List<Integer> unpermit;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Integer> getPermit() {
		return permit;
	}

	public void setPermit(List<Integer> permit) {
		this.permit = permit;
	}

	public List<Integer> getUnpermit() {
		return unpermit;
	}

	public void setUnpermit(List<Integer> unpermit) {
		this.unpermit = unpermit;
	}
	
}
