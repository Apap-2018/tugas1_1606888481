package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Comparator;

import com.apap.tugas1.model.*;
import com.apap.tugas1.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private InstansiService instansiService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private JabatanService jabatanService;

	
	@RequestMapping("/")
	private String home(Model model) {
		List<PegawaiModel> pegawailst = pegawaiService.getPegawaiList();
		List<JabatanModel> jabatanlst = jabatanService.getJabatanList();
		List<InstansiModel> instansilst = instansiService.getInstansiList();
		
		model.addAttribute("pegawailst",pegawailst);
		model.addAttribute("jabatanlst",jabatanlst);
		model.addAttribute("instansilst",instansilst);
		model.addAttribute("title", "Home");
		return "home";
	}
	// tes pake nip = 	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawaiTemp = pegawaiService.getPegawaiDetailByNip(nip);
		Double gaji = pegawaiService.calculateGaji(pegawaiTemp.getId());
		model.addAttribute("pegawai", pegawaiTemp);
		model.addAttribute("gaji",gaji);
		model.addAttribute("title", "View Pegawai");
		return "view-pegawai";
	}
	//untuk melakukan penyimpanan
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawai(@ModelAttribute PegawaiModel pegawai) {
		System.out.println(pegawai.getTanggal_lahir());
		
		pegawaiService.addPegawai(pegawai);		
		return "tambah-pegawai";
	}
	
	//untuk menampilkan page
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawaiInitial(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatan(new ArrayList<JabatanModel>());
		pegawai.getJabatan().add(new JabatanModel());
		
		List<JabatanModel> jabatanLst;
		jabatanLst = jabatanService.getJabatanList();				
		List<ProvinsiModel> provinsiLst = provinsiService.getProvinsiList();
		List<InstansiModel> instansiLst = instansiService.getInstansiList();		

		model.addAttribute("pegawai",pegawai);
		model.addAttribute("provinsi", provinsiLst);
		model.addAttribute("instansi", new HashSet(instansiLst));
		model.addAttribute("jabatan", new HashSet(jabatanLst));
		return "tambah-pegawai";
	}

	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTuaMuda(@RequestParam(value = "idInstansi") Long idInstansi, Model model) {
		InstansiModel instansiTemp = instansiService.getInstansiDetailById(idInstansi).get();

		List<PegawaiModel> pegawailst= pegawaiService.getPegawaiList();
		List<PegawaiModel> pegawaiInstansi= new ArrayList<PegawaiModel>();
		
		for (PegawaiModel pegawai : pegawailst) {
			if(instansiTemp.getId() == pegawai.getInstansi().getId()) {
//			if(instansiTemp.getNama().equals(pegawai.getInstansi().getNama())) {
				pegawaiInstansi.add(pegawai);
			}
		Comparator<PegawaiModel> ageComp = new PegawaiAgeComparator();
		pegawaiInstansi.sort(ageComp);
		
		}
		
		PegawaiModel pegawaiTua = pegawaiInstansi.get(0);
		PegawaiModel pegawaiMuda = pegawaiInstansi.get(pegawaiInstansi.size()-1);
		
		Double gajiMuda = pegawaiService.calculateGaji(pegawaiTua.getId());
		Double gajiTua = pegawaiService.calculateGaji(pegawaiMuda.getId());

		
		model.addAttribute("tua", pegawaiTua);
		model.addAttribute("muda", pegawaiMuda);
		model.addAttribute("gajiTua", gajiMuda);
		model.addAttribute("gajiMuda", gajiTua);
		 
		return "view-pegawai-tua-muda";
	}

}


