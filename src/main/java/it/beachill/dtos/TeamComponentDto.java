package it.beachill.dtos;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamComponent;

public class TeamComponentDto {
	private Long id;
	private Long teamId;
	private Long recipientPlayerId;
	private Integer status;
	
	public TeamComponentDto() {
	}
	
	public TeamComponentDto(TeamComponent teamComponent) {
		this.id=teamComponent.getId();
		this.teamId = teamComponent.getTeam().getId();
		this.recipientPlayerId = teamComponent.getPlayer().getId();
		this.status = teamComponent.getStatus();
	}
	public TeamComponent fromDto() {
		TeamComponent teamComponent = new TeamComponent();
		teamComponent.setTeam(new Team(this.teamId));
		teamComponent.setPlayer(new Player(this.recipientPlayerId));
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
	
	public Long getRecipientPlayerId() {
		return recipientPlayerId;
	}
	
	public void setRecipientPlayerId(Long recipientPlayerId) {
		this.recipientPlayerId = recipientPlayerId;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
}
