package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.InstansiDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public Optional<InstansiModel> getInstansiDetailById(Long id){
		return instansiDb.findById(id);
	}
	@Override
	public List<InstansiModel> getInstansiList(){
		return instansiDb.findAll();	
	}


}
