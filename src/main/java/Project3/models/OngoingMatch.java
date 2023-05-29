package Project3.models;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class OngoingMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player firstPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player secondPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Player winner;

    @Column(name = "player1_score")
    private int firstPlayerScore;

    @Column(name = "player2_score")
    private int secondPlayerScore;

    public OngoingMatch() {
    }

    public OngoingMatch(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", winner=" + winner +
                '}';
    }
}