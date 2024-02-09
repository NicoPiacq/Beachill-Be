package it.beachill.model.services.implementation;

import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.repositories.abstractions.MatchRepository;
import it.beachill.model.repositories.abstractions.SetMatchRepository;
import it.beachill.model.services.abstraction.MatchsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JPAMatchsService implements MatchsService {
    private final MatchRepository matchRepository;
    private final SetMatchRepository setMatchRepository;

    @Autowired
    public JPAMatchsService(MatchRepository matchRepository, SetMatchRepository setMatchRepository) {
        this.matchRepository = matchRepository;
        this.setMatchRepository = setMatchRepository;
    }

    public List<Match> getAllMatchesByTournament(Long tournamentId){
        return matchRepository.findByTournamentId(tournamentId);
    }
    
    @Override
    public void setMatchSetResult(User user, Long setMatchId, SetMatchDto setMatchDto) throws CheckFailedException {
        if(!Objects.equals(setMatchId, setMatchDto.getMatchId())){
            throw new CheckFailedException("I dati del set non sono coerenti");
        }
        Optional<SetMatch> setMatchOptional =setMatchRepository.findById(setMatchId);
        if(setMatchOptional.isEmpty()){
            throw new CheckFailedException("Il set che vuoi modificare non esiste");
        }
        setMatchRepository.save(setMatchDto.fromDto());
    }
    
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
