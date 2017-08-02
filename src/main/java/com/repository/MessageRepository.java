package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Message;
import com.entity.OperateLogTest;


@Repository("messageRepository")
public interface MessageRepository extends CrudRepository<Message, Long> {
	
}
