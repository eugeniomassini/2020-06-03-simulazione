/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.InfoPlayer;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="btnTopPlayer"
	private Button btnTopPlayer; // Value injected by FXMLLoader

	@FXML // fx:id="btnDreamTeam"
	private Button btnDreamTeam; // Value injected by FXMLLoader

	@FXML // fx:id="txtK"
	private TextField txtK; // Value injected by FXMLLoader

	@FXML // fx:id="txtGoals"
	private TextField txtGoals; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

//	PRIMO METODO CREAGRAFO
	
	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();

//		Scenario parametro da inserire 
		
		/*
		 * Match match = boxMatch.getValue(); 
		 * Team team = boxTeam.getValue(); 
		 * Player player = boxPlayer.getValue();
		 */
		
		Object parametro = null;
		if(parametro==null) {
			txtResult.setText(String.format("Errore scegliere un valore nel box di %s", parametro.getClass()));
			return;
		}
		
//		model.creaGrafo(parametro);
		
//		Scenario conversione
		
		String stringaDaConvertire = txtGoals.getText();

		double convertitoD =0.0;
		int convertitoI =0;
		
		try {
			convertitoD = Double.parseDouble(stringaDaConvertire);
		} catch (NumberFormatException e) {
			txtResult.setText("Errore");
			e.printStackTrace();
			return;
		}

		model.creaGrafo(convertitoD);
		
	}

//	SECONDO METODO CERCARE QUALCOSA
	
	@FXML
	void doStampaVicini(ActionEvent event) {
		txtResult.clear();
		
//		Controllo se ho creato il grafo
		
		if(model.vertexSet().isEmpty() && model.edgeSet().isEmpty()) {
			txtResult.setText("Errore, selezionare un valore e creare il grafo");
			return;
		}
		
//		Se l'ho creato cerco il vicino e ricontrollo l'errore

//		Copio pezzo da sopra in base al parametro
		
		//STAMPA DEL PARAMETRO DI PARTENZA
//		txtResult.appendText(String.format("Traovati a partire da: %s", paramentroPartenza));
		
//		Scenario li stampo tutti
		for(InfoQualcosa a: model.getVicini(parametro)) {
			txtResult.appendText(String.format("%s\n", a));
		}
		
//		Scenario solo un numero N
		int N = 5;
		List <?> daStampare = model.getVicini(parametro);
		for(int i=0; i<N; i++) {
			txtResult.appendText(String.format("%s\n", daStampare.get(i));
		}
	}
	
	@FXML
	void doEsplora(ActionEvent event) {
		txtResult.clear();
//		Avrò un punto di partenza e trovo tutti i collegati
		
//		CONTROLLI VARI
		
		if(campo == null) {
			txtResult.setText("Errore");
			return;
		}
		
		for(Object o: model.getVicini(parametro)) {
			txtResult.appendText(String.format("%s\n", o);
		}
		

	}
	
	@FXML
	void doSimulazione(ActionEvent event) {
		
//		OTTENGO I PARAMETRI DI PARTENZA
		
//		CONTROLLO I PARAMETRI
		
//		FACCIO PARTIRE IL SIMULATORE
		model.runSim();
		
//		STAMPO I RISULTATI
//		txtResult.appendText(String.format(format, args));

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		
	}
}
