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
		createMatkulList();
		createKelasList();
		sksmax = 0;
	}

	private void createMatkulList(){
		listMatkul = new ArrayList<>();

		//available / ga harusnya dari dsl

	}

	private void createKelasList(){

		listKelas = new ArrayList<>();


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
