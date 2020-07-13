package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

// SIMULAZIONE:
//Creare get per dare in output al controller i risulati

public class Model {

	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> graph;
	private Map<Integer, Player> idMapPlayer;
	private Map<Integer, Match> idMapMatch;
	private Map<Integer, Team> idMapTeam;

	public Model() {
		dao = new PremierLeagueDAO();
		idMapPlayer = new HashMap<Integer, Player>();
		idMapMatch = new HashMap<Integer, Match>();
		idMapTeam = new HashMap<Integer, Team>();

		dao.listAllPlayers(idMapPlayer);
		/*
		 * dao.listAllMatches(idMapMatch); 
		 * dao.listAllTeams(idMapTeam);
		 */
	}

	public Set<?> vertexSet() {
		return this.graph.vertexSet();
	}
	
	public Set<?> edgeSet(){
		return this.graph.edgeSet();
	}
	
	public void creaGrafo(Object parametro) {

		//		Creo il nuovo grafo
		System.out.println("***CREO GRAFO***\n");
		//Orientato e pesato
		this.graph = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//Non orientato e pesato
		//		this.graph = new SimpleWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//		Aggiungo i vertici
		System.out.println("Aggiungo i vertici\n");
		//		Graphs.addAllVertices(this.graph, dao.getVertici(parametro, idMap?));

//		Aggiungo gli ARCHI

		//Grafo orientato
		/*
		 * for(Arco a: dao.getArchi(parametro, idMapPlayer)) {
		 * if(this.graph.vertexSet().contains(a.getP1())&&this.graph.vertexSet().
		 * contains(a.getP2())) { if(a.getPeso()>0) { Graphs.addEdge(this.graph,
		 * a.getP1(), a.getP2(), a.getPeso()); } }
		 * 
		 * }
		 */


//		Grafo non orientato
		for(Arco a: dao.getArchi(parametro, idMapPlayer)) {
			DefaultWeightedEdge e = this.graph.getEdge(a.getP1(), a.getP2());
			if(e==null) {
				//Aggiungo arco e vertici
				Graphs.addEdgeWithVertices(this.graph, a.getP1(), a.getP2(), a.getPeso());
				
				//Aggiungo solo l'arco
				Graphs.addEdge(this.graph, a.getP1(), a.getP2(), a.getPeso());
				
				System.out.format("Aggiunto arco: %s\n", a);
			}
		}

		//		Stampa #Vertici e #Archi
		System.out.format("#Vertici: %d\n#Archi: %d\n", this.graph.vertexSet().size(), this.graph.edgeSet().size());

	}

//	Scenario in cui devo gettare i vicini
	public List<?> getVicini(Object parametro){
		System.out.println("***CREO LISTA DI VICINI***");
		List<?> result = new ArrayList<>();
		
		for(Object o: Graphs.neighborListOf(this.graph, parametro)) {
			//ottengo il peso
			Double peso = this.graph.getEdgeWeight(this.graph.getEdge(parametro, o));
			//Creo oggetto infoqualcosa per contenere le info che mi servono
			InfoQualcosa = new InfoQualcosa(o, peso);
		}
		
		//La ordino se devo con un comparatore esterno ad hoc
		/*Collections.sort(result, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		})*/
		
		System.out.println("Vado a stampare la lista");
		
		return result;
	}
	
//	Scenario in cui devo trovare i connessi o percorso
	public List<?> esploroGrafo(){
		List<?> result = new ArrayList<>();

		//Metodo 1: visita per ampiezza

		BreadthFirstIterator<?, DefaultWeightedEdge> iterator = new
				BreadthFirstIterator<?, DefaultWeightedEdge>(this.grafo, partenza);
		while(iterator.hasNext()) {
			result.add(iterator.next()); 
		}
		//elimino il punto di partenza
		result.remove(0);


		//Metodo 2: connectivity inspector, restituisce tutti i vertici maggiormente connessi
		/*
		 * ConnectivityInspector<Actor, DefaultWeightedEdge> inspector = new ConnectivityInspector<Actor, DefaultWeightedEdge>(this.grafo);
		 * attoriSimili.addAll(inspector.connectedSetOf(partenza));
		 */

//		COMPARATORE NEL CASO SERVISSE
		
		/*
		 * Collections.sort(result, new Comparator<Actor>(){
		 * 
		 * @Override public int compare(Actor o1, Actor o2) { // TODO Auto-generated
		 * method stub return o1.lastName.compareTo(o2.lastName); }
		 * 
		 * });
		 */

		return result;
	}
	
	public void runSim() {
		
		System.out.println("***FACCIO PARTIRE LA SIMULAZIONE***");
		
//		CREO IL SIMULATORE E PASSO I PARAMETRI DI SIMULAZIONE
		
		//Potrei dover passare modello e grafo
		Simulator sim = new Simulator();
		
//		ESEGUO LA SIMULAZIONE
		//Passo parametri per far partire la simulazione
		sim.iniz();
		
		
	}
	
	
	
	
	
	
	
	
	public List<InfoPlayer> getTopPlayer() {

		Player TopPlayer = null;
		int battutiMax =0;

		for(Player p: this.graph.vertexSet()) {
			if(this.graph.outDegreeOf(p)>battutiMax) {
				battutiMax=this.graph.outDegreeOf(p);
				TopPlayer=p;
			}
		}

		List<InfoPlayer> output = new ArrayList<InfoPlayer>();

		for(DefaultWeightedEdge d: this.graph.outgoingEdgesOf(TopPlayer)) {
			output.add(new InfoPlayer(this.graph.getEdgeTarget(d), (int)this.graph.getEdgeWeight(d)));
		}
		Collections.sort(output, new Comparator<InfoPlayer>() {

			@Override
			public int compare(InfoPlayer o1, InfoPlayer o2) {
				// TODO Auto-generated method stub
				return -Double.compare(o1.getPeso(), o2.getPeso());
			}

		});

		output.add(0, new InfoPlayer(TopPlayer, 0));

		return output;
	}

}
