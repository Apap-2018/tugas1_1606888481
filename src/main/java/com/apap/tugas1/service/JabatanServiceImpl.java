package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.*;
import com.apap.tugas1.repository.JabatanDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public Optional<JabatanModel> getJabatanDetailById(Long id){
		return jabatanDb.findById(id);
	}
	@Override
	public List<JabatanModel> getJabatanList(){
//		System.out.println("         MENGAMBIL SEMUA DATA JABATAN            ");
		return jabatanDb.findAll();	
		
	}
	@Override
	public void saveJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}
	@Override
	public void deleteJabatan(Long id) {
		jabatanDb.deleteById(id);
		
	}


}
