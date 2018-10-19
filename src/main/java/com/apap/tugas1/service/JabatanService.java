package com.apap.tugas1.service;
import java.util.Optional;
import java.util.List;
import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	Optional<JabatanModel> getJabatanDetailById(Long id);
	List<JabatanModel> getJabatanList();
	void saveJabatan(JabatanModel jabatan);
	void deleteJabatan(Long id);
}
