import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * C'est la classe principale du jeu. Elle cree les salles, le user interface,
 * etc. Elle contient une boucle qui lit et execute les commandes entrees par
 * l'utilisateur jusqu'a ce que le jeu soit termine.
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class GameEngine {
    /** le nombre maximum de commandes */
    private static final int CMAXNBRCMD = 100;
    /** le joueur */
    private Player aPlayer;
    /** le parser qui analyse les commandes */
    private Parser aParser;
    /** l'interface utilisateur */
    private UserInterface aGui;
    /** les salles du jeu */
    private ArrayList<Room> aRooms;
    /** true si le jeu est en mode test, false sinon */
    private boolean aInTestMode;
    /** le personnage non-joueur qui en train de parler avec le joueur */
    private CharacterNPC aNpcTalking;

    /**
     * Constructeur d'objets de classe GameEngine
     */
    public GameEngine() {
        // String vPrenom = javax.swing.JOptionPane.showInputDialog("What's your
        // name?");
        // this.aPlayer = new Player(vPrenom);
        this.aRooms = new ArrayList<Room>();
        this.aPlayer = new Player();
        this.createRooms();
        this.aParser = new Parser();
    }

    /**
     * Setter pour l'interface utilisateur.
     * 
     * @param pUserInterface interface utilisateur
     */
    public void setGUI(final UserInterface pUserInterface) {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Initialiser les salles, les sorties et la position actuelle du joueur.
     */
    private void createRooms() {
        Room vOutside = new Room(
                "standing outside the secret laboratory, the entrance looming ahead with an air of mystery and danger.",
                "entrance.jpg");
        Room vStorage = new Room(
                "in a cluttered storage room, filled with various discarded objects and tools.", "storage.jpg");
        Room vClean = new Room(
                "in the clean room, a sterile area where disinfection and changing take place. \nThe air smells faintly of chemicals, and a row of lockers lines the wall.",
                "clean.jpg");
        Room vMeeting = new Room(
                "in the meeting room, where scientists plan their experiments. \nA large table and chairs dominate the space, and diagrams cover the walls.",
                "meeting.jpg");
        Room vPrison = new Room(
                "in the prison room, where human subjects are held in confinement. The air feels heavy with tension.",
                "prison.jpg");
        vPrison.setIsLocked(true, "bracelet", vMeeting);
        vMeeting.setIsLocked(true, "bracelet", vPrison);
        Room vAnimal = new Room(
                "in the animal room, a isolated space where animals, transformed by experiments, are kept in cages. \nThe room is eerily quiet.",
                "animal.jpg");
        Room vArchive = new Room(
                " in the archive room, a space filled with shelves of files and records. \nEach document holds secrets about the experiments conducted here.",
                "archive.jpg");
        Room vExperimentation = new Room(
                "in the experimentation room, where the darkest of the laboratory's procedures are carried out. \nSurgical beds and strange equipment fill the space.",
                "experimentation.png");
        vExperimentation.setIsLocked(true, "card", vArchive);
        vArchive.setIsLocked(true, "card", vExperimentation);
        Room vGarden = new Room(
                "in a garden, with flowers blooming beautifully but with a rancid smell.", "garden.jpg");

        // les salles possibles pour la salle aleatoire (vTransporter)
        this.aRooms.add(vStorage);
        this.aRooms.add(vClean);
        this.aRooms.add(vMeeting);
        this.aRooms.add(vArchive);

        this.aRooms.add(vOutside);
        this.aRooms.add(vPrison);
        this.aRooms.add(vAnimal);
        this.aRooms.add(vExperimentation);
        this.aRooms.add(vGarden);

        Room vTransporter = new TransporterRoom("transporter.jpg", this.aRooms); // random room, pas encore fait
        this.aRooms.add(vTransporter);

        vOutside.setExits("south", vStorage);
        vOutside.addItem("an old pair of glasses without lenses. Wearing it can make you see things differently", 40,
                "glasses", true, true); // 40 is the weight of the item

        vStorage.setExits("down", vClean);
        vStorage.setExits("north", vOutside);
        vStorage.addItem("a key for the cabinet in the archives room", 40, "key", true, true);
        vStorage.addItem("an invisible cloth can help you hide", 50, "cloth", true, false);
        vStorage.addItem("a torn piece of paper with the name Alice", 2, "paper", false, true);
        vStorage.addItem("a plan of the entire laboratory but broken", 10, "plan", false, true);

        vClean.setExits("west", vMeeting);
        vClean.setExits("south", vTransporter);
        vClean.setExits("up", vStorage);
        vClean.addItem("an access card for the experiment room", 40, "card", true, true);
        vClean.addItem("a magic cake that can increase your maximum ability to take things by 20% after eating it", 60,
                "magicCake", true, false);

        vMeeting.setExits("east", vClean);
        vMeeting.setExits("west", vArchive);
        vMeeting.setExits("south", vPrison);
        vMeeting.addItem(
                "a whiteboard displays a discussion about which animal to place 2566's soul into, along with a prominent slogan and a cat logo",
                500, "whiteboard", false, true);
        vMeeting.addItem(
                "a bracelet that can open the prison",
                40, "bracelet", true, true);

        vPrison.setExits("north", vMeeting);
        vPrison.setExits("west", vGarden);

        vGarden.setExits("east", vPrison);

        vAnimal.setExits("west", vExperimentation);
        vAnimal.setExits("south", vMeeting);
        vAnimal.addItem("an empty cage with a small plastic tag labeled 2566", 300, "cage", false, true);

        vArchive.setExits("east", vMeeting);
        vArchive.setExits("north", vExperimentation);
        vArchive.setExits("south", vGarden);
        Item vCabinet = new Item(
                "a notebook inside the cabinet, which contains your sister's name, her photo and what happened to her: \nshe was sent to the laboratory a day ago to conduct an experiment on souls entering animals",
                900, "cabinet", false, true);
        vCabinet.setIsLocked(true, "key");
        vArchive.addItem(vCabinet);
        Item vGoodnight = new Item("a sleeping gas in a glass bottle", 10, "goodnight", true, true);
        String[] vDialogues = {
                "Who are you? You're not supposed to be here. Are you one of the new interns? Or... are you here for something else?",
                "You seem determined. But this place is dangerous. Do you know what you're getting into?",
                "You remind me of my sister. She was always so brave...",
                "You've been selfish and cruel. I won't help you. Just know that Louis is dangerous.",
                "You've been a bit kind, but not enough. Just be careful, Louis is dangerous.",
                "You've been mostly kind. Here's a precise warning: Louis is in the Experimentation Room. Avoid him at all costs.",
                "You've been so kind and determined. Take this 'goodnight' item. It will help you if you encounter Louis. \nAlso, be careful, Louis is in the Experimentation Room, and he's very dangerous."
        };
        String[] vDialoguesChoices = {
                "I'm here to save my sister. She was taken by the scientists years ago, and I've been trying to find her ever since. \nThis is the closest I've ever been to saving her. Please, I need your help.",
                "I don't care about your experiments or your little science projects. I just need to get what I came for, \nand I don't have time for your questions.",
                "Dangerous? You think I care? I just need to get what I came for and get out of here.",
                "I know it's dangerous, but I have no choice. I have to save her, even if it means risking my life.",
                "Stop talking. I don't have time for your stories.",
                "Your sister? What happened to her?"
        };
        vArchive.addNpc(new CharacterNPC("Sophie",
                "Sophie, the daughter of a scientist. She spends her time doing homework in the archives room.",
                vGoodnight, vDialogues, new ArrayList<>(Arrays.asList(0, 3, 5)), vDialoguesChoices));

        vExperimentation.setExits("east", vAnimal);
        vExperimentation.setExits("south", vArchive);
        vExperimentation.addItem("a bed with a '2566' mark on it", 300, "bed", false, true);
        vExperimentation.addItem(
                "a little girl is sleeping in bed, and she looks very familiar. As you think about it, \nyou realize she is your sister, but she appears thinner than in the photos you saw in the files",
                50, "girl", true, true);
        vExperimentation.addItem(
                "a cat is dozing in a cage. It is truly beautiful, with long, white fur, \nbut it seems to be in pain, occasionally twitching slightly with its eyes closed",
                50, "cat", true, true);
        vExperimentation.addItem(
                "a rabbit is eating grass in a very tiny cage. Unlike the rabbits we usually see, \nit is very small, looks incredibly cute, and is surprisingly black",
                50, "rabbit", true, true);
        vExperimentation.addItem("a dog is sleeping in a cage. It looks very large.", 50, "dog", true, true);
        vExperimentation.addItem(new Beamer("beamer"));

        this.aPlayer.setCurrentRoom(vOutside);
    }

    /**
     * Deplacer le joueur dans une direction donnee.
     * 
     * @param pCmd commande a traiter
     */
    private void goRoom(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Go where ?");
            return;
        }
        String vDirection = pCmd.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit(vDirection);
        if (vNextRoom == null) {
            this.aGui.println("There is no door !");
            return;
        } else {
            boolean vLocked = vNextRoom.getIsLocked() && aPlayer.getCurrentRoom() == vNextRoom.getExistLocked();
            if (!(!vLocked || (this.aPlayer.hasItem(vNextRoom.getNameKey()) && vLocked))) {
                this.aGui.println(vNextRoom.getRoomName() + " need something to unlock.");
                return;
            }
            if (!vNextRoom.isExit(this.aPlayer.getCurrentRoom()))
                this.aPlayer.removeAllPreviousRooms();
            this.aPlayer.addPreviousRoom(this.aPlayer.getCurrentRoom());// Pushes a new element on top of this
                                                                        // Stack.
            this.aPlayer.setCurrentRoom(vNextRoom);
            this.aPlayer.setNbrCmdAddOne();
            this.aNpcTalking = null;
        }
        if (!this.limiteCmd())
            this.printLocationInfo();
    }

    /**
     * Deplacer le joueur dans la salle precedante.
     * 
     * @param pCmd commande a traiter
     */
    private void back(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("You cannot specify a direction when going back.");
            return;
        } else if (this.aPlayer.PreviousRoomIsEmpty()) {
            this.aGui.println("There's nowhere you can go back to.");
            return;
        } else if (!this.aPlayer.getCurrentRoom().isExit(this.aPlayer.getPreviousRoom())
                || this.aPlayer.getPreviousRoom() instanceof TransporterRoom) {
            this.aGui.println("You can't back on the one-way road.");
            return;
        }
        Room vNextRoom = this.aPlayer.getPreviousRoom();// Returns the head element without modifying the Stack.
        boolean vLocked = vNextRoom.getIsLocked() && aPlayer.getCurrentRoom() == vNextRoom.getExistLocked();
        if (!(!vLocked || (this.aPlayer.hasItem(vNextRoom.getNameKey()) && vLocked))) {
            this.aGui.println(vNextRoom.getRoomName() + " need something to unlock.");
            return;
        }
        this.aPlayer.setCurrentRoom(vNextRoom);
        this.aPlayer.removePreviousRoom();// Removes the head element from this Stack.
        this.printLocationInfo();
        this.aPlayer.setNbrCmdAddOne();
        this.aNpcTalking = null;
        this.limiteCmd();
    }

    /**
     * Afficher les informations localisation de salle actuel.
     */
    private void printLocationInfo() {
        if (!isWin())
            this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription(this.aPlayer));
        if (this.aPlayer.getCurrentRoom().getImgName() != null)
            this.aGui.showImage(this.aPlayer.getCurrentRoom().getImgName());
    }

    /**
     * Afficher les informations de bienvenue et les informations de la position
     * actuelle.
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
        this.aGui.println("You are lost. You are alone. You wander around at the laboratory.\n\n"
                + "Your command words are:" + this.aParser.getCommands() + "\n"
                + "Attention!!! You can only use 100 times the following commands: 'go', 'back', 'take' and 'drop'!!!\n");
    }

    /**
     * traiter la commande quit.
     * 
     * @param pCmd commande a traiter
     */
    private void quit(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("Quit can't be followed by a second word.");
        } else {
            this.aGui.println("Thank you for playing.  Good bye.");
            this.aGui.enable(false);
        }
    }

    /**
     * Traiter la commande du joueur.
     * 
     * @param pCmd commande a traiter
     */
    public void interpretCommand(final String pCmd) {

        this.aGui.println("> " + pCmd);
        Command vCmd = this.aParser.getCommand(pCmd);

        if (vCmd.isUnknown()) {
            this.aGui.println("Erreur du programmeur : commande non reconnue !");
            return;
        } else if (vCmd.getCommandWord().equals("go")) {
            this.goRoom(vCmd);
            // nbrCmd+1
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
            // nbrCmd+1
        } else if (vCmd.getCommandWord().equals("test")) {
            this.test(vCmd);
        } else if (vCmd.getCommandWord().equals("take")) {
            this.take(vCmd);
            // nbrCmd+1
        } else if (vCmd.getCommandWord().equals("drop")) {
            this.drop(vCmd);
            // nbrCmd+1
        } else if (vCmd.getCommandWord().equals("items")) {
            this.items(vCmd);
        } else if (vCmd.getCommandWord().equals("charge")) {
            this.charge(vCmd);
        } else if (vCmd.getCommandWord().equals("fire")) {
            this.fire(vCmd);
        } else if (vCmd.getCommandWord().equals("alea")) {
            this.alea(vCmd);
        } else if (vCmd.getCommandWord().equals("talk")) {
            this.talk(vCmd);
        } else {
            this.aGui.println("I don't know what you mean...");
        }
    }

    /**
     * Afficher les informations de la salle actuelle et les objets dans joueur.
     * 
     * @param pCmd commande a traiter
     */
    private void look(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            String vItemName = pCmd.getSecondWord();
            boolean vFLetterUpper = Character.isUpperCase(vItemName.charAt(0));
            if (!vFLetterUpper) {
                Item vItem = this.aPlayer.getCurrentRoom().getOneItem(vItemName);
                vItem = vItem == null ? this.aPlayer.getOneItem(vItemName) : vItem;
                if (vItem == null) {
                    this.aGui.println("The item you are looking for is not in this room or in your inventory.");
                    return;
                }
                boolean vLocked = vItem.getIsLocked();
                if (!vLocked || (this.aPlayer.hasItem(vItem.getNameKey()) && vLocked)) {
                    this.aGui.println("There is " + vItem.getItemDescription() + ".");
                    return;
                } else {
                    this.aGui.println(vItem.getItemName().substring(0, 1).toUpperCase()
                            + vItem.getItemName().substring(1) + " is locked.");
                    return;
                }
            } else {
                CharacterNPC vNpc = this.aPlayer.getCurrentRoom().getOneNpc(vItemName);
                if (vNpc == null) {
                    this.aGui.println("The NPC you are looking for is not in this room.");
                    return;
                }
                this.aGui.println(vNpc.getDescription());
                return;
            }
        }
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription(this.aPlayer) + "\n");
    }

    /**
     * Manger un objet.
     * 
     * @param pCmd commande a traiter
     */
    private void eat(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Eat what?");
            return;
        }
        if (pCmd.getSecondWord().equals("magicCake")) {
            if (this.aPlayer.hasItem("magicCake")) {
                this.aPlayer.removeItem("magicCake");
                this.aPlayer.setWeightAllowed(120);
                this.aGui.println("You have eaten the magic cake and now you have " + this.aPlayer.getWeightAllowed()
                        + "% capacity to carry things.");
            } else {
                this.aGui.println("You don't have the magic cake.");
                return;
            }
        } else
            this.aGui.println("You have eaten a food imaginary, you are not hungry anymore.");
    }

    /**
     * Tester les commandes dans un fichier.
     * 
     * @param pCmd commande a traiter qui a le nom du fichier comme second mot
     */
    private void test(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Test what?");
            return;
        }
        Scanner vSc = null;
        this.aInTestMode = true;
        try { // pour "essayer" les instructions suivantes :
            vSc = new Scanner(new File(pCmd.getSecondWord() + ".txt")); // ouverture du fichier s'il existe
            while (vSc.hasNextLine()) { // tant qu'il y a encore une ligne a lire dans le fichier
                interpretCommand(vSc.nextLine()); // lecture de la ligne dans le fichier
                /// traitement de la ligne lue
            } // while
        } // try
        catch (final FileNotFoundException pFNFE) { // si le fichier n'existe pas
            // traitement en cas d'exception
            this.aGui.println("You can't test this file because it doesn't exist.");
        } // catch
        finally {
            this.aInTestMode = false;
            if (vSc != null) {
                vSc.close();
                return;
            }
        }
    }

    /**
     * Prendre un objet.
     * 
     * @param pCmd commande a traiter
     */
    private void take(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Take what?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getOneItem(vItemName);
        if (vItem == null) {
            this.aGui.println("There is no such item in this room.");
            return;
        }
        if (vItem.getCanBePickedUp() && this.aPlayer.pickUpItem(vItem, aGui)) {
            this.aPlayer.getCurrentRoom().removeItem(vItemName);
            this.aPlayer.setNbrCmdAddOne();
            this.limiteCmd();
        } else if (!vItem.getCanBePickedUp()) {
            this.aGui.println(vItemName + " cannot be picked up.");
        }
    }

    /**
     * Lacher un objet.
     * 
     * @param pCmd commande a traiter
     */
    private void drop(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Drop what?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getOneItem(vItemName);
        if (vItem == null) {
            this.aGui.println("You don't have this item in your inventory.");
            return;
        }
        this.aPlayer.dropItem(vItemName, aGui);
        this.aPlayer.getCurrentRoom().addItem(vItem);
        this.aPlayer.setNbrCmdAddOne();
        this.limiteCmd();
    }

    /**
     * Afficher les objets dans joueur.
     * 
     * @param pCmd commande a traiter
     */
    private void items(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("Items can't be followed by a second word.");
            return;
        }
        this.aGui.println(this.aPlayer.getItemsNames() + "\n" + this.aPlayer.getWeightInfo());
    }

    /**
     * @return true si le joueur a atteint le nombre maximum de commandes, false
     *         sinon
     */
    private boolean limiteCmd() {
        if (this.aPlayer.getNbrCmd() >= CMAXNBRCMD) {
            this.aGui.println("Game Over! You have reached the limit of " + CMAXNBRCMD + " commands.");
            this.aGui.enable(false);
            return true;
        } else if (CMAXNBRCMD - this.aPlayer.getNbrCmd() < 6) {
            this.aGui
                    .println("Attention! You have only " + (CMAXNBRCMD - this.aPlayer.getNbrCmd()) + " commands left.");
        }
        return false;
    }

    /**
     * @return true si le joueur a gagne, false sinon
     */
    private boolean isWin() {
        boolean vIsOutside = this.aPlayer.getCurrentRoom().getImgName().equals("entrance.jpg");
        boolean vHasCat = this.aPlayer.hasItem("cat");
        if (vIsOutside && vHasCat && !limiteCmd()) {
            this.aGui.println("Congratulations! You have found your sister and left the laboratory. You win!");
            this.aGui.enable(false);
            return true;
        }
        return false;
    }

    /**
     * Charger le beamer.
     * 
     * @param pCmd commande a traiter
     */
    private void charge(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("Charge can't be followed by a second word because only the beamer can be charged.");
            return;
        }
        this.aPlayer.chargeBeamer(this.aGui);
    }

    /**
     * Utiliser le beamer.
     * 
     * @param pCmd commande a traiter
     */
    private void fire(final Command pCmd) {
        if (pCmd.hasSecondWord()) {
            this.aGui.println("Fire can't be followed by a second word because only the beamer can be fired.");
            return;
        }
        if (this.aPlayer.fireBeamer(this.aGui)) {
            this.aNpcTalking = null;
            this.printLocationInfo();
        }
    }

    /**
     * Changer la salle aleatoire ou predefinie dans la salle de transport (que pour
     * mode test).
     * 
     * @param pCmd commande a traiter contient index de la salle
     */
    public void alea(final Command pCmd) {
        if (!this.aInTestMode) {
            this.aGui.println("You can't use the alea command because you are not in test mode.");
            return;
        } else if (this.aPlayer.getCurrentRoom() instanceof TransporterRoom) {
            this.aGui.println("You are in the transporter room, you can't use the alea command.");
            return;
        }

        TransporterRoom vRoom = (TransporterRoom) this.aRooms.get(aRooms.size() - 1);
        if (pCmd.hasSecondWord()) {
            Integer vIndex = Integer.parseInt(pCmd.getSecondWord());
            if (!(vIndex >= 0 && vIndex < RoomRandomizer.CNB_ROOMS))
                this.aGui
                        .println("The index of the room must be between 0 and " + (RoomRandomizer.CNB_ROOMS - 1) + ".");
            vRoom.setIndexRoom(vIndex);
        } else {
            vRoom.setIndexRoom(null);
        }
    }

    /**
     * traiter le 2eme mot dans la commande puis appel la methode talk correpondante
     * 
     * @param pCmd commande a traiter
     */
    public void talk(final Command pCmd) {
        if (!pCmd.hasSecondWord()) {
            this.aGui.println("Talk to whom? or what?");
            return;
        }
        int vTalkNum = 100;
        String vNpcName = "";
        String vNpcNameOrTalkNum = pCmd.getSecondWord();
        try {
            vTalkNum = Integer.parseInt(vNpcNameOrTalkNum);
            if (pCmd.hasSecondWord() && this.aNpcTalking == null) {
                this.aGui.println("There is no such character who you can talk to.");
                return;
            }
        } catch (NumberFormatException e) {
            vNpcName = vNpcNameOrTalkNum;
            CharacterNPC vNpc = this.aPlayer.getCurrentRoom().getOneNpc(vNpcName);
            if (vNpc == null) {
                this.aGui.println("There is no such character in this room.");
                return;
            }
            this.aNpcTalking = vNpc;
        }
        if (vTalkNum != 100)
            aNpcTalking.talk(this.aGui, vTalkNum, this.aPlayer.getCurrentRoom());
        else
            aNpcTalking.talk(this.aGui, this.aPlayer.getCurrentRoom());
    }
}
