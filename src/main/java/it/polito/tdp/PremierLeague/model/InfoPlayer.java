package it.polito.tdp.PremierLeague.model;

public class InfoPlayer {

	private Player player;
	private Integer peso;
	public InfoPlayer(Player player, Integer peso) {
		super();
		this.player = player;
		this.peso = peso;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return player + " | " + peso;
	}
	
	
}
