package Project3.dao;

import Project3.models.FinishedMatch;
import Project3.models.OngoingMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MatchDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MatchDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setWinner(OngoingMatch ongoingMatch, int id) {
        jdbcTemplate.update("UPDATE matches SET winner_id=? where id=?", ongoingMatch.getWinner().getId(), id);
    }

    public void createFinishedMatch(OngoingMatch ongoingMatch) {
        jdbcTemplate.update("INSERT INTO finished_matches(player1_id, player2_id, winner_id," +
                "player1_score, player2_score) VALUES (?, ?, ?, ?, ?)", ongoingMatch.getFirstPlayer().getId(),
                ongoingMatch.getSecondPlayer().getId(), ongoingMatch.getWinner().getId(), ongoingMatch.getFirstPlayerScore(),
                ongoingMatch.getSecondPlayerScore());
    }

    public void deleteMatch(int id) {
        jdbcTemplate.update("DELETE FROM matches WHERE id = ?", id);
    }

}
