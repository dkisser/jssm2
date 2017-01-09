package org.lf.jssm.db.dao;
public interface BaseMapper<T> {
	int deleteByPrimaryKey(Integer id);

	int insert(T record);
}
