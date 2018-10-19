package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public Optional<PegawaiModel> getPegawaiDetailById(Long id){
		return pegawaiDb.findById(id);
	}
	@Override
	public List<PegawaiModel> getPegawaiList(){
		return pegawaiDb.findAll();
		
	}
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		PegawaiModel pegawaiTemp = pegawaiDb.findByNip(nip);
		return pegawaiTemp;
	}
	@Override
	public Double calculateGaji(Long id) {
		PegawaiModel tempPegawai = pegawaiDb.findById(id).get();
		Double gajiPokokTerbesar = 0.0;
		Double tunjangan = tempPegawai.getInstansi().getProvinsi().getPresentase_tunjangan();
		for (JabatanModel jabatan : tempPegawai.getJabatan()) {
			Double gajiPokok = jabatan.getGaji_pokok(); 
//			System.out.println(jabatan.getNama() + " = " + gajiPokok);
			if(gajiPokok >= gajiPokokTerbesar) {
				gajiPokokTerbesar = gajiPokok;
			}
		}
		Double gajiAkhir = gajiPokokTerbesar + (gajiPokokTerbesar * tunjangan/100);
//		System.out.println(gajiAkhir + " = "+ gajiPokokTerbesar + " + (" + gajiPokokTerbesar + " * " + tunjangan + ")" );
		return gajiAkhir;
	}
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}




}
