package it.polito.tdp.spellchecker;

import java.util.Arrays;
import java.util.LinkedList;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSpell;

    @FXML
    private ComboBox<String> cmbLang;

    @FXML
    private Label lblError;

    @FXML
    private Label lblTime;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtResult;

    @FXML
    void doClearText(ActionEvent event) {
    	
    	this.txtInput.clear();
    	this.txtResult.clear();
    	this.lblError.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	String input = this.txtInput.getText();
    	
    	String[] str = input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "").toLowerCase().split(" ");
    	List<String> parole= new LinkedList<>();
    	
    	this.model.loadDictionary(this.cmbLang.getValue());
    	parole = Arrays.asList(str);
    	List<RichWord> errors = new LinkedList<>();
    	
    	long start = System.nanoTime();
    	errors = this.model.spellCheckTextDichotomic(parole);
    	long end = System.nanoTime();
    	this.lblTime.setText("Spell check completed in " + (end - start)/1000000 + " milliseconds");
    	
    	this.txtResult.clear();
    	for(RichWord r : errors)
    		this.txtResult.appendText(r.getParola() + "\n");
    	this.lblError.setText("The text contains " + errors.size() + " errors");
    	
    }

    @FXML
    void initialize() {
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLang != null : "fx:id=\"cmbLang\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
       this.cmbLang.getItems().add("English");
       this.cmbLang.getItems().add("Italian");
       this.cmbLang.setValue("English");
    }
    
    public void setModel(Dictionary model) {
		this.model = model;
	}

}




/*
list.contains():    Arraylist = 0      			LinkedList = 0
Linear:				ArrayList = 11 ms			LinkedList = 9 ms
Dichotomic:			ArrayList = 7 ms			LinkedList = 5 ms

Facile vedere come la LinkedList è più veloce dell'arrayList, ma solo per liste limitate.
Inoltre la ricerca dicotomica è molto più veloce

*/