package parser;

import grammar.Checker;
import ontopt.pen.GrammarException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by gilang on 30/11/2015.
 */
public class DSLParser {

    private List<String> lines;
    private List<MatkulHelper> matkulList;
    private List<KelasHelper> kelasList;
    private int sksmax;
    private static final String SPLIT_TOKEN = "\\W";

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

                for(int j=1; j<5; j++) {
                    String[] lineGroup = filterSplit(lines.get(i + j).split(SPLIT_TOKEN));
                    if(lineGroup.length > 1) {
                        if (lineGroup[0].equals("nama"))
                            nama = lineGroup[1];
                        if (lineGroup[0].equals("prereq")) {
                            prereq = new String[lineGroup.length - 1];
                            for(int x=1; x<lineGroup.length; x++){
                                prereq[x-1] = lineGroup[x];
                            }
                        }
                        if (lineGroup[0].equals("sks"))
                            sks = Integer.parseInt(lineGroup[1]);
                        if (lineGroup[0].equals("available")) {
                            String sAv = lineGroup[1];
                            if (sAv.equalsIgnoreCase("true")) {
                                availability = true;
                            } else if (sAv.equalsIgnoreCase("false")) {
                                availability = false;
                            }
                        }
                    }
                }

                MatkulHelper m = new MatkulHelper(nama,prereq,sks,availability);
                matkulList.add(m);
                i+=4;
            }
            //parse jumlah sks
            else if(currentLine.length()>=6 && currentLine.substring(0,6).equalsIgnoreCase("sksmax")) {
                String[] lineGroup = filterSplit(currentLine.split(SPLIT_TOKEN));
                if(lineGroup.length > 1)
                    sksmax = Integer.parseInt(lineGroup[1]);
            }
            //parse kelas
            else if(currentLine.length()>=5 && currentLine.substring(0,5).equalsIgnoreCase("kelas")) {
                String kode = "";
                String matkul = "";
                int kapasitas = 0;

                for(int j=1; j<4; j++) {
                    String[] lineGroup = filterSplit(lines.get(i + j).split(SPLIT_TOKEN));
                    if(lineGroup.length > 1) {
                        if (lineGroup[0].equals("kode"))
                            kode = lineGroup[1];
                        if (lineGroup[0].equals("matkul"))
                            matkul = lineGroup[1];
                        if (lineGroup[0].equals("kapasitas"))
                            kapasitas = Integer.parseInt(lineGroup[1]);
                    }
                }

                KelasHelper k = new KelasHelper(kode,matkul,kapasitas);
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
                "\t\tcreateMatkulList();\n" +
                "\t\tcreateKelasList();\n" +
                "\t\tsksmax = "+sksmax+";\n" +
                "\t}\n" +
                "\n" +
                "\tprivate void createMatkulList(){\n" +
                "\t\tlistMatkul = new ArrayList<>();\n" +
                "\n" +
                "\t\t//available / ga harusnya dari dsl\n";

        //add MatkulHelper & prereqnya
        String line = "";
        for(int i=0; i<matkulList.size(); i++) {
            MatkulHelper m = matkulList.get(i);
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
                "\t}\n" +
                "\n" +
                "\tprivate void createKelasList(){\n" +
                "\n" +
                "\t\tlistKelas = new ArrayList<>();\n" +
                "\n";

        //add KelasHelper
        for(int i=0; i<kelasList.size(); i++) {
            KelasHelper k = kelasList.get(i);
            line = "\t\tlistKelas.add(new Kelas(getMatkulbyName(\""+k.matkul+"\"), "+k.kode+", "+k.kapasitas+"));\n";
            output += line;
        }

        output += "\n" +
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

    public String[] filterSplit(String[] strings){
        List<String> list = new ArrayList<>();
        for(int i=0; i<strings.length; i++){
            if(strings[i].length() > 0)
                list.add(strings[i]);
        }
        String[] retStrings = new String[list.size()];
        for(int i=0; i<list.size(); i++){
            retStrings[i] = list.get(i);
        }
        return retStrings;
    }

    public static void main(String[] args) throws IOException, GrammarException {
        DSLParser d = new DSLParser();
        Checker checker = new Checker(args[0]);
        if(checker.check(args[1])) {
            d.generate(args[1], "Data.java");
        }else{
            System.out.println("Terdapat kesalahan grammar pada file input");
        }
    }
}
