package de.inue.tkplaner;

import java.util.ArrayList;

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
			if(otherPlayer.getName().equals(this.name))
				result = true;
		}
		return result;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString(){
	    return "Player " + this.name;
	}
}
