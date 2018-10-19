package com.apap.tugas1.service;
import java.util.Optional;
import java.util.List;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	Optional<ProvinsiModel> getProvinsiDetailById(Long id);
	List<ProvinsiModel> getProvinsiList();
}
