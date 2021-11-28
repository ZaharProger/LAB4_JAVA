package Main;

import UI.Menu;

public class Main {
    public static void main(String[] args){
        byte result;
        do result = Menu.run(); while (result == 0);
    }
}
