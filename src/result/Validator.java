package result;

import model.Kelas;
import model.Mahasiswa;
import model.MataKuliah;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 30/11/2015.
 */
public class Validator {

	Data data;

	public Validator(Data data){
		this.data = data;
	}

	public boolean validate(Mahasiswa mahasiswa, List<MataKuliah> pilihanMatkul){
		if(computeTotalSks(pilihanMatkul) > data.sksmax)
			return false;
		if(!checkPrasyarat(pilihanMatkul, mahasiswa.getMatkulSelesai()))
			return false;
		if(!checkKelas(pilihanMatkul))
			return false;
		return true;
	}

	private boolean checkKelas(List<MataKuliah> matkulDipilih){
		for(MataKuliah m : matkulDipilih) {
			boolean found = false;
			for (Kelas k : data.listKelas) {
				if(m == k.getMataKuliah() && k.getJumlahPeserta() + 1 <= k.getKapasitas()){
					found = true;
				}
			}
			if(!found)
				return false;
		}
		return true;
	}

	private boolean checkPrasyarat(List<MataKuliah> matkulDipilih, List<MataKuliah> matkulSelesai){
		boolean check = true;
		for(MataKuliah mataKuliah : matkulDipilih){
			for(MataKuliah prasyarat : mataKuliah.getMatkulPrasyarat()){
				if(!matkulSelesai.contains(prasyarat))
					check = false;
			}
		}
		return check;
	}

	private int computeTotalSks(List<MataKuliah> listMatkul){
		int total = 0;
		for(MataKuliah m : listMatkul){
			total += m.getJumlahSks();
		}
		return total;
	}
}
