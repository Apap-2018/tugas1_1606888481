package com.apap.tugas1.model;

import java.util.Comparator;

public class PegawaiAgeComparator implements Comparator<PegawaiModel> {

	@Override
	public int compare(PegawaiModel pegawai1, PegawaiModel pegawai2) {
		return pegawai1.getTanggal_lahir().compareTo(pegawai2.getTanggal_lahir());
	}
}

