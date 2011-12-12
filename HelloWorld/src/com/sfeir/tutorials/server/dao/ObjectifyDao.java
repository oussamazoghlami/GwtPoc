package com.sfeir.tutorials.server.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

/**
 * Generic objectify DAO implementation
 * 
 * @author Oussama Zoghlami
 * 
 * @param <T>
 */
public class ObjectifyDao<T> extends DAOBase {

	private Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 * 
	 * @param clazz
	 */
	protected ObjectifyDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public Key<T> add(T entity) {
		Key<T> key = ofy().put(entity);
		return key;
	}

	/**
	 * 
	 * @param entities
	 */
	public void add(List<T> entities) {
		ofy().put(entities);
	}

	/**
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		ofy().delete(entity);
	}

	public void delete(List<T> entities) {
		ofy().delete(entities);
	}

	/**
	 * 
	 * @param entityKey
	 */
	public void delete(Key<T> entityKey) {
		ofy().delete(entityKey);
	}

	/**
	 * Get an entity throw its id
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	public T get(Long id) throws EntityNotFoundException {
		T obj = ofy().get(this.clazz, id);
		return obj;
	}
	
	public List<T> getAll() {
		Query<T> q = ofy().query(clazz);
		return q.list();
	}

	/**
	 * @param propName
	 * @param propValue
	 * @return T
	 */
	public T getByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.get();
	}

	/**
	 * @param propName
	 * @param propValue
	 * @return
	 */
	public List<T> listByProperty(String propName, Object propValue) {
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.list();
	}

	/**
	 * Get by example
	 * 
	 * @param u
	 * @param matchProperties
	 * @return
	 */
	public T getByExample(T u, String... matchProperties) {
		Query<T> q = ofy().query(clazz);
		// Find non-null properties and add to query
		for (String propName : matchProperties) {
			Object propValue = getPropertyValue(u, propName);
			q.filter(propName, propValue);
		}
		return q.get();
	}

	/**
	 * 
	 * @param u
	 * @param matchProperties
	 * @return
	 */
	public List<T> listByExample(T u, String... matchProperties) {
		Query<T> q = ofy().query(clazz);
		// Find non-null properties and add to query
		for (String propName : matchProperties) {
			Object propValue = getPropertyValue(u, propName);
			q.filter(propName, propValue);
		}
		return q.list();
	}

	/**
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	private Object getPropertyValue(Object obj, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propName = propertyDescriptor.getName();
			if (propName.equals(propertyName)) {
				Method readMethod = propertyDescriptor.getReadMethod();
				try {
					Object value = readMethod.invoke(obj, new Object[] {});
					return value;
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

}
