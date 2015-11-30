package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by gilang on 30/11/2015.
 */
public class DSLParser {
    private List<String> lines;
    private List<matkul> matkulList;
    private List<kelas> kelasList;
    private int sksmax;

    public DSLParser() {
        lines = new ArrayList<>();
        matkulList = new ArrayList<>();
        kelasList = new ArrayList<>();
        sksmax = 0;
    }

    public void parse(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename)).useDelimiter("\n");
        while(sc.hasNext()) {
            String s = sc.next();
            lines.add(s);
        }
        sc.close();

        for(int i=0; i<lines.size(); i++) {
            String currentLine = lines.get(i);

            //parse matkul
            if(currentLine.length()>=6 && currentLine.substring(0,6).equalsIgnoreCase("matkul")) {
                String nama = "";
                String[] prereq = {};
                int sks = 0;
                boolean availability = false;

                nama = lines.get(i+1).split("( )*:( )*")[1].trim();
                String sPre = lines.get(i+2).split("( )*:( )*")[1].trim();
                prereq = sPre.split(" ");
                if(prereq.length==1 && prereq[0].equalsIgnoreCase("")) {
                    prereq = new String[]{};
                }
                sks = Integer.parseInt(lines.get(i+3).split("( )*:( )*")[1].trim());
                String sAv = lines.get(i+4).split("( )*:( )*")[1].trim();
                if(sAv.equalsIgnoreCase("true")) {
                    availability = true;
                }
                else if(sAv.equalsIgnoreCase("false")) {
                    availability = false;
                }

                matkul m = new matkul(nama,prereq,sks,availability);
                matkulList.add(m);
                i+=4;
            }
            //parse jumlah sks
            else if(currentLine.length()>=6 && currentLine.substring(0,6).equalsIgnoreCase("sksmax")) {
                sksmax = Integer.parseInt(currentLine.split("( )*:( )*")[1].trim());
            }
            //parse kelas
            else if(currentLine.length()>=5 && currentLine.substring(0,5).equalsIgnoreCase("kelas")) {
                String kode = "";
                String matkul = "";
                int kapasitas = 0;

                kode = lines.get(i+1).split("( )*:( )*")[1].trim();
                matkul = lines.get(i+2).split("( )*:( )*")[1].trim();
                kapasitas = Integer.parseInt(lines.get(i+3).split("( )*:( )*")[1].trim());

                kelas k = new kelas(kode,matkul,kapasitas);
                kelasList.add(k);
                i+=3;
            }
        }
        //TES
//        for(matkul m: matkulList) System.out.println(m.nama+" "+m.sks+" "+m.prereq.length+" "+m.availability);
//        System.out.println("sm = "+sksmax);
//        for(kelas k: kelasList) System.out.println(k.kode+" "+k.matkul+" "+k.kapasitas);
    }

    public static void main(String[] args) throws FileNotFoundException {
        DSLParser d = new DSLParser();
        d.parse("test.frs");
    }
}
