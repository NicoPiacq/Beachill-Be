package it.beachill.dtos;

import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.SetMatch;

public class SetMatchDto {
	private Long id;
	private Long matchId;
	private int setNumber;
	private int homeTeamScore;
	private int awayTeamScore;
	
	public SetMatchDto() {
	}
	
	public SetMatchDto(SetMatch setMatch) {
		this.id = setMatch.getId();
		this.matchId = setMatch.getMatch().getId();
		this.setNumber = setMatch.getSetNumber();
		this.homeTeamScore = setMatch.getHomeTeamScore();
		this.awayTeamScore = setMatch.getAwayTeamScore();
	}
	
	public SetMatch fromDto(){
		SetMatch setMatch = new SetMatch();
		setMatch.setId(this.id);
		setMatch.setMatch(new Match(this.matchId));
		setMatch.setSetNumber(this.setNumber);
		setMatch.setHomeTeamScore(this.homeTeamScore);
		setMatch.setAwayTeamScore(this.awayTeamScore);
		return setMatch;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMatchId() {
		return matchId;
	}
	
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
	public int getSetNumber() {
		return setNumber;
	}
	
	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}
	
	public int getHomeTeamScore() {
		return homeTeamScore;
	}
	
	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	
	public int getAwayTeamScore() {
		return awayTeamScore;
	}
	
	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}
}
