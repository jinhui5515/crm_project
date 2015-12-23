package com.atguigu.crm.dao.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository<T,PK extends Serializable>
	extends JpaRepository<T,PK>,JpaSpecificationExecutor<T>{

}
