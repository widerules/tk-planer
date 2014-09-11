package de.inue.tkplaner;

import java.util.ArrayList;

import android.util.Log;

/**
 * 
 */
public class Player {

	private String name;
	private ArrayList<Player> prevPartners;
	
	public Player(String newName){
		this.name = newName;
		this.prevPartners = new ArrayList<Player>();
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
}
