/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Game {
    private Room aCurrentRoom;
    private Parser aParser;

    public Game() {
        this.createRooms();
        this.aParser = new Parser();
        play();
    }

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

        vOutside.setExits(null, null, vStorage, null);
        vStorage.setExits(null, null, vClean, vOutside);
        vClean.setExits(null, vMeeting, vAleatoire, vStorage);
        vMeeting.setExits(vClean, vArchive, vPrison, vAnimal);
        vPrison.setExits(null, null, null, vMeeting);
        vAnimal.setExits(null, vExperimentation, vMeeting, null);
        vArchive.setExits(vMeeting, null, null, vExperimentation);
        vExperimentation.setExits(vAnimal, null, vArchive, null);

        this.aCurrentRoom = vOutside;
    }

    private void goRoom(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            System.out.println("Go where ?");
            return;
        }
        String vDirection = pCmd.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        if (vNextRoom == Room.UNKNOWN_DIRECTION) {
            System.out.println("Unknown direction !");
            return;
        } else if(vNextRoom==null){
            System.out.println("There is no door !");
            this.printLocationInfo();
            return;
        } else{
            this.aCurrentRoom = vNextRoom;
        }
    }

    private void printLocationInfo() {
        System.out.println("You are " + this.aCurrentRoom.getDescription());
        System.out.print(this.aCurrentRoom.getExitString());
        System.out.println();
    }

    private void printWelcome() {
        System.out.println("Welcome to the World of Zuul!\n"
                + "World of Zuul is a new, incredibly boring adventure game.\n"
                + "Type 'help' if you need help.");
        this.printLocationInfo();
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone.\n"
                + "You wander around at the university.\n\n"
                + "Your command words are:\n"
                + "  go quit help");
    }

    private boolean quit(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            System.out.println("Quit what ?");
            return false;
        }
        return true;
    }

    public boolean processCommand(final Command pCmd) {

        Command vCmd = pCmd;

        if (vCmd.isUnknown()) {
            System.out.println("Erreur du programmeur : commande non reconnue !");
        } else if (vCmd.getCommandWord().equals("go")) {
            goRoom(vCmd);
        } else if (vCmd.getCommandWord().equals("quit")) {
            return this.quit(vCmd);
        } else if (vCmd.getCommandWord().equals("help")) {
            this.printHelp();
        } else {
            System.out.println("I don't know what you mean...");
        }
        return false;
    }

    private void play() {
        boolean vFinished = false;
        printWelcome();
        while (!vFinished) {
            Command vCmd = this.aParser.getCommand();
            vFinished = processCommand(vCmd);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
} // Game
