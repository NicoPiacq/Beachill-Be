package it.beachill.dtos;

import it.beachill.model.entities.*;

public class PlayerDto {
	private Long id;
	private Long score;
	private Long userId;

	public PlayerDto() {
	}

	public PlayerDto(Player player) {
		this.id = player.getId(); ;
		this.score = player.getScore();
		this.userId = player.getUser().getId();
	}

	public Player fromDto(){
		Player player = new Player();
		player.setScore(this.score);
		player.setUser(new User(this.userId));
		return player;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getScore() {
		return score;
	}
	
	public void setScore(Long score) {
		this.score = score;
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
