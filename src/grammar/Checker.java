package grammar;

import ontopt.pen.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gilang on 01/12/2015.
 */
public class Checker {

	String grammarFile;

	public Checker(String grammarFile){
		this.grammarFile = grammarFile;
	}

	public boolean check(String textFile) throws IOException, GrammarException {
		boolean pass = true;
		EarleyParser parser = new EarleyParser(grammarFile);
		SemanticNode node;
		Outputter outputter = new Outputter(System.out);
		ArrayList<SemanticNode> parses;
		String buffer;

		BufferedReader reader = new BufferedReader(new FileReader(textFile));
		boolean modeAppend = false;
		String sentence = "", line;
		while((line = reader.readLine()) != null){
			if(line.contains("{")){
				modeAppend = true;
			}
			if(line.contains("}")){
				modeAppend = false;
				sentence += line;
				parses = parser.parseSentence(new PenSentence(sentence));
				if(parses.size() == 0) {
					pass = false;
					break;
				}
				sentence = "";
				continue;
			}
			if(modeAppend){
				sentence += line;
			}else{
				parses = parser.parseSentence(new PenSentence(line));
				if(parses.size() == 0){
					pass = false;
					break;
				}
			}
		}
		return pass;
	}
}
