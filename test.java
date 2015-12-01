package result;

import model.Kelas;
import model.MataKuliah;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 30/11/2015.
 */
public class Data {

	public List<MataKuliah> listMatkul;
	public List<Kelas> listKelas;
	public int sksmax;

	public Data(){
		listMatkul = createMatkulList();
		listKelas = createKelasList();
		sksmax = 22;
	}

	private List<MataKuliah> createMatkulList(){
		List<MataKuliah> listMatkul = new ArrayList<>();

		//available / ga harusnya dari dsl
		listMatkul.add(new MataKuliah("if001", 2, true));
		listMatkul.add(new MataKuliah("if002", 1, true));
		listMatkul.add(new MataKuliah("if003", 2, true));
		listMatkul.add(new MataKuliah("if004", 3, true));
		getMatkulbyName("if004").addPrasyarat(getMatkulbyName("if001"));
		getMatkulbyName("if004").addPrasyarat(getMatkulbyName("if002"));
		listMatkul.add(new MataKuliah("if005", 3, true));
		listMatkul.add(new MataKuliah("if006", 3, true));
		listMatkul.add(new MataKuliah("if007", 4, true));
		getMatkulbyName("if007").addPrasyarat(getMatkulbyName("if004"));
		listMatkul.add(new MataKuliah("if008", 2, true));
		listMatkul.add(new MataKuliah("if009", 1, true));
		listMatkul.add(new MataKuliah("if010", 4, true));
		getMatkulbyName("if010").addPrasyarat(getMatkulbyName("if003"));
		getMatkulbyName("if010").addPrasyarat(getMatkulbyName("if007"));

		return listMatkul;
	}

	private List<Kelas> createKelasList(){

		//defining class capacity
		List<Kelas> listKelas = new ArrayList<>();

		listKelas.add(new Kelas(getMatkulbyName("if001"), 1, 50));
		listKelas.add(new Kelas(getMatkulbyName("if001"), 2, 50));
		listKelas.add(new Kelas(getMatkulbyName("if002"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if003"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if004"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if005"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if006"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if007"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if008"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if009"), 1, 100));
		listKelas.add(new Kelas(getMatkulbyName("if010"), 1, 100));

		return listKelas;
	}

	public MataKuliah getMatkulbyName(String namaMatkul){
		for(MataKuliah m : listMatkul){
			if(m.getNama().equals(namaMatkul)){
				return m;
			}
		}
		return  null;
	}
}
