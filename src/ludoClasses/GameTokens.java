package ludoClasses;

import GUI.LudoGUI;

public interface GameTokens {
    public void move(int spaces, Player player, LudoBoard board, Player[] playersArr, LudoGUI gui);
}
