package com.orm.service;

import java.util.List;

public abstract interface IBaseEntityService<T> {
	public abstract int save(T paramT, Class<T> paramClass);
	
	public abstract int update(T paramT, Class<T> paramClass) throws Exception;
	
	public int deleteByColumn(String columnName, Object object, Class<T> clazz);
	
	public List<T> findAll(Class<T> clazz);
}