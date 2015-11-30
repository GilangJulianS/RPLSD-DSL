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

    public DSLParser() {
        lines = new ArrayList<>();
    }

    public void parse(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename)).useDelimiter("\n");

        while(sc.hasNext()) {
            String s = sc.next();
            lines.add(s);
        }
        sc.close();

    }

    public static void main(String[] args) throws FileNotFoundException {
        DSLParser d = new DSLParser();
        d.parse("test.frs");
    }
}
