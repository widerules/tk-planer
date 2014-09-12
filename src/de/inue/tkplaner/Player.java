package de.inue.tkplaner;

import java.util.ArrayList;

import android.util.Log;

/**
 * 
 */
public class Player {

	private String name;
	private ArrayList<Player> prevPartners;
	private ArrayList<Player> prevOpponents; // repetitions possible
	
	public Player(String newName){
		this.name = newName;
		this.prevPartners = new ArrayList<Player>();
		this.prevOpponents = new ArrayList<Player>();
	}
	
	public boolean hasPlayedWith(Player otherPlayer){
		boolean result = false;
		for(int i = 0; i < this.prevPartners.size(); i++){
			if(otherPlayer.getName().equals(this.prevPartners.get(i).getName())){
				Log.d("Player", this.name + " has played with " + otherPlayer.getName());
				result = true;
			}
		}
		return result;
	}

	public void newTeam(Player otherPlayer){
		this.prevPartners.add(otherPlayer);
		Log.d("Player", this.name + " played with " + this.prevPartners);
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString(){
	    return "Player " + this.name;
	}

	public void newOpponent(Player p1, Player p2) {
		this.prevOpponents.add(p1);
		this.prevOpponents.add(p2);
		Log.d("Player", this.name + " played against " + this.prevOpponents);
	}
	
	public int nGamesAgainst(Player otherPlayer){
		int result = 0;
		
		for(int i = 0; i < this.prevOpponents.size(); i++){
			if(this.prevOpponents.get(i).getName().equals(otherPlayer.getName()))
				result++;
		}
		return result;
	}
}
