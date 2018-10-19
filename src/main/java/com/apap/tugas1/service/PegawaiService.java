package com.apap.tugas1.service;
import java.util.Optional;
import java.util.List;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	List<PegawaiModel> getPegawaiList();
	PegawaiModel getPegawaiDetailByNip(String nip);
	Double calculateGaji(Long id);
	void addPegawai(PegawaiModel pegawai);
}
