package it.beachill.model.services.implementation;

import it.beachill.dtos.MatchDto;
import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.MatchsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JPAMatchsService implements MatchsService {

    public static final String DEFAULT_SCORE_TYPE = "GENERALE";
    public static final String BASE_SCORE_TYPE = "BASE";
    public static final String INTERMEDIATE_SCORE_TYPE = "INTERMEDIO";
    public static final String ADVANCE_SCORE_TYPE = "AVANZATO";

    private final MatchRepository matchRepository;
    private final SetMatchRepository setMatchRepository;
    private final ScoreTypeRepository scoreTypeRepository;
    private final ScoreRepository scoreRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public JPAMatchsService(MatchRepository matchRepository, SetMatchRepository setMatchRepository, ScoreTypeRepository scoreTypeRepository, ScoreRepository scoreRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.setMatchRepository = setMatchRepository;
        this.scoreTypeRepository = scoreTypeRepository;
        this.scoreRepository = scoreRepository;
        this.teamRepository = teamRepository;
    }

    public List<Match> getAllMatchesByTournament(Long tournamentId){
        return matchRepository.findByTournamentId(tournamentId);
    }
    
    @Override
    public void updateMatchSetResult(User user, Long setMatchId, SetMatchDto setMatchDto) throws CheckFailedException {
        if(!Objects.equals(setMatchId, setMatchDto.getMatchId())){
            throw new CheckFailedException("I dati del set non sono coerenti");
        }
        Optional<SetMatch> setMatchOptional = setMatchRepository.findById(setMatchId);
        if(setMatchOptional.isEmpty()){
            throw new CheckFailedException("Il set che vuoi modificare non esiste");
        }
        Optional<Match> match = matchRepository.findById(setMatchId);
        if(match.isEmpty()){
            throw new CheckFailedException("Il match associato al set non esiste");
        }
        if(match.get().getMatchAdmin().getId() != user.getId()){
            throw new CheckFailedException("Non sei l' admin del match.");
        }
        setMatchRepository.save(setMatchDto.fromDto());
    }

    @Override
    public List<SetMatch> getAllSetsByMatchId(Long matchId) throws CheckFailedException {
        Optional<Match> matchOptional = matchRepository.findById(matchId);
        if(matchOptional.isEmpty()){
            throw new CheckFailedException("Il match selezionato non esiste.");
        }
        return setMatchRepository.findByMatchId(matchId);
    }
    
    @Transactional
    public void updateMatchResultAndPlayersScore(User user, Long matchId) throws CheckFailedException {
        Optional<Match> matchOptional = matchRepository.findById(matchId);
        if(matchOptional.isEmpty()){
            throw new CheckFailedException("Il match non esiste.");
        }
        if(matchOptional.get().getMatchAdmin().getId() != user.getId()){
            throw new CheckFailedException("Non sei l' admin del match.");
        }
        Match match= matchOptional.get();
        updateMatchResult(match);
        updatePlayersScore(match);
    }
    
    private void updateMatchResult(Match match) throws CheckFailedException {
        List<SetMatch> setMatchList = setMatchRepository.findByMatchId(match.getId());
        if(setMatchList.isEmpty()){
            throw new CheckFailedException("Non esistono set per questo match.");
        }

        int homeTeamSetWins = 0;
        int awayTeamSetWins = 0;
        float homeTeamPointsScored = 0;
        float awayTeamPointsScored = 0;
        for(SetMatch setMatch : setMatchList){
            if(setMatch.getHomeTeamScore() > setMatch.getAwayTeamScore()){
                homeTeamSetWins++;
            } else {
                awayTeamSetWins++;
            }
            homeTeamPointsScored += setMatch.getHomeTeamScore();
            awayTeamPointsScored += setMatch.getAwayTeamScore();
        }
        if(homeTeamSetWins > awayTeamSetWins){
            match.setWinnerTeam(match.getHomeTeam());
        } else if(awayTeamSetWins > homeTeamSetWins){
            match.setWinnerTeam(match.getAwayTeam());
        }else {
            if(homeTeamPointsScored > awayTeamPointsScored){
                match.setWinnerTeam(match.getHomeTeam());
            }
            else if(awayTeamPointsScored > homeTeamPointsScored){
                match.setWinnerTeam(match.getAwayTeam());
            } else{
                throw new CheckFailedException("Il numero di set vinti e punti fatti-subiti sono uguali, aggiungi un set o tira una monetina.");
            }
        }
    }
    
    private void updatePlayersScore(Match match) {

//      //ABBIAMO TOLTO LO SCORE DAL PLAYER DATO CHE ADESSO è ALL' INTERNO DELLA TABELLA SCORE E GIUSTAMENTE QUA MUORE :( Rip
        
        //controllare il tipo del match
        String scoreTypeString;
        if (match.getTournament() != null) {
            scoreTypeString = match.getTournament().getTournamentLevel().getLevelName();
        } else {
            scoreTypeString = match.getMatchType().getType();
        }
        ScoreType scoreType = scoreTypeRepository.findById(scoreTypeString).get();
        
        double homeAverageScore = match.getHomeTeam().getTeamComponents().stream().map(TeamComponent::getPlayer)
                .map(p -> p.getScoreForType(scoreType)).filter(Optional::isPresent).mapToDouble(Optional::get).average().orElse(0);
        double awayAverageScore = match.getAwayTeam().getTeamComponents().stream().map(TeamComponent::getPlayer)
                .map(p -> p.getScoreForType(scoreType)).filter(Optional::isPresent).mapToDouble(Optional::get).average().orElse(0);
        
        boolean homeWon = match.getWinnerTeam().equals(match.getHomeTeam());
        double ratio = homeWon ? awayAverageScore / homeAverageScore : homeAverageScore / awayAverageScore;
        match.getWinnerTeam().getTeamComponents().stream().map(TeamComponent::getPlayer).forEach(p -> {
            Optional<Score> scoreOptional = scoreRepository.findByPlayerAndScoreType(p, scoreType);
            Score sc = scoreOptional.get();
            sc.addScore((int) Math.round(scoreType.getBaseWinScore() *ratio));
        });
        Team losingTeam=match.getWinnerTeam().equals(match.getHomeTeam()) ? match.getAwayTeam() : match.getHomeTeam();
        losingTeam.getTeamComponents().stream().map(TeamComponent::getPlayer).forEach(p -> {
            Optional<Score> scoreOptional = scoreRepository.findByPlayerAndScoreType(p, scoreType);
            Score sc = scoreOptional.get();
            sc.subtractScore((int) Math.round(scoreType.getBaseWinScore() *ratio));
        });
    }

    @Override
    public List<Match> getAllMatchesByAdmin(User user) {
        return matchRepository.findByMatchAdmin(user);
    }

    @Override
    public List<Match> getAllMatchesByTeam(Long teamId) {
        return matchRepository.findByHomeTeamOrAwayTeam(new Team(teamId), new Team(teamId));
    }

    @Transactional
    @Override
    public void createMatch(User user, Match match, int setNumber) throws CheckFailedException {
        Optional<Team> homeTeamOptional = teamRepository.findById(match.getHomeTeam().getId());
        Optional<Team> awayTeamOptional = teamRepository.findById(match.getAwayTeam().getId());
        if(homeTeamOptional.isEmpty() || awayTeamOptional.isEmpty()){
            throw new CheckFailedException("Uno dei team non esiste");
        }
        for(int i = 0; i < setNumber; i++){
            SetMatch setMatch = new SetMatch(match, i+1);
            match.getSets().add(setMatch);
        }
        matchRepository.save(match);
    }


    //UTILIZZATA PER CREARE UN MATCH DI UN TORNEO (PRIMA FASE)
    public Match createMatchAndSets(int matchNumber, MatchType matchType, int groupStage,
                                    Tournament tournament, Team homeTeam, Team awayTeam,
                                    int fieldNumber, int setsnumber, User matchAdmin){
        Match match = new Match(matchNumber, matchType, groupStage, tournament, homeTeam, awayTeam, fieldNumber,matchAdmin);
        matchRepository.save(match);
        List<SetMatch> matchSets = new ArrayList<>();
        for(int i = 0; i < setsnumber; i++){
            matchSets.add(new SetMatch(match, i+1));
        }
        setMatchRepository.saveAll(matchSets);
        return match;
    }

    //UTILIZZATA PER CREARE UN MATCH DI UN TORNEO (SECONDA FASE)
    public Match createMatchAndSets(int matchNumber, MatchType matchType, Tournament tournament,
                                    int fieldNumber, int setsnumber, User matchAdmin){
        Match match = new Match(matchNumber, matchType, tournament, fieldNumber, matchAdmin);
        matchRepository.save(match);
        List<SetMatch> matchSets = new ArrayList<>();
        for(int i = 0; i < setsnumber; i++){
            matchSets.add(new SetMatch(match, i+1));
        }
        setMatchRepository.saveAll(matchSets);
        return match;
    }

    //----------------------------METODI CREATE ----------------------
    public void createOrUpdateMatch(Match match) {
        matchRepository.save(match);
    }
}
