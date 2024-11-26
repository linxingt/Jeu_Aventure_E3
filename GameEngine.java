import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Decrivez votre classe GameEngine ici.
 *
 * @author (votre nom)
 * @version (un numero de version ou une date)
 */
public class GameEngine
{
    /** le joueur */
    private Player aPlayer;
    /** le parser qui analyse les commandes */
    private Parser aParser;
    /** l'interface utilisateur */
    private UserInterface aGui;

    /**
     * Constructeur d'objets de classe GameEngine
     */
    public GameEngine()
    {
        String vPrenom = javax.swing.JOptionPane.showInputDialog( "What's your name?" );
        this.aPlayer = new Player( vPrenom );
        this.createRooms();
        this.aParser = new Parser();
    }

    /**
     * Setter pour l'interface utilisateur.
     * @param pUserInterface interface utilisateur
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Initialiser les salles, les sorties et la position actuelle du joueur.
     */
    private void createRooms() {
        Room vOutside = new Room(
                "standing outside the secret laboratory, \nthe entrance looming ahead with an air of mystery and danger.","entrance.jpg");
        Room vStorage = new Room(
                "in a cluttered storage room, \nfilled with various discarded objects and tools.","storage.jpg");
        Room vClean = new Room(
                "in the clean room, \na sterile area where disinfection and changing take place. \nThe air smells faintly of chemicals, \nand a row of lockers lines the wall.","clean.jpg");
        Room vMeeting = new Room(
                "in the meeting room, \nwhere scientists plan their experiments. \nA large table and chairs dominate the space, \nand diagrams cover the walls.","meeting.jpg");
        Room vPrison = new Room(
                "in the prison room, \nwhere human subjects are held in confinement. \nThe air feels heavy with tension.","prison.jpg");
        Room vAnimal = new Room(
                "in the animal room, a isolated space \nwhere animals, transformed by experiments, are kept in cages. \nThe room is eerily quiet.","animal.jpg");
        Room vArchive = new Room(
                " in the archive room, a space \nfilled with shelves of files and records. \nEach document holds secrets about the experiments conducted here.","archive.jpg");
        Room vExperimentation = new Room(
                "in the experimentation room, \nwhere the darkest of the laboratory's procedures are carried out. \nSurgical beds and strange equipment fill the space.","exprience.png");
        Room vAleatoire = vAnimal; // random room, pas encore fait

        vOutside.setExits("south", vStorage);
        vOutside.addItem("an old pair of glasses without lenses. Wearing it can make you see things differently", 40,"glasses",true,true);  // 40 is the weight of the item

        vStorage.setExits("down", vClean);
        vStorage.setExits("north", vOutside);
        vStorage.addItem("a key for the cabinet in the archives room", 60,"key",true,true);
        vStorage.addItem("an invisible cloth can help you hide", 50,"cloth",true,false);
        vStorage.addItem("a torn piece of paper with the name Alice", 2,"paper",false,true);
        vStorage.addItem("a plan of the entire laboratory but broken", 10,"plan",false,true);

        vClean.setExits("west", vMeeting);
        vClean.setExits("south", vAleatoire);
        vClean.setExits("up", vStorage);
        vClean.addItem("an access card for the experiment room",50,"card",true,true);
        vClean.addItem("a magic cake that can increase your maximum ability to take things by 20% after eating it", 70,"cake",true,false);
        
        vMeeting.setExits("east", vClean);
        vMeeting.setExits("west", vArchive);
        vMeeting.setExits("south", vPrison);
        vMeeting.setExits("north", vAnimal);
        vMeeting.addItem("a whiteboard displays a discussion about which animal to place 2566's soul into, along with a prominent slogan",500,"whiteboard",false,true);

        vPrison.setExits("north", vMeeting);

        vAnimal.setExits("west", vExperimentation);
        vAnimal.setExits("south", vMeeting);
        vAnimal.addItem("an empty cage with a small plastic tag labeled 2566", 300,"cage",false,true);

        vArchive.setExits("east", vMeeting);
        vArchive.setExits("north", vExperimentation);
        vArchive.addItem("a cabinet with a lock", 900,"cabinet",false,true);
        //attends key open cabinet
        //"You use the key to open the cabinet and find a lot of \nexperimenter information, sorted by name. You easily find \nAlice's information and find that her name and photo are \nthe same as your missing sister. You also find that the latest information \nindicates that she will be sent to the laboratory within a week. \nWhen flipping through the information, you find that the \nearliest information about this laboratory appears in 2050. \nYou suspect that this is the year the laboratory was founded. \nA piece of paper with August 9, 2050 written on it falls \nfrom the book, proving your guess."

        vExperimentation.setExits("east", vAnimal);
        vExperimentation.setExits("south", vArchive);
        vExperimentation.addItem("a bed with a small sign saying 2566", 300,"bed",false,true);

        aPlayer.setCurrentRoom(vOutside);
    }

    /**
     * Deplacer le joueur dans une direction donnee.
     * @param pCmd commande de deplacement
     */
    private void goRoom(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Go where ?");
            return;
        }
        String vDirection = pCmd.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit(vDirection);
        if(vNextRoom==null){
            this.aGui.println("There is no door !");
        } else{
            this.aPlayer.addPreviousRoom(this.aPlayer.getCurrentRoom());//Pushes a new element on top of this Stack.
            this.aPlayer.setCurrentRoom(vNextRoom);
        }
        this.printLocationInfo();
    }

    /**
     * Deplacer le joueur dans la salle precedante.
     * @param pCmd commande de deplacement
     */
    private void back(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("You cannot specify a direction when going back.");
            return;
        }else if(this.aPlayer.PreviousRoomIsEmpty()){
            this.aGui.println("There's nowhere you can go back to.");
            return;
        }
        Room vNextRoom = this.aPlayer.getPreviousRoom();//Returns the head element without modifying the Stack.
        this.aPlayer.setCurrentRoom(vNextRoom);
        this.aPlayer.removePreviousRoom();//Removes the head element from this Stack.
        this.printLocationInfo();
    }

    /**
     * Afficher les informations localisation de salle actuel.
     */
    private void printLocationInfo() {
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription(this.aPlayer));
        if ( this.aPlayer.getCurrentRoom().getImgName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImgName() );
    }

    /**
     * Afficher les informations de bienvenue et les informations de la position actuelle.
     */
    private void printWelcome() {
        this.aGui.println("\nWelcome to the Secret of the Cat!\n"
                + "Secret of the Cat is a new, incredibly boring adventure game.\n"
                + "Type 'help' if you need help.\n");
        this.printLocationInfo();
    }

    /**
     * Afficher les informations d'aide.
     */
    private void printHelp() {
        this.aGui.println("You are lost. You are alone.\n"
                + "You wander around at the laboratory.\n\n"
                + "Your command words are:"+this.aParser.getCommands()+"\n");
    }

    /**
     * traiter la commande quit.
     * @param pCmd commande a traiter
     */
    private void quit(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("Quit what ?");
        }else {
            this.aGui.println("Thank you for playing.  Good bye.");
            this.aGui.enable(false);
        }
    }

    /**
     * Traiter la commande du joueur.
     * @param pCmd commande a traiter
     */
    public void interpretCommand(final String pCmd) {

        this.aGui.println( "> " + pCmd );
        Command vCmd = this.aParser.getCommand( pCmd );

        if (vCmd.isUnknown()) {
            this.aGui.println("Erreur du programmeur : commande non reconnue !");
            return;
        } else if (vCmd.getCommandWord().equals("go")) {
            this.goRoom(vCmd);
        } else if (vCmd.getCommandWord().equals("quit")) {
            this.quit(vCmd);
        } else if (vCmd.getCommandWord().equals("help")) {
            this.printHelp();
        } else if (vCmd.getCommandWord().equals("look")) {
            this.look(vCmd);
        } else if (vCmd.getCommandWord().equals("eat")) {
            this.eat(vCmd);
        } else if (vCmd.getCommandWord().equals("back")) {
            this.back(vCmd);
        } else if (vCmd.getCommandWord().equals("test")) {
            this.test(vCmd);
        } else if (vCmd.getCommandWord().equals("take")) {
            this.take(vCmd);
        } else if (vCmd.getCommandWord().equals("drop")) {
            this.drop(vCmd);
        } else {
            this.aGui.println("I don't know what you mean...");
        }
    }

    /**
     * Afficher les informations de la salle actuelle et les objets dans joueur.
     * @param pCmd commande a traiter apres le mot look
     */
    private void look(final Command pCmd){
        if (pCmd.hasSecondWord()) {
            String vItemName=pCmd.getSecondWord();
            Item vItem = this.aPlayer.getCurrentRoom().getOneItem(vItemName);
            if(vItem==null){
                this.aGui.println("The item you are looking for is not in this room.");
                return;
            }
            this.aGui.println("There is "+vItem.getItemDescription()+".");
            return;
        }
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription(this.aPlayer)+"\n"+this.aPlayer.getItemsNames()+"\n"+this.aPlayer.getWeightInfo());
    }

    /**
     * Manger un objet.
     * @param pCmd commande a traiter apres le mot eat
     */
    private void eat(final Command pCmd){
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Eat what?");
            return;
        }
        if(pCmd.getSecondWord()=="cake"){
            this.aPlayer.setWeightAllowed(120);
            this.aGui.println("You have eaten the magic cake and now you have "+this.aPlayer.getWeightAllowed()+"% capacity to carry things.");
        }
        else
            this.aGui.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * Tester les commandes dans un fichier.
     * @param pCmd commande a traiter qui a le nom du fichier comme second mot
     */
    private void test(final Command pCmd){
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Test what?");
            return;
        }
        Scanner vSc=null;
        try { // pour "essayer" les instructions suivantes :
            vSc = new Scanner( new File( pCmd.getSecondWord()+".txt" ) ); // ouverture du fichier s'il existe
            while ( vSc.hasNextLine() ) { // tant qu'il y a encore une ligne a lire dans le fichier
                interpretCommand(vSc.nextLine()); // lecture de la ligne dans le fichier
                /// traitement de la ligne lue
            } // while
        } // try
        catch ( final FileNotFoundException pFNFE ) { // si le fichier n'existe pas
            /// traitement en cas d'exception
        } // catch
        finally
        {
            if(vSc !=null)
            {
                vSc.close();
                return;
            }
        }
    }

    /**
     * Prendre un objet.
     * @param pCmd commande a traiter apres le mot take
     */
    private void take(final Command pCmd){
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Take what?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getOneItem(vItemName);
        if(vItem==null){
            this.aGui.println("There is no such item in this room.");
            return;
        }
        if(vItem.getCanBePickedUp()){
                this.aPlayer.pickUpItem(vItem, aGui);
                this.aPlayer.getCurrentRoom().removeItem(vItemName);
        }else{
            this.aGui.println(vItemName + " cannot be picked up.");
        }
    }

    /**
     * Lacher un objet.
     * @param pCmd commande a traiter apres le mot drop
     */
    private void drop(final Command pCmd){
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Drop what?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getOneItem(vItemName);
        if(vItem==null){
            this.aGui.println("You don't have this item in your inventory.");
            return;
        }
        this.aPlayer.dropItem(vItemName, aGui);
        this.aPlayer.getCurrentRoom().addItem(vItem);
    }
}
