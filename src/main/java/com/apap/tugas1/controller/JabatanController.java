package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import com.apap.tugas1.model.*;
import com.apap.tugas1.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JabatanController {

	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private InstansiService instansiService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private JabatanService jabatanService;

	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam(value = "idJabatan") String id_jabatan, Model model) {
		JabatanModel jabatanTemp = jabatanService.getJabatanDetailById(Long.parseLong(id_jabatan)).get();
		List<PegawaiModel> pegawailstTemp = pegawaiService.getPegawaiList();
		Integer count = 0;
		for(PegawaiModel pegawai : pegawailstTemp) {
//			System.out.println(pegawai.getNama());
			for(JabatanModel jabatan : pegawai.getJabatan()) {
				if(jabatan.getNama().equals(jabatanTemp.getNama())) {
//					System.out.println("sama");
					count++;
				}
			}
		}

		
		model.addAttribute("jabatan", jabatanTemp);
		model.addAttribute("count", count);
		model.addAttribute("title", "View Jabatan");

		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewSemuaJabatan(Model model) {
		List <JabatanModel> jabatanTemp = jabatanService.getJabatanList();
		model.addAttribute("jabatanlst", jabatanTemp);
		model.addAttribute("title", "View Semua Jabatan");

		return "viewall-jabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String tambahJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		model.addAttribute("title", "Tambah Jabatan");
		return "tambah-jabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		jabatanService.saveJabatan(jabatan);
		model.addAttribute("title", "sukses");
		return "sukses";

	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam(value = "idJabatan") Long idJabatan,Model model) {
		JabatanModel tempJabatan = jabatanService.getJabatanDetailById(idJabatan).get();

		model.addAttribute("jabatan", tempJabatan);
		model.addAttribute("title", "Ubah Jabatan");
		return "ubah-jabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		jabatanService.saveJabatan(jabatan);
		model.addAttribute("title", "sukses");
		return "sukses";

	}
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String hapusJabatanSubmit(@RequestParam(value= "idJabatan") Long id,Model model) {
		JabatanModel jabatanTemp = jabatanService.getJabatanDetailById(id).get();
		List<PegawaiModel> pegawailstTemp = pegawaiService.getPegawaiList();
		for(PegawaiModel pegawai : pegawailstTemp) {
//			System.out.println(pegawai.getNama());
			for(JabatanModel jabatan : pegawai.getJabatan()) {
				if(jabatan.getNama().equals(jabatanTemp.getNama())) {
//					System.out.println("sama");
					return "gagal";
				}
			}
		}
		
		jabatanService.deleteJabatan(id);
		model.addAttribute("title", "sukses");
		return "sukses";

	}

	
}
