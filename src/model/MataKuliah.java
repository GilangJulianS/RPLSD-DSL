package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 30/11/2015.
 */
public class MataKuliah {

	private String nama;
	private List<MataKuliah> matkulPrasyarat;
	private boolean isAvailable;
	private int sks;

	public MataKuliah(String nama, int jumlahSks){
		this.nama = nama;
		matkulPrasyarat = new ArrayList<>();
		isAvailable = false;
		sks = jumlahSks;
	}

	public MataKuliah(String nama, List<MataKuliah> listPrasyarat, int jumlahSks, boolean isAvailable){
		this.nama = nama;
		matkulPrasyarat = listPrasyarat;
		sks = jumlahSks;
		this.isAvailable = isAvailable;
	}

	public MataKuliah(String nama, int jumlahSks, boolean isAvailable){
		this.nama = nama;
		matkulPrasyarat = new ArrayList<>();
		sks = jumlahSks;
		this.isAvailable = isAvailable;
	}

	public void addPrasyarat(MataKuliah prasyarat){
		matkulPrasyarat.add(prasyarat);
	}

	public void setJumlahSks(int jumlahSks){
		sks = jumlahSks;
	}

	public void setAvailable(boolean available){
		isAvailable = available;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public boolean isAvailable(){
		return isAvailable;
	}

	public int getJumlahSks(){
		return sks;
	}

	public List<MataKuliah> getMatkulPrasyarat(){
		return matkulPrasyarat;
	}

	public String getNama(){
		return nama;
	}
}
