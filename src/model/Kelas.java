package model;

/**
 * Created by gilang on 30/11/2015.
 */
public class Kelas {

	int kode;
	private MataKuliah mataKuliah;
	private int kapasitas;
	private int jumlahPeserta;

	public Kelas(MataKuliah mataKuliah, int kodeKelas, int kapasitas){
		this.mataKuliah = mataKuliah;
		this.kapasitas = kapasitas;
		kode = kodeKelas;
		jumlahPeserta = 0;
	}

	public void tambahPeserta(){
		jumlahPeserta++;
	}

	public void tambahPeserta(int jumlahSiswa){
		jumlahSiswa += jumlahSiswa;
	}

	public void setMataKuliah(MataKuliah mataKuliah, int kodeKelas){
		this.mataKuliah = mataKuliah;
		kode = kodeKelas;
	}

	public void setKapasitas(int kapasitas){
		this.kapasitas = kapasitas;
	}

	public void setKode(int kodeKelas){
		kode = kodeKelas;
	}

	public void setJumlahPeserta(int jumlahPeserta){
		this.jumlahPeserta = jumlahPeserta;
	}

	public int getKapasitas(){
		return kapasitas;
	}

	public int getKode(){
		return kode;
	}

	public MataKuliah getMataKuliah(){
		return mataKuliah;
	}

	public int getJumlahPeserta(){
		return jumlahPeserta;
	}
}
