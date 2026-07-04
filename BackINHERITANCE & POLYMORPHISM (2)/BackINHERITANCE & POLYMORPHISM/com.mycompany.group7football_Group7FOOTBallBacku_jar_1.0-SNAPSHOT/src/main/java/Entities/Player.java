
package Entities;

import Utilities.DataValidation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public abstract class Player {
    private String id;
    private String name;
    private String position;
    private int jerseyNumber;
    private LocalDate dateOfBirth;
    private String nationality;
    private double marketValue;
    private int goalsScored;
    private double baseSalary;
    private String status;
    private int assists;
    private int yellowCards;
    private int redCards;

    // Constructor đầy đủ
    public Player(String id, String name, String position, int jerseyNumber,
                  LocalDate dateOfBirth, String nationality, double marketValue,
                  int goalsScored, double baseSalary, String status,
                  int assists, int yellowCards, int redCards) throws Exception {
        setId(id);
        setName(name);
        setPosition(position);
        setJerseyNumber(jerseyNumber);
        setDateOfBirth(dateOfBirth);
        setNationality(nationality);
        setMarketValue(marketValue);
        setGoalsScored(goalsScored);
        setBaseSalary(baseSalary);
        setStatus(status);
        setAssists(assists);
        setYellowCards(yellowCards);
        setRedCards(redCards);
    }

    // Constructor rút gọn cho thêm mới
    public Player(String id, String name, String position, int jerseyNumber,
                  LocalDate dateOfBirth, String nationality, double baseSalary,
                  String status) throws Exception {
        this(id, name, position, jerseyNumber, dateOfBirth, nationality, 0.0, 0,
             baseSalary, status, 0, 0, 0);
    }

    // ========== ABSTRACT METHODS ==========
    public abstract String getPlayerType();
    public abstract double calculateBonus(int monthlyPerformancePoints);
    public abstract double calculateMonthlySalary(int monthlyPerformancePoints);

    // ========== GETTERS & SETTERS ==========
    public String getId() { return id; }
    public void setId(String id) throws Exception {
        if (!DataValidation.isValidPlayerId(id))
            throw new Exception("ID must be Pxxx (e.g., P001)");
        this.id = id.toUpperCase();
    }

    public String getName() { return name; }
    public void setName(String name) throws Exception {
        if (!DataValidation.checkStringWithFormat(name, "[A-Za-z\\s]{3,50}"))
            throw new Exception("Name 3-50 letters/spaces");
        this.name = toTitleCase(name);
    }

    public String getPosition() { return position; }
    public void setPosition(String position) throws Exception {
        if (!DataValidation.isValidPosition(position))
            throw new Exception("Position: Goalkeeper/Defender/Midfielder/Forward");
        this.position = toTitleCase(position);
    }

    public int getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(int jerseyNumber) throws Exception {
        if (!DataValidation.isValidJerseyNumber(jerseyNumber))
            throw new Exception("Jersey number 1-99");
        this.jerseyNumber = jerseyNumber;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dob) throws Exception {
        if (dob == null) throw new Exception("DOB required");
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 16 || age > 50) throw new Exception("Age 16-50 only");
        this.dateOfBirth = dob;
    }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) throws Exception {
        if (!DataValidation.checkStringWithFormat(nationality, "[A-Za-z\\s]{2,30}"))
            throw new Exception("Nationality 2-30 letters");
        this.nationality = toTitleCase(nationality);
    }

    public double getMarketValue() { return marketValue; }
    public void setMarketValue(double mv) throws Exception {
        if (mv < 0) throw new Exception("Market value cannot be negative");
        this.marketValue = mv;
    }

    public int getGoalsScored() { return goalsScored; }
    public void setGoalsScored(int goals) throws Exception {
        if (goals < 0) throw new Exception("Goals cannot be negative");
        this.goalsScored = goals;
    }

    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double salary) throws Exception {
        if (salary < 0) throw new Exception("Salary must be >=0");
        this.baseSalary = salary;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) throws Exception {
        if (!status.equalsIgnoreCase("Active") && !status.equalsIgnoreCase("Inactive"))
            throw new Exception("Status must be Active or Inactive");
        this.status = status.substring(0,1).toUpperCase() + status.substring(1).toLowerCase();
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

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private String toTitleCase(String str) {
        str = str.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0)
                sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s,%s,%s,%d,%s,%s,%.2f,%d,%.2f,%s,%s,%d,%d,%d",
                id, name, position, jerseyNumber, dateOfBirth.format(fmt),
                nationality, marketValue, goalsScored, baseSalary, getPlayerType(),
                status, assists, yellowCards, redCards);
    }

    public void displayDetail() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("═══════════════════════════════════════════════");
        System.out.printf("ID: %s | Name: %s%n", id, name);
        System.out.printf("Position: %s | Jersey: %d | Age: %d%n", position, jerseyNumber, getAge());
        System.out.printf("Nationality: %s | Market Value: %.2fM%n", nationality, marketValue);
        System.out.printf("Goals: %d | Assists: %d | YC: %d | RC: %d%n", goalsScored, assists, yellowCards, redCards);
        System.out.printf("Base Salary: %.2f | Type: %s | Status: %s%n", baseSalary, getPlayerType(), status);
        System.out.println("═══════════════════════════════════════════════");
    }
}