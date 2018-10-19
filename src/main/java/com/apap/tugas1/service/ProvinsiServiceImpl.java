package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.ProvinsiDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public Optional<ProvinsiModel> getProvinsiDetailById(Long id){
		return provinsiDb.findById(id);
	}
	@Override
	public List<ProvinsiModel> getProvinsiList(){
		return provinsiDb.findAll();	
	}


}
