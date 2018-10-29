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
	
		@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
		public String cariPegawai (@RequestParam(value="idProvinsi", required = false) Optional<Long> idProvinsi, 
									@RequestParam(value="idInstansi", required = false) Optional<Long> idInstansi, 
									@RequestParam(value="idJabatan", required = false) Optional<Long> idJabatan, 
									Model model) {

			List<JabatanModel> jabatanlst = jabatanService.getJabatanList();
			List<InstansiModel> instansilst = instansiService.getInstansiList();
			List<ProvinsiModel> provinsilst = provinsiService.getProvinsiList();


			List<PegawaiModel> allPegawailst = pegawaiService.getPegawaiList();

			List<PegawaiModel> targetPegawailst = new ArrayList();

			
			List<PegawaiModel> pegawaiIterasiJabatan= new ArrayList();
			List<PegawaiModel> targetIterasiInstansi = new ArrayList();
			List<PegawaiModel> targetIterasiDaerah = new ArrayList();
			
			ProvinsiModel targetProv = new ProvinsiModel();
			InstansiModel targetInstansi= new InstansiModel();
			JabatanModel targetJabatan = new JabatanModel();
			
			
			if(idProvinsi.isPresent()) {
				targetProv = provinsiService.getProvinsiDetailById(idProvinsi.get()).get();				
			}
			if(idInstansi.isPresent()) {
				targetInstansi = instansiService.getInstansiDetailById(idInstansi.get()).get();
			}
			if(idJabatan.isPresent()) {
				targetJabatan = jabatanService.getJabatanDetailById(idJabatan.get()).get();							
			}
			
			
			for(PegawaiModel pegawaiTemp : allPegawailst) {
				String daerah = pegawaiTemp.getInstansi().getProvinsi().getNama();
				String instansi = pegawaiTemp.getInstansi().getNama();
				boolean alreadyInlst = false;
				ArrayList<String> jabatan = new ArrayList();

				for(JabatanModel jabatanTemp : pegawaiTemp.getJabatan()) {
					jabatan.add(jabatanTemp.getNama());
				}
				for(PegawaiModel pegawaicek : targetPegawailst) {
					if(pegawaicek.getId() == pegawaiTemp.getId()) {
						alreadyInlst = true;
					}
				}
				
				
				if(idProvinsi.isPresent()) {
					if(targetProv.getNama().equals(daerah) && !alreadyInlst){
						targetPegawailst.add(pegawaiTemp);
						alreadyInlst = true;					
					}
				}
				if(idInstansi.isPresent()) {
					if(targetInstansi.getNama().equals(instansi) && !alreadyInlst){
						targetPegawailst.add(pegawaiTemp);
						alreadyInlst = true;					
					}
				}
				
				if(idJabatan.isPresent()) {
					for(String jabatanTemp : jabatan) {
						if(jabatan.equals(targetJabatan.getNama())&& !alreadyInlst) {						
							targetPegawailst.add(pegawaiTemp);
							alreadyInlst = true;					
						}

					}
				}
				
			}
			
			
			model.addAttribute("pegawailst",targetPegawailst);
			model.addAttribute("jabatanlst",jabatanlst);
			model.addAttribute("instansilst",instansilst);
			model.addAttribute("provinsilst",provinsilst);
			model.addAttribute("title", "Home");
			return "cari-pegawai";
	}

}


