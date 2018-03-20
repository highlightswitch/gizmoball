package view;

import javax.swing.*;

public interface GameView {

   JPanel getPanel();
   JPanel getBoard();
   void setAllButtonListeners();
   void setMessage(String m);

}
