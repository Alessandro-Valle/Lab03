package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Dictionary {
	
	private List<String> dizionario;
	
	public void loadDictionary(String language) {
		
		String s = "";
		if(language.equals("")) {
			System.out.println("DEVI SELEZIONARE LA LINGUA");
			return;
		}
		if(language.equals("English") || language.equals("Italian"))
			s = "src/main/resources/" +  language + ".txt";
		
		try {
			FileReader fr = new FileReader(s);   // Cambiare successivamente "english" in language
			BufferedReader br = new BufferedReader(fr);
			String word;
			List<String> d = new ArrayList<>();
			while ((word = br.readLine()) != null) {
				d.add(word.toLowerCase());
			}
			dizionario = d;
			br.close();
			} catch (IOException e){
			System.out.println("Errore nella lettura del file");
			}
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		
		List<RichWord> err = new LinkedList<>();
		boolean check = false;
		for(String s : inputTextList) {
			for(String dict : this.dizionario) {
				if(s.equals(dict)) {
					check = true;
					break;
				}
			}
			if(!check)
				err.add(new RichWord(s, check));
			check = false;
		}
		return err;
	}
	
public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		
		List<RichWord> err = new ArrayList<>();
		boolean check = false;
		
		int mid = this.dizionario.size() / 2;

		for(String s : inputTextList) {
			
			if(s.equals(dizionario.get(mid)))
				check = true;
			if(s.compareTo(dizionario.get(mid)) < 0) {
				for(int i = 0; i < mid; i++) {
					if(s.equals(dizionario.get(i))) {
						check = true;
						break;
					}
				}
			}
			if(s.compareTo(dizionario.get(mid)) > 0){
				for(int i = mid; i < dizionario.size(); i++) {
					if(s.equals(dizionario.get(i))) {
						check = true;
						break;
					}
				}
			}
			
			if(!check)
				err.add(new RichWord(s, check));
			check = false;
		}
		return err;
	}
	
	
}
