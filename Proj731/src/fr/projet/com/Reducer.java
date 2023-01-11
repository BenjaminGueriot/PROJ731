package fr.projet.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Reducer extends Thread{
	
	// id d'identification du thread
	private int id;
	
	private List<HashMap<String, Integer>> liste_maps = new ArrayList<>();
	
	private HashMap<String, Integer> final_map = new HashMap<>();
	
	public Reducer(int id) {
		this.id = id;
	}
	
	
	@Override
	public void run() {
		
		//On tourne dans ca liste de HashMap
		for(HashMap<String, Integer> map : liste_maps) {
			
			//On effectue le calcul des valeurs
			for(String key : map.keySet()) {
				
				
				if(final_map.containsKey(key)) {
					map.put(key, final_map.get(key) + map.get(key));
				} {
					final_map.put(key, map.get(key));
				} 
				
			} 
			
		} 
		
		//On envoi tous dans la HashMap final
		Manager.final_map.putAll(final_map);
		
		
	}
	
	public void addMap(HashMap<String, Integer> map) {
		this.liste_maps.add(map);
		
	}
	
}
