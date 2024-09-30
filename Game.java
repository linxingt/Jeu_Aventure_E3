/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author LIN Xingtong
 */
public class Game {
    private Room aCurrentRoom;
    private Parser aParser;

    /**
     * D�marre un nouveau Game qui interagissent avec le clavier avec les salles, sorties et position actuel par defaut.
     */
    public Game() {
        this.createRooms();
        this.aParser = new Parser();
        this.play();
    }

    /**
     * Initialise position actuel et des salles du Game avec la description et sorties par defaut.
     */
    private void createRooms() {
        Room vOutside = new Room(
                "standing outside the secret laboratory, \nthe entrance looming ahead with an air of mystery and danger.");
        Room vStorage = new Room(
                "in a cluttered storage room, \nfilled with various discarded objects and tools, \nsome of which might be useful for your mission.");
        Room vClean = new Room(
                "in the clean room, \na sterile area where disinfection and changing take place. \nThe air smells faintly of chemicals, \nand a row of lockers lines the wall.");
        Room vMeeting = new Room(
                "in the meeting room, \nwhere scientists plan their experiments. \nA large table and chairs dominate the space, \nand diagrams cover the walls.");
        Room vPrison = new Room(
                "in the prison room, \nwhere human subjects are held in confinement. \nThe air feels heavy with tension, \nand cold metal cages line the walls.");
        Room vAnimal = new Room(
                "in the animal room, a dark, isolated space \nwhere animals, transformed by experiments, are kept in cages. \nThe room is eerily quiet.");
        Room vArchive = new Room(
                " in the archive room, a library-like space \nfilled with shelves of files and records. \nEach document holds secrets about the experiments conducted here.");
        Room vExperimentation = new Room(
                "in the experimentation room, \nwhere the darkest of the laboratory's procedures are carried out. \nSurgical beds and strange equipment fill the space.");
        Room vAleatoire = vAnimal; // random room, pas encore fait

        vOutside.setExits("south", vStorage);

        vStorage.setExits("down", vClean);
        vStorage.setExits("north", vOutside);

        vClean.setExits("west", vMeeting);
        vClean.setExits("south", vAleatoire);
        vClean.setExits("up", vStorage);

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
            System.out.println("Go where ?");
            return;
        }
        String vDirection = pCmd.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        if(vNextRoom==null){
            System.out.println("There is no door !");
            this.printLocationInfo();
            return;
        } else{
            this.aCurrentRoom = vNextRoom;
        }
    }

    /**
     * Afficher les informations localisation de salle actuel.
     */
    private void printLocationInfo() {
        System.out.print(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Afficher les informations de bienvenue et les informations de la position actuelle.
     */
    private void printWelcome() {
        System.out.println("Welcome to the World of Zuul!\n"
                + "World of Zuul is a new, incredibly boring adventure game.\n"
                + "Type 'help' if you need help.");
        this.printLocationInfo();
    }

    /**
     * Afficher les informations d'aide.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone.\n"
                + "You wander around at the university.\n\n"
                + "Your command words are:");
        this.aParser.showCommands();
    }

    /**
     * Returne true si la commande est "quit", false sinon.
     * @param pCmd commande a verifier
     */
    private boolean quit(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            System.out.println("Quit what ?");
            return false;
        }
        return true;
    }

    /**
     * Traiter tous les commandes du joueur, retourner true si la commande est "quit", false sinon.
     * @param pCmd commande a verifier
     */
    public boolean processCommand(final Command pCmd) {

        Command vCmd = pCmd;

        if (vCmd.isUnknown()) {
            System.out.println("Erreur du programmeur : commande non reconnue !");
        } else if (vCmd.getCommandWord().equals("go")) {
            this.goRoom(vCmd);
        } else if (vCmd.getCommandWord().equals("quit")) {
            return this.quit(vCmd);
        } else if (vCmd.getCommandWord().equals("help")) {
            this.printHelp();
        } else if (vCmd.getCommandWord().equals("look")) {
            this.look(vCmd);
        } else if (vCmd.getCommandWord().equals("eat")) {
            this.eat();
        } else {
            System.out.println("I don't know what you mean...");
        }
        return false;
    }

    /**
     * Procédure pour jouer le jeu avec les commandes du joueur.
     */
    private void play() {
        boolean vFinished = false;
        this.printWelcome();
        while (!vFinished) {
            Command vCmd = this.aParser.getCommand();
            vFinished = this.processCommand(vCmd);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Afficher la description de la salle actuelle.
     * @param pCmd
     */
    private void look(final Command pCmd){
        if (pCmd.hasSecondWord()) {
            System.out.println("I don't know how to look at something in particular yet.");
        }
        System.out.println(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Afficher un message par raport a l'action de manger.
     */
    private void eat(){
        System.out.println("You have eaten now and you are not hungry any more.");
    }
} // Game
