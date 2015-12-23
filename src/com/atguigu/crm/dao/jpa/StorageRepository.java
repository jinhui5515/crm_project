package com.atguigu.crm.dao.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.atuigu.crm.entity.Storage;

public interface StorageRepository extends BaseRepository<Storage, Long> {

	@Modifying
	@Query(value="update storages s set s.stock_count=? where s.id=?",nativeQuery=true)
	public void update1(Integer count,Long id);

}
