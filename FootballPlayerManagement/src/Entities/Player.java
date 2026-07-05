package Entities;

import BusinessObject.SalaryManagement;
import java.util.List;

public class Player {

    public static final String[] VALID_POSITIONS =
            {"Goalkeeper", "Defender", "Midfielder", "Forward"};
    public static final String[] VALID_PLAYER_TYPES =
            {"Regular Player", "Star Player"};
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_INACTIVE = "Inactive";

    private String id;             
    private String fullName;
    private int age;
    private String nationality;
    private String position;
    private int shirtNumber;
    private double baseSalary;
    private String playerType;     
    private String status;

  
    private int goalsScored;
    private int assists;
    private int yellowCards;
    private int redCards;

    public Player() {
        
    }

    public Player(String id, String fullName, int age, String nationality,
                  String position, int shirtNumber, double baseSalary,
                  String playerType) throws Exception {
        setId(id);
        setFullName(fullName);
        setAge(age);
        setNationality(nationality);
        setPosition(position);
        setShirtNumber(shirtNumber);
        setBaseSalary(baseSalary);
        setPlayerType(playerType);
        this.status = STATUS_ACTIVE;
        this.goalsScored = 0;
        this.assists = 0;
        this.yellowCards = 0;
        this.redCards = 0;
    }

    // ---------- Getters ----------
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getNationality() { return nationality; }
    public String getPosition() { return position; }
    public int getShirtNumber() { return shirtNumber; }
    public double getBaseSalary() { return baseSalary; }
    public String getPlayerType() { return playerType; }
    public String getStatus() { return status; }
    public boolean isActive() { return STATUS_ACTIVE.equals(status); }

    public int getGoalsScored() { return goalsScored; }
    public int getAssists() { return assists; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }

    // ---------- Setters----------

    public void setId(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Error: Player ID must not be empty.");
        }
        this.id = id.trim();
    }

    public void setFullName(String fullName) throws Exception {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new Exception("Error: Full name must not be empty.");
        }
        this.fullName = fullName.trim();
    }

    public void setAge(int age) throws Exception {
        if (age < 16 || age > 45) {
            throw new Exception("Error: Age must be between 16 and 45.");
        }
        this.age = age;
    }

    public void setNationality(String nationality) throws Exception {
        if (nationality == null || nationality.trim().isEmpty()) {
            throw new Exception("Error: Nationality must not be empty.");
        }
        this.nationality = nationality.trim();
    }

    public void setPosition(String position) throws Exception {
        for (String p : VALID_POSITIONS) {
            if (p.equalsIgnoreCase(position == null ? "" : position.trim())) {
                this.position = p;
                return;
            }
        }
        throw new Exception(
            "Error: Position must be one of: Goalkeeper, Defender, Midfielder, Forward.");
    }

    public void setShirtNumber(int shirtNumber) throws Exception {
        if (shirtNumber < 1 || shirtNumber > 99) {
            throw new Exception("Error: Shirt number must be between 1 and 99.");
        }
        this.shirtNumber = shirtNumber;
    }

    public void setBaseSalary(double baseSalary) throws Exception {
        if (baseSalary <= 0) {
            throw new Exception("Error: Base salary must be greater than zero.");
        }
        this.baseSalary = baseSalary;
    }

    public void setPlayerType(String playerType) throws Exception {
        for (String t : VALID_PLAYER_TYPES) {
            if (t.equalsIgnoreCase(playerType == null ? "" : playerType.trim())) {
                this.playerType = t;
                return;
            }
        }
        throw new Exception("Error: Player type must be 'Regular Player' or 'Star Player'.");
    }

    public void setStatus(String status) throws Exception {
        if (STATUS_ACTIVE.equalsIgnoreCase(status)) {
            this.status = STATUS_ACTIVE;
        } else if (STATUS_INACTIVE.equalsIgnoreCase(status)) {
            this.status = STATUS_INACTIVE;
        } else {
            throw new Exception("Error: Status must be Active or Inactive.");
        }
    }

    public void setGoalsScored(int goalsScored) throws Exception {
        if (goalsScored < 0) throw new Exception("Error: Goals scored must not be negative.");
        this.goalsScored = goalsScored;
    }

    public void setAssists(int assists) throws Exception {
        if (assists < 0) throw new Exception("Error: Assists must not be negative.");
        this.assists = assists;
    }

    public void setYellowCards(int yellowCards) throws Exception {
        if (yellowCards < 0) throw new Exception("Error: Yellow cards must not be negative.");
        this.yellowCards = yellowCards;
    }

    public void setRedCards(int redCards) throws Exception {
        if (redCards < 0) throw new Exception("Error: Red cards must not be negative.");
        this.redCards = redCards;
    }

    // ---------- File serialization  ----------
    // id,fullName,age,nationality,position,shirtNumber,baseSalary,playerType,status,goalsScored,assists,yellowCards,redCards

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%s,%d,%.2f,%s,%s,%d,%d,%d,%d",
                id, fullName, age, nationality, position, shirtNumber, baseSalary,
                playerType, status, goalsScored, assists, yellowCards, redCards);
    }

    public static Player fromCsvLine(String line) throws Exception {
        String[] f = line.split(",", -1);
        if (f.length < 9) {
            throw new Exception("Error: Corrupted player data line: " + line);
        }
        Player p = new Player(f[0].trim(), f[1].trim(), Integer.parseInt(f[2].trim()),
                f[3].trim(), f[4].trim(), Integer.parseInt(f[5].trim()),
                Double.parseDouble(f[6].trim()), f[7].trim());
        p.setStatus(f[8].trim());
        if (f.length >= 13) {
            p.setGoalsScored(Integer.parseInt(f[9].trim()));
            p.setAssists(Integer.parseInt(f[10].trim()));
            p.setYellowCards(Integer.parseInt(f[11].trim()));
            p.setRedCards(Integer.parseInt(f[12].trim()));
        }
        return p;
    }

    // ---------- Display ----------

    public void displayInfo() {
        System.out.printf("%-8s | %-20s | %-12s | #%-3d | %-15s | %-9s%n",
                id, fullName, position, shirtNumber, playerType, status);
    }

    public String toDetailString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------- PLAYER DETAIL ----------\n");
        sb.append("Player ID    : ").append(id).append("\n");
        sb.append("Full Name    : ").append(fullName).append("\n");
        sb.append("Age          : ").append(age).append("\n");
        sb.append("Nationality  : ").append(nationality).append("\n");
        sb.append("Position     : ").append(position).append("\n");
        sb.append("Shirt Number : ").append(shirtNumber).append("\n");
        sb.append("Base Salary  : ").append(String.format("%,.0f VND", baseSalary)).append("\n");
        sb.append("Player Type  : ").append(playerType).append("\n");
        sb.append("Status       : ").append(status).append("\n");
        sb.append("------------------------------------");
        return sb.toString();
    }
}