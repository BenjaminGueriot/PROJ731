package fr.projet.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Map extends Thread{
	
	// id d'identification du thread
	private int id;

	// Liste de lignes a analyser
	private List<String> liste_lignes;
	
	// Liste des mots Ã  analyser
	private List<String> liste_mots = new ArrayList<>();
	
	// Liste des reducer
	private List<Reducer> liste_reducer;
	
	private int nb_reducer;

	// La Liste contenant les Hashmap resultats
	private List<HashMap<String, Integer>> map_results = new ArrayList<>();
	
	public Map(List<String> liste_lignes, int id, int nb_reducer, List<Reducer> liste_reducer) {
		this.liste_lignes = liste_lignes;
		this.id = id;
		this.nb_reducer = nb_reducer;
		this.liste_reducer = liste_reducer;
		
		createMapForReducers(nb_reducer);
	}
	
	//Creer les HashMap qui seront envoyé au différent reducer
	private void createMapForReducers(int nb_reducer) {
		for (int i = 0; i < nb_reducer; i++) {
			this.map_results.add(new HashMap<String, Integer>()) ;
		}
	}
	
	@Override
	public void run() {
		
		// transforme les lignes en liste de mots
		for(String s : this.liste_lignes) {
			for(String word : s.split(" ")) {
				if(word != "") {
					this.liste_mots.add(word.trim());
				}
			}
		}
		
		//Fonction d'analyse
		for (String mot : this.liste_mots) {
			
			if(!mot.isEmpty()) {
				String sub_value = mot.toLowerCase().trim();
				
				//On effectue le shuffle afin de répartir la donnée dans le bon reducer
				int value =  sub_value.hashCode() & 0x0fffffff;
				value = value % this.nb_reducer;
				
				// On regarde si le mot n'existe pas deja dans la HashMap
				synchronized(map_results) {
					if (map_results.get(value).get(mot) == null) {
						map_results.get(value).put(mot, 1);
					} 
					else {
						map_results.get(value).put(mot, map_results.get(value).get(mot) + 1);
					}
				}
			}

		}
		
		//On envoi la bonne HashMap au bon reducer
		for(Reducer reducer : this.liste_reducer) {
			
			int index = this.liste_reducer.indexOf(reducer);
			
			synchronized(map_results) {
				reducer.addMap(this.map_results.get(index));
			}
			
		}
			
	
	}
	
	public List<HashMap<String, Integer>> getMap_result() {
		return this.map_results;
	}
	
}
