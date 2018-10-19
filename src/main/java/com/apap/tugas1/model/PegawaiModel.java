package com.apap.tugas1.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Comparable<PegawaiModel>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique= true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempat_lahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggal_lahir;

	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahun_masuk;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jabatan_pegawai", joinColumns = @JoinColumn(name = "id_pegawai"), inverseJoinColumns = @JoinColumn(name = "id_jabatan"))
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private List<JabatanModel> jabatan;

	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNip() {
		return nip;
	}


	public void setNip(String nip) {
		this.nip = nip;
	}


	public String getNama() {
		return nama;
	}


	public void setNama(String nama) {
		this.nama = nama;
	}


	public String getTempat_lahir() {
		return tempat_lahir;
	}


	public void setTempat_lahir(String tempat_lahir) {
		this.tempat_lahir = tempat_lahir;
	}


	public Date getTanggal_lahir() {
		return tanggal_lahir;
	}


	public void setTanggal_lahir(Date tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}


	public String getTahun_masuk() {
		return tahun_masuk;
	}


	public void setTahun_masuk(String tahun_masuk) {
		this.tahun_masuk = tahun_masuk;
	}


	public InstansiModel getInstansi() {
		return instansi;
	}


	public List<JabatanModel> getJabatan() {
		return jabatan;
	}


	public void setJabatan(List<JabatanModel> jabatan) {
		this.jabatan = jabatan;
	}


	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}


	@Override
	public int compareTo(PegawaiModel pegawaiLain) {
		return this.tanggal_lahir.compareTo(pegawaiLain.getTanggal_lahir()) ;
	}


}
