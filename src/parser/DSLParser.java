package parser;

import java.io.*;
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
    }

    public void generate(String infile, String outfile) throws FileNotFoundException {
        parse(infile);
        String output = "package result;\n" +
                "\n" +
                "import model.Kelas;\n" +
                "import model.MataKuliah;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * Created by gilang on 30/11/2015.\n" +
                " */\n" +
                "public class Data {\n" +
                "\n" +
                "\tpublic List<MataKuliah> listMatkul;\n" +
                "\tpublic List<Kelas> listKelas;\n" +
                "\tpublic int sksmax;\n" +
                "\n" +
                "\tpublic Data(){\n" +
                "\t\tlistMatkul = createMatkulList();\n" +
                "\t\tlistKelas = createKelasList();\n" +
                "\t\tsksmax = "+sksmax+";\n" +
                "\t}\n" +
                "\n" +
                "\tprivate List<MataKuliah> createMatkulList(){\n" +
                "\t\tList<MataKuliah> listMatkul = new ArrayList<>();\n" +
                "\n" +
                "\t\t//available / ga harusnya dari dsl\n";

        //add matkul & prereqnya
        String line = "";
        for(int i=0; i<matkulList.size(); i++) {
            matkul m = matkulList.get(i);
            line = "\t\tlistMatkul.add(new MataKuliah(\""+m.nama+"\", "+m.sks+", "+m.availability+"));\n";
            output += line;
            if(m.prereq.length>0) {
                for(int j=0; j<m.prereq.length; j++) {
                    line = "\t\tgetMatkulbyName(\""+m.nama+"\").addPrasyarat(getMatkulbyName(\""+m.prereq[j]+"\"));\n";
                    output += line;
                }
            }
        }

        output += "\n" +
                "\t\treturn listMatkul;\n" +
                "\t}\n" +
                "\n" +
                "\tprivate List<Kelas> createKelasList(){\n" +
                "\n" +
                "\t\t//defining class capacity\n" +
                "\t\tList<Kelas> listKelas = new ArrayList<>();\n" +
                "\n";

        //add kelas
        for(int i=0; i<kelasList.size(); i++) {
            kelas k = kelasList.get(i);
            line = "\t\tlistKelas.add(new Kelas(getMatkulbyName(\""+k.matkul+"\"), "+k.kode+", "+k.kapasitas+"));\n";
            output += line;
        }

        output += "\n" +
                "\t\treturn listKelas;\n" +
                "\t}\n" +
                "\n" +
                "\tpublic MataKuliah getMatkulbyName(String namaMatkul){\n" +
                "\t\tfor(MataKuliah m : listMatkul){\n" +
                "\t\t\tif(m.getNama().equals(namaMatkul)){\n" +
                "\t\t\t\treturn m;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\treturn  null;\n" +
                "\t}\n" +
                "}\n";

        //write to output
        PrintStream ps = new PrintStream(new FileOutputStream(outfile));
        ps.print(output);
        ps.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        DSLParser d = new DSLParser();
        d.generate("test.frs","test.java");
    }
}
