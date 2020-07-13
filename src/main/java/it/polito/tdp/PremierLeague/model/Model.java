package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> graph;
	private Map<Integer, Player> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer, Player>();
		
		dao.listAllPlayers(idMap);
	}
	

	public void creaGrafo(double goal) {
		
		this.graph = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, dao.getVertici(goal, idMap));
		
		for(Arco a: dao.getArchi(goal, idMap)) {
			if(this.graph.vertexSet().contains(a.getP1())&&this.graph.vertexSet().contains(a.getP2())) {
				if(a.getPeso()>0) {
					Graphs.addEdge(this.graph, a.getP1(), a.getP2(), a.getPeso());
				}
			}
			
		}
		
		System.out.format("#Vertici: %d\n#Archi: %d\n", this.graph.vertexSet().size(), this.graph.edgeSet().size());
		
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
