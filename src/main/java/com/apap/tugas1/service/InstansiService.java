package com.apap.tugas1.service;
import java.util.Optional;
import java.util.List;
import com.apap.tugas1.model.InstansiModel;

public interface InstansiService {
	Optional<InstansiModel> getInstansiDetailById(Long id);
	List<InstansiModel> getInstansiList();
}
