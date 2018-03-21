package view;

import javax.swing.*;

public interface GameView {
   /*Returns main panel containing board and other game panels*
    */
   JPanel getPanel();
   /*Returns board panel
    */
   JPanel getBoard();

   /*Allows the controller to add all actions listeners during runtime
    */
   void setAllButtonListeners();

   /*Allows controller to update JLabel with news for the user
    */
   void setMessage(String m);

}
