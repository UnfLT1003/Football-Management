package Entities;

import java.time.LocalDate;

public class StarPlayer extends Player {

    public StarPlayer(String id, String name, String position, int jerseyNumber,
                      LocalDate dateOfBirth, String nationality, double marketValue,
                      int goalsScored, double baseSalary, String status,
                      int assists, int yellowCards, int redCards) throws Exception {
        super(id, name, position, jerseyNumber, dateOfBirth, nationality, marketValue,
              goalsScored, baseSalary, status, assists, yellowCards, redCards);
    }

    public StarPlayer(String id, String name, String position, int jerseyNumber,
                      LocalDate dateOfBirth, String nationality, double baseSalary,
                      String status) throws Exception {
        super(id, name, position, jerseyNumber, dateOfBirth, nationality, baseSalary, status);
    }

    @Override
    public String getPlayerType() {
        return "StarPlayer";
    }

    @Override
    public double calculateBonus(int monthlyPerformancePoints) {
        return monthlyPerformancePoints * 500_000.0;
    }

    @Override
    public double calculateMonthlySalary(int monthlyPerformancePoints) {
        return getBaseSalary() + calculateBonus(monthlyPerformancePoints);
    }
}