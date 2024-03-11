package Unit_selection;

import Control.CLI;
import Control.DataBase;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        dataBase.init();
        CLI cli = new CLI();
        cli.init(dataBase);
    }
}
