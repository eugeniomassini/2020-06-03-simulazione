package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(Map<Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
				idMap.put(player.getPlayerID(), player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> getVertici(double goal, Map<Integer, Player> idMap) {
		String sql = "select `PlayerID` as id, avg(`Goals`) as media " + 
				"from actions " + 
				"group by `PlayerID` " + 
				"having avg(`Goals`)>?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, goal);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {				
				result.add(idMap.get(res.getInt("id")));
			}
			conn.close();
			System.out.format("Giocatori aggiunti: %d\n", result.size());
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Arco> getArchi(double goal, Map<Integer, Player> idMap) {
		String sql = "select a1.`PlayerID` as uno, a2.`PlayerID` as due, (sum(a1.`TimePlayed`)-sum(a2.`TimePlayed`)) as peso " + 
				"from actions as a1, actions as a2 " + 
				"where a1.`MatchID`=a2.`MatchID` " + 
				"and a1.`Starts`=1 " + 
				"and a2.`Starts`=1 " + 
				"and a1.`TeamID`<>a2.`TeamID` " + 
				"group by a1.`PlayerID`, a2.`PlayerID` ";
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {			
				
				result.add(new Arco(idMap.get(res.getInt("uno")), idMap.get(res.getInt("due")), res.getDouble("peso")));
				
				
			}
			conn.close();
			System.out.format("Archi aggiunti: %d\n", result.size());
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
