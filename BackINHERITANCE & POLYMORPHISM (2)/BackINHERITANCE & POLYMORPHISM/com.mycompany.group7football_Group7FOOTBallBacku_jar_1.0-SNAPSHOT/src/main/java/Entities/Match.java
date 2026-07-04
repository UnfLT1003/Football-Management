

package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Match {
    private String matchId;
    private LocalDate date;
    private String opponent;
    private String matchType;

    public Match(String matchId, LocalDate date, String opponent, String matchType) throws Exception {
        setMatchId(matchId);
        setDate(date);
        setOpponent(opponent);
        setMatchType(matchType);
    }

    public String getMatchId() { return matchId; }
    public void setMatchId(String id) throws Exception {
        if (!id.matches("M\\d{3}")) throw new Exception("Match ID must be Mxxx");
        this.matchId = id.toUpperCase();
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) throws Exception {
        if (date == null) throw new Exception("Date required");
        this.date = date;
    }

    public String getOpponent() { return opponent; }
    public void setOpponent(String opp) throws Exception {
        if (opp == null || opp.trim().isEmpty()) throw new Exception("Opponent required");
        this.opponent = opp.trim();
    }

    public String getMatchType() { return matchType; }
    public void setMatchType(String type) throws Exception {
        if (type == null || type.trim().isEmpty()) throw new Exception("Match type required");
        this.matchType = type.trim();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s,%s,%s,%s", matchId, date.format(fmt), opponent, matchType);
    }

    public void displayInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("| %-8s | %-12s | %-25s | %-15s |%n", matchId, date.format(fmt), opponent, matchType);
    }
}