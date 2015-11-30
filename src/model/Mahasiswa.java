package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 30/11/2015.
 */
public class Mahasiswa {

	private List<MataKuliah> matkulSelesai;

	public Mahasiswa(){
		matkulSelesai = new ArrayList<>();
	}

	public Mahasiswa(List<MataKuliah> matkulSelesai){
		this.matkulSelesai = matkulSelesai;
	}

	public void addMatkulSelesai(MataKuliah mataKuliah){
		matkulSelesai.add(mataKuliah);
	}

	public List<MataKuliah> getMatkulSelesai(){
		return matkulSelesai;
	}
}
