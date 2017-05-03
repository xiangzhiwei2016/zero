package com.jpaconfig;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;


public class FrameworkRepoFactorybean<R extends JpaRepository<T, I>, T, I extends Serializable>
extends JpaRepositoryFactoryBean<R, T, I> {
protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
return new FrameworkRepoFactorybean.MyRepositoryFactory(em);
}

private static class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {
private final EntityManager em;

public MyRepositoryFactory(EntityManager em) {
	super(em);
	this.em = em;
}

protected Object getTargetRepository(RepositoryMetadata metadata) {
	return new FrameworkRepositoryimpl(metadata.getDomainType(), this.em);
}

protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
	return FrameworkRepositoryimpl.class;
}
}
}