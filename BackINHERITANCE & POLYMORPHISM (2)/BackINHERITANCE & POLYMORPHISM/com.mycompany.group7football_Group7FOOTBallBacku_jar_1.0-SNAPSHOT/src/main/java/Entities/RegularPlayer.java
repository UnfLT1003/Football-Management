package Entities;

import java.time.LocalDate;

public class RegularPlayer extends Player {

    public RegularPlayer(String id, String name, String position, int jerseyNumber,
                         LocalDate dateOfBirth, String nationality, double marketValue,
                         int goalsScored, double baseSalary, String status,
                         int assists, int yellowCards, int redCards) throws Exception {
        super(id, name, position, jerseyNumber, dateOfBirth, nationality, marketValue,
              goalsScored, baseSalary, status, assists, yellowCards, redCards);
    }

    public RegularPlayer(String id, String name, String position, int jerseyNumber,
                         LocalDate dateOfBirth, String nationality, double baseSalary,
                         String status) throws Exception {
        super(id, name, position, jerseyNumber, dateOfBirth, nationality, baseSalary, status);
    }

    @Override
    public String getPlayerType() {
        return "RegularPlayer";
    }

    @Override
    public double calculateBonus(int monthlyPerformancePoints) {
        return 0;
    }

    @Override
    public double calculateMonthlySalary(int monthlyPerformancePoints) {
        return getBaseSalary();
    }
}