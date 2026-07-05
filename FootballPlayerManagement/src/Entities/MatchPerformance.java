
package Entities;

public class MatchPerformance {
    private String matchId;
    private String playerId;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private int minutesPlayed;

    public MatchPerformance(String matchId, String playerId, int goals, int assists,
                            int yellowCards, int redCards, int minutesPlayed) throws Exception {
        setMatchId(matchId);
        setPlayerId(playerId);
        setGoals(goals);
        setAssists(assists);
        setYellowCards(yellowCards);
        setRedCards(redCards);
        setMinutesPlayed(minutesPlayed);
    }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public int getGoals() { return goals; }
    public void setGoals(int goals) throws Exception {
        if (goals < 0) throw new Exception("Goals >= 0");
        this.goals = goals;
    }

    public int getAssists() { return assists; }
    public void setAssists(int assists) throws Exception {
        if (assists < 0) throw new Exception("Assists >= 0");
        this.assists = assists;
    }

    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yc) throws Exception {
        if (yc < 0) throw new Exception("Yellow cards >= 0");
        this.yellowCards = yc;
    }

    public int getRedCards() { return redCards; }
    public void setRedCards(int rc) throws Exception {
        if (rc < 0) throw new Exception("Red cards >= 0");
        this.redCards = rc;
    }

    public int getMinutesPlayed() { return minutesPlayed; }
    public void setMinutesPlayed(int minutes) throws Exception {
        if (minutes < 0 || minutes > 120) throw new Exception("Minutes 0-120");
        this.minutesPlayed = minutes;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%d,%d,%d",
                matchId, playerId, goals, assists, yellowCards, redCards, minutesPlayed);
    }
}