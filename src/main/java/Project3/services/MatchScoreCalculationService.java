package Project3.services;

import Project3.dao.MatchDAO;
import Project3.models.OngoingMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchScoreCalculationService {

    private final OngoingMatchesService ongoingMatchesService;

    private boolean isMatchExists = true;

    private final MatchDAO matchDAO;
    private final SavingFinishedMatchesService savingFinishedMatchesService;

    @Autowired
    public MatchScoreCalculationService(OngoingMatchesService ongoingMatchesService, MatchDAO matchDAO, SavingFinishedMatchesService savingFinishedMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.matchDAO = matchDAO;
        this.savingFinishedMatchesService = savingFinishedMatchesService;
    }


    @Transactional
    public void addPointToPlayer(Integer firstPlayerId, Integer secondPlayerId,
                                 OngoingMatch ongoingMatch, Integer matchId) {

        if (!isThereWinner(ongoingMatch)) {
            if (firstPlayerId == null) {
                ongoingMatch.setSecondPlayerScore(ongoingMatch.getSecondPlayerScore() + 1);
                ongoingMatchesService.update(matchId, ongoingMatch, "second");
            } else if (secondPlayerId == null) {
                ongoingMatch.setFirstPlayerScore(ongoingMatch.getFirstPlayerScore() + 1);
                ongoingMatchesService.update(matchId, ongoingMatch, "first");
            }
        } else {
            detectWinner(matchId, ongoingMatch);
            System.out.println(111111);
        }
    }

    public boolean isThereWinner(OngoingMatch ongoingMatch) {
        int firstScore = ongoingMatch.getFirstPlayerScore();
        int secondScore = ongoingMatch.getSecondPlayerScore();

        if (firstScore >= 6 || secondScore >= 6) {
            return Math.abs(firstScore - secondScore) >= 2;
        }
        return false;
    }

    @Transactional
    public void detectWinner(int id, OngoingMatch ongoingMatch) {
        if (isThereWinner(ongoingMatch)) {
            int firstScore = ongoingMatch.getFirstPlayerScore();
            int secondScore = ongoingMatch.getSecondPlayerScore();
            if (firstScore > secondScore) {
                ongoingMatch.setWinner(ongoingMatch.getFirstPlayer());
            } else {
                ongoingMatch.setWinner(ongoingMatch.getSecondPlayer());
            }
            matchDAO.setWinner(ongoingMatch, id);
            finishMatch(id, ongoingMatch);
        }
    }

    public boolean isMatchExists() {
        return isMatchExists;
    }

    public void setMatchExists(boolean MatchExists) {
        isMatchExists = MatchExists;
    }

    @Transactional
    public void finishMatch(int id, OngoingMatch ongoingMatch) {
        savingFinishedMatchesService.createFinishedMatch(ongoingMatch);
        matchDAO.deleteMatch(id);
        setMatchExists(false);
    }
}
