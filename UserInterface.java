import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.net.URL;
import java.util.Arrays;

/**
 * This class implements a simple graphical user interface with a
 * text entry area, a text output area and an optional image.
 * 
 * @author Michael Kolling and LIN Xingtong
 * @version 1.0 (Jan 2003) DB edited (2023) LIN Xingtong edited (2024)
 */
public class UserInterface implements ActionListener {
    /** un moteur de jeu qui gere la fonctionnalite du jeu */
    private GameEngine aEngine;
    /** la fenetre principale de l'interface */
    private JFrame aMyFrame;
    /** la zone de texte pour l'entree de l'utilisateur */
    private JTextField aEntryField;
    /** la zone de texte pour la sortie du programme */
    private JTextArea aLog;
    /** permet d'afficher une image */
    private JLabel aImage;
    /** un tableau de boutons pour les directions */
    private JButton[] aBtsGo;
    /** un bouton pour revenir en arriere */
    private JButton aBtBack;
    /** un tableau contenant les directions possibles */
    private String[] vDirections = { "North", "South", "West", "East", "Up", "Down" };

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is needed.
     * 
     * @param pGameEngine The GameEngine object implementing the game logic.
     */
    public UserInterface(final GameEngine pGameEngine) {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     * 
     * @param pText The text to print.
     */
    public void print(final String pText) {
        this.aLog.append(pText);
        this.aLog.setCaretPosition(this.aLog.getDocument().getLength());
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     * 
     * @param pText The text to print.
     */
    public void println(final String pText) {
        this.print(pText + "\n");
    } // println(.)

    /**
     * Show an image file in the interface.
     * 
     * @param pImageName The name of the image file to display.
     */
    public void showImage(final String pImageName) {
        String vImagePath = "Images/" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        if (vImageURL == null)
            System.out.println("Image not found : " + vImagePath);
        else {
            ImageIcon vIcon = new ImageIcon(vImageURL);
            this.aImage.setIcon(vIcon);
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the entry field.
     * 
     * @param pOnOff true to enable, false to disable.
     */
    public void enable(final boolean pOnOff) {
        this.aEntryField.setEditable(pOnOff); // enable/disable
        if (pOnOff) { // enable
            this.aEntryField.getCaret().setBlinkRate(500); // cursor blink
            this.aEntryField.addActionListener(this); // reacts to entry
            for (int i = 0; i < vDirections.length; i++) {
                this.aBtsGo[i].addActionListener(this);
            }
            this.aBtBack.addActionListener(this);
        } else { // disable
            this.aEntryField.getCaret().setBlinkRate(0); // cursor won't blink
            this.aEntryField.removeActionListener(this); // won't react to entry
            for (int i = 0; i < vDirections.length; i++) {
                this.aBtsGo[i].removeActionListener(this);
            }
            this.aBtBack.removeActionListener(this);
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI() {
        this.aMyFrame = new JFrame("Le Secret du Chat"); // change the title !
        this.aEntryField = new JTextField(34);

        this.aLog = new JTextArea();
        this.aLog.setEditable(false);
        JScrollPane vListScroller = new JScrollPane(this.aLog);
        vListScroller.setPreferredSize(new Dimension(200, 200));
        vListScroller.setMinimumSize(new Dimension(100, 100));

        this.aImage = new JLabel();

        JPanel vEastPanel = new JPanel();// ==> place buttons add
        vEastPanel.setLayout(new BoxLayout(vEastPanel, BoxLayout.Y_AXIS));// BoxLayout vertical

        this.aBtsGo = new JButton[vDirections.length];
        for (int i = 0; i < vDirections.length; i++) {
            this.aBtsGo[i] = new JButton(vDirections[i]);
            vEastPanel.add(this.aBtsGo[i]);
            this.aBtsGo[i].addActionListener(this);
        }
        Dimension vd = this.aBtsGo[1].getMaximumSize();
        // car south est le plus long
        for (int i = 0; i < vDirections.length; i++) {
            if (i != 1)
                this.aBtsGo[i].setMaximumSize(new Dimension(vd));
        }

        this.aBtBack = new JButton("Back");
        vEastPanel.add(this.aBtBack);
        this.aBtBack.addActionListener(this);
        this.aBtBack.setMaximumSize(new Dimension(vd));

        JPanel vPanel = new JPanel();
        vPanel.setLayout(new BorderLayout()); // ==> only five places
        vPanel.add(this.aImage, BorderLayout.NORTH);
        vPanel.add(vListScroller, BorderLayout.CENTER);
        vPanel.add(this.aEntryField, BorderLayout.SOUTH);
        vPanel.add(vEastPanel, BorderLayout.EAST);

        this.aMyFrame.getContentPane().add(vPanel, BorderLayout.CENTER);

        // add some event listeners to some components
        this.aEntryField.addActionListener(this);

        // to end program when window is closed
        this.aMyFrame.addWindowListener(
                new WindowAdapter() { // anonymous class
                    @Override
                    public void windowClosing(final WindowEvent pE) {
                        System.exit(0);
                    }
                });

        this.aMyFrame.pack();
        this.aMyFrame.setVisible(true);
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        String vBtnName = pE.getActionCommand();
        if (Arrays.asList(vDirections).contains(vBtnName)) {
            this.aEngine.interpretCommand("go " + vBtnName.toLowerCase());
        } else if (vBtnName.equals("Back")) {
            this.aEngine.interpretCommand("back");
        } else
            this.processCommand(); // never suppress this line
    } // actionPerformed(.)

    /**
     * A command has been entered in the entry field.
     * Read the command and do whatever is necessary to process it.
     */
    private void processCommand() {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText("");

        this.aEngine.interpretCommand(vInput);
    } // processCommand()
} // UserInterface
