
    

package BusinessObject;

import DataObjects.TeamDAO;
import Entities.Team;
import Utilities.DataInput;
import Utilities.Menu;

public class TeamManagement {
    private TeamDAO teamDAO;
    public TeamManagement(TeamDAO teamDAO) { this.teamDAO = teamDAO; }
    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("TEAM MANAGEMENT|1.Add|2.Update|3.Delete|4.List|5.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1:
                    Team t = new Team(DataInput.getStringNonEmpty("ID Txxx: "),
                            DataInput.getStringNonEmpty("Name: "),
                            DataInput.getStringNonEmpty("Coach: "),
                            DataInput.getStringNonEmpty("Stadium: "));
                    teamDAO.addTeam(t);
                    teamDAO.saveToFile();
                    break;
                case 5: stop = true; break;
                default: System.out.println("Coming soon");
            }
        }
    }
}