package com.atguigu.crm.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.dao.jpa.StorageRepository;
import com.atuigu.crm.entity.Storage;

@Service
public class StorageService extends BaseService<Storage, Long>{
	
	@Autowired
	private StorageRepository storageRepository;
	
	public void update(Storage storage, Integer incrementCount, Long id) {
		Integer stockCount = storage.getStockCount();
		if(id!=null){
			Integer count =stockCount+incrementCount;
			storageRepository.update1(count,id);
		}else{
			storageRepository.saveAndFlush(storage);
		}
		
	}

}
