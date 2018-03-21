package view;

import javax.swing.*;

public interface GameView {

   JPanel getPanel();
   void setAllButtonListeners();
   void setMessage(String m);

}
