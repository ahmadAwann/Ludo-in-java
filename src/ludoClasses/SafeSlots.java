package ludoClasses;

import java.util.ArrayList;

public class SafeSlots {
    ArrayList<Character> safeSlotList = new ArrayList<>();
    int row;
    int col;
    // parameterized constructor... for manual definition of safe slots....
    public SafeSlots(int row,int col){
        this.row = row;
        this.col = col;
        // setting the default # character at index 1 for every list...
        safeSlotList.add('#');
    }
}
