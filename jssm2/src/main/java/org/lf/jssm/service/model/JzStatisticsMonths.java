package org.lf.jssm.service.model;

import java.util.ArrayList;
import java.util.List;

public class JzStatisticsMonths {
	//'Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'
	private int status;
	private int Jan;
	private int Feb;
	private int Mar;
	private int Apr;
	private int May;
	private int Jun;
	private int Jul;
	private int Aug;
	private int Sep;
	private int Oct;
	private int Nov;
	private int Dec;
			
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getJan() {
		return Jan;
	}
	public void setJan(int jan) {
		Jan = jan;
	}
	public int getFeb() {
		return Feb;
	}
	public void setFeb(int feb) {
		Feb = feb;
	}
	public int getMar() {
		return Mar;
	}
	public void setMar(int mar) {
		Mar = mar;
	}
	public int getApr() {
		return Apr;
	}
	public void setApr(int apr) {
		Apr = apr;
	}
	public int getMay() {
		return May;
	}
	public void setMay(int may) {
		May = may;
	}
	public int getJun() {
		return Jun;
	}
	public void setJun(int jun) {
		Jun = jun;
	}
	public int getJul() {
		return Jul;
	}
	public void setJul(int jul) {
		Jul = jul;
	}
	public int getAug() {
		return Aug;
	}
	public void setAug(int aug) {
		Aug = aug;
	}
	public int getSep() {
		return Sep;
	}
	public void setSep(int sep) {
		Sep = sep;
	}
	public int getOct() {
		return Oct;
	}
	public void setOct(int oct) {
		Oct = oct;
	}
	public int getNov() {
		return Nov;
	}
	public void setNov(int nov) {
		Nov = nov;
	}
	public int getDec() {
		return Dec;
	}
	public void setDec(int dec) {
		Dec = dec;
	}
	//'Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'
	public List<Integer> getData(){
		List<Integer> data=new ArrayList<>();
		data.add(Jan);
		data.add(Feb);
		data.add(Mar);
		data.add(Apr);
		data.add(May);
		data.add(Jun);
		data.add(Jul);
		data.add(Aug);
		data.add(Sep);
		data.add(Oct);
		data.add(Nov);
		data.add(Dec);
		return data;
	}
}
