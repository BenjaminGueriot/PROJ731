package fr.projet.com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Manager {

	public static HashMap<String, Integer> final_map = new HashMap<>(); 
	
	public static void main(String args[]) {
	
		//Manager.startAction((Paths.get("mon_combat").toAbsolutePath()).toString(), 4, 2);
		new DragAndDropFile();
	
	}

	public static void startAction(String link, int nb_mapper, int nb_reducer) {
		
		// On initialise un instant
		Instant inst1 = Instant.now();
		
		List<Reducer> reducer_list = new ArrayList<>();
		
		List<Map> mapper_list = new ArrayList<>();
		
		// Creation des reducer
		for(int i = 0; i < nb_reducer; i++) {
			
			reducer_list.add(new Reducer(i + 1));
			
		}
		
		
		try {
			String text = new String(Files.readAllBytes(Paths.get(link)));
			//met l'entièreté du texte en minuscule
	        text = text.toLowerCase();
	        // Retire la ponctuation
	        String charac = "'";
	        char charac2 = '"';
	        // Replace les charactere spéciaux par des espaces
	        text = text.replaceAll("[,-.:!?*_;(){}" + charac + charac2 + "]", " ");
	        // Ecrit le text modifie dans un nouveau fichier
	        Files.write(Paths.get("clean.txt").toAbsolutePath(), text.getBytes());
	    } 
		catch (IOException e) {
	        e.printStackTrace();
	    } 
		
		String link_cleaned = "" + Paths.get("clean.txt").toAbsolutePath();
		
		List<String> lignes = new ArrayList<String>();

		//Decompose les fichier text en un ensemble de lignes
		try (BufferedReader br = new BufferedReader(new FileReader(link_cleaned))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       lignes.add(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int nb_lines = lignes.size();
		int nb_lines_devided = lignes.size() / nb_mapper;
		
		List<Integer> values = new ArrayList<>();
		
		int val = 0;
		for(int i = 0; i < nb_mapper + 1; i++) {
			
			val += nb_lines_devided;
			values.add(val);
			
		}
		
		List<List<String>> lignes_to_send = new ArrayList<>();
		
		// Separe les lignes dans des listes en fonction du nombre de mapper
		for(int i = 0; i < nb_lines; i+= nb_lines_devided) {
				
			List<String> liste;
			if (i + nb_lines_devided > nb_lines) {
				liste = lignes.subList(i, nb_lines);
				
			} else {
				liste = lignes.subList(i, i + nb_lines_devided);	
			}
			
			lignes_to_send.add(liste);
			
		}
		
		
		//Creation des mapper et demarrage
		for (int i = 0; i < nb_mapper; i++) {
			Map mapper = new Map(lignes_to_send.get(i),i + 1,nb_reducer, reducer_list);
			mapper_list.add(mapper);
			mapper.start();
		}
		
		//Attente de la fin de l'execution des mapper
		try {
			for(Map map : mapper_list) {
				map.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Lancement des reducer
		for(Reducer reducer : reducer_list) {
			reducer.start();
		}
		
		
		//Attente de la fin de l'execution des reducer
		try {
			for(Reducer reducer : reducer_list) {
				reducer.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Appel la fonction d'écriture
		try {
			writeAndDisplayMap(final_map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//On initialise un second instant et on affiche le temps passe entre les deux
		Instant inst2 = Instant.now();
		System.out.println("Temps : " + Duration.between(inst1, inst2).toString());
			
	} 
	
	
// Ecrit dans un fichier le resultat
private static void writeAndDisplayMap(HashMap<String, Integer> map) throws IOException {

		FileWriter myWriter = new FileWriter("results.txt");
		
		map.forEach((k, v) -> {
			String str = k + " : " + v;
			try {
				myWriter.write(str + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println("Done");
		
		myWriter.close();

	}
	
}
