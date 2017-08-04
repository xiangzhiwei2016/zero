package com.core.config;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.persistence.EntityManager;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class FrameworkRepositoryimpl<T, ID extends Serializable> extends SimpleJpaRepository<T, Serializable>
implements FrameworkRepository<T, Serializable> {
private final EntityManager entityManager;

public FrameworkRepositoryimpl(Class<T> domainClass, EntityManager em) {
super(domainClass, em);
this.entityManager = em;
}

@Transactional(propagation = Propagation.REQUIRED)
public void updateSelective(T entity) {
try {
	Serializable e = null;
	Field[] entityFields = entity.getClass().getDeclaredFields();

	for (int dbEntity = 0; dbEntity < entityFields.length; ++dbEntity) {
		if (!Modifier.isStatic(entityFields[dbEntity].getModifiers())) {
			Id index = (Id) entityFields[dbEntity].getAnnotation(Id.class);
			if (index != null) {
				entityFields[dbEntity].setAccessible(true);
				e = (Serializable) entityFields[dbEntity].get(entity);
				break;
			}
		}
	}

	if (e != null) {
		Object arg11 = this.entityManager.find(entity.getClass(), e);
		int arg12 = 0;
		Field[] arg5 = entityFields;
		int arg6 = entityFields.length;

		for (int arg7 = 0; arg7 < arg6; ++arg7) {
			Field field = arg5[arg7];
			if (Modifier.isStatic(field.getModifiers())) {
				++arg12;
			} else {
				field.setAccessible(true);
				Object updateValue = field.get(entity);
				if (updateValue != null) {
					entityFields[arg12].setAccessible(true);
					entityFields[arg12].set(arg11, updateValue);
				}

				++arg12;
			}
		}

		this.entityManager.merge(arg11);
	}
} catch (IllegalAccessException arg10) {
	throw new RuntimeException(arg10);
}
}

@Transactional(propagation = Propagation.REQUIRED)
public void partUpdate(Serializable id, T entity) throws Exception {
this.updateSelective(entity);
}
}