package it.beachill.dtos;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.TeamComponent;

public class TeamComponentDto {
	private Long id;
	private Long teamId;
	private Long PlayerId;
	private String playerName;
	private String playerSurname;
	private Integer status;
	
	public TeamComponentDto() {
	}
	
	public TeamComponentDto(TeamComponent teamComponent) {
		this.id=teamComponent.getId();
		this.teamId = teamComponent.getTeam().getId();
		this.PlayerId = teamComponent.getPlayer().getId();
		this.playerName = teamComponent.getPlayer().getUser().getName();
		this.playerSurname = teamComponent.getPlayer().getUser().getSurname();
		this.status = teamComponent.getStatus();
	}
	public TeamComponent fromDto() {
		TeamComponent teamComponent = new TeamComponent();
		teamComponent.setTeam(new Team(this.teamId));
		teamComponent.setPlayer(new Player(this.PlayerId));
		if (this.status == null) {
			teamComponent.setStatus(2);
		} else {
			teamComponent.setStatus(this.status);
		}
			return teamComponent;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTeamId() {
		return teamId;
	}
	
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	public Long getPlayerId() {
		return PlayerId;
	}
	
	public void setPlayerId(Long playerId) {
		this.PlayerId = playerId;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerSurname() {
		return playerSurname;
	}

	public void setPlayerSurname(String playerSurname) {
		this.playerSurname = playerSurname;
	}
}
