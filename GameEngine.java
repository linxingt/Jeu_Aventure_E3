
/**
 * D�crivez votre classe GameEngine ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class GameEngine
{
    // variables d'instance - remplacez l'exemple qui suit par le v�tre
    private Room aCurrentRoom;
    private Parser aParser;
    private UserInterface aGui;

    /**
     * Constructeur d'objets de classe GameEngine
     */
    public GameEngine()
    {
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
     * Initialise position actuel et des salles du Game avec la description et sorties par defaut.
     */
    private void createRooms() {
        Room vOutside = new Room(
                "standing outside the secret laboratory, \nthe entrance looming ahead with an air of mystery and danger.","entrance.jpg");
        Room vStorage = new Room(
                "in a cluttered storage room, \nfilled with various discarded objects and tools.","storage.png");
        Room vClean = new Room(
                "in the clean room, \na sterile area where disinfection and changing take place. \nThe air smells faintly of chemicals, \nand a row of lockers lines the wall.","clean.jpg");
        Room vMeeting = new Room(
                "in the meeting room, \nwhere scientists plan their experiments. \nA large table and chairs dominate the space, \nand diagrams cover the walls.","meeting.png");
        Room vPrison = new Room(
                "in the prison room, \nwhere human subjects are held in confinement. \nThe air feels heavy with tension.","prison.jpg");
        Room vAnimal = new Room(
                "in the animal room, a isolated space \nwhere animals, transformed by experiments, are kept in cages. \nThe room is eerily quiet.","animal.jpg");
        Room vArchive = new Room(
                " in the archive room, a space \nfilled with shelves of files and records. \nEach document holds secrets about the experiments conducted here.","archive.jpg");
        Room vExperimentation = new Room(
                "in the experimentation room, \nwhere the darkest of the laboratory's procedures are carried out. \nSurgical beds and strange equipment fill the space.","exprience.jpg");
        Room vAleatoire = vAnimal; // random room, pas encore fait

        vOutside.setExits("south", vStorage);

        vStorage.setExits("down", vClean);
        vStorage.setExits("north", vOutside);
        vStorage.setItem("1 key for the cabinet in the archives room", 30);
        //itmems à ajouter dans vStorage
        //tissu d'invisibilité
        //un morceau de papier déchiré avec le nom de votre sœur - Alice
        //un plan de tout le laboratoire.

        vClean.setExits("west", vMeeting);
        vClean.setExits("south", vAleatoire);
        vClean.setExits("up", vStorage);
        vClean.setItem("1 access card for the experiment room.",10);
        //itmems à ajouter dans vStorage
        //gâteau magique

        vMeeting.setExits("east", vClean);
        vMeeting.setExits("west", vArchive);
        vMeeting.setExits("south", vPrison);
        vMeeting.setExits("north", vAnimal);

        vPrison.setExits("north", vMeeting);

        vAnimal.setExits("west", vExperimentation);
        vAnimal.setExits("south", vMeeting);

        vArchive.setExits("east", vMeeting);
        vArchive.setExits("north", vExperimentation);

        vExperimentation.setExits("east", vAnimal);
        vExperimentation.setExits("south", vArchive);

        this.aCurrentRoom = vOutside;
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
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        if(vNextRoom==null){
            this.aGui.println("There is no door !");
            this.printLocationInfo();
            return;
        } else{
            this.aCurrentRoom = vNextRoom;
            this.printLocationInfo();
        }
    }

    /**
     * Afficher les informations localisation de salle actuel.
     */
    private void printLocationInfo() {
        this.aGui.println(this.aCurrentRoom.getLongDescription());
        if ( this.aCurrentRoom.getImgName() != null )
            this.aGui.showImage( this.aCurrentRoom.getImgName() );
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
            this.eat();
        } else {
            this.aGui.println("I don't know what you mean...");
        }
    }

    /**
     * Afficher la description de la salle actuelle.
     * @param pCmd
     */
    private void look(final Command pCmd){
        if (pCmd.hasSecondWord()) {
            this.aGui.println("I don't know how to look at something in particular yet.");
        }
        this.aGui.println(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Afficher un message par raport a l'action de manger.
     */
    private void eat(){
        this.aGui.println("You have eaten now and you are not hungry any more.");
    }
}
