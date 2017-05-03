package com.jpaconfig;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FrameworkRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	void updateSelective(T arg0);

	@Deprecated
	void partUpdate(ID arg0, T arg1) throws Exception;
}