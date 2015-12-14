package frs;

import model.Kelas;
import model.Mahasiswa;
import model.MataKuliah;
import result.Data;
import result.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by gilang on 30/11/2015.
 */
public class MainClass {

    public static void main(String[] args){

        Data data = new Data();
        Validator validator = new Validator(data);

        Mahasiswa mahasiswa = getMahasiswaFromDB(data);
        List<MataKuliah> pilihan = getPilihanMahasiswa(data);

        System.out.println(validator.validate(mahasiswa, pilihan));
    }

    public static Mahasiswa getMahasiswaFromDB(Data data){
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.addMatkulSelesai(data.getMatkulbyName("if001"));
        mahasiswa.addMatkulSelesai(data.getMatkulbyName("if002"));
        mahasiswa.addMatkulSelesai(data.getMatkulbyName("if003"));
        return mahasiswa;
    }

    public static List<MataKuliah> getPilihanMahasiswa(Data data){
        List<MataKuliah> pilihan = new ArrayList<>();
        pilihan.add(data.getMatkulbyName("if004"));
        pilihan.add(data.getMatkulbyName("if010"));
        return pilihan;
    }
}
