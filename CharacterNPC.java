import java.util.ArrayList;

/**
 * Decrivez votre classe CharacterNPC ici.
 *
 * @author LIN Xingtong
 * @version 01/2025
 */
public class CharacterNPC {
    // variables d'instance - remplacez l'exemple qui suit par le vetre
    private String aName;
    private String aDescription;
    private Item aItem;
    private String[] aDialogues;
    private String[] aDialoguesChoices;
    private int aCounter;
    private boolean aTrusted;
    private boolean aGiven;
    private int aGoodChoiceCount;
    private ArrayList<Integer> aGoodChoices;

    /**
     * Constructeur d'objets de classe CharacterNPC
     */
    public CharacterNPC(final String pName, final String pDescription, final Item pItem, final String[] pDialogues,
            final ArrayList<Integer> pGoodChoices, final String[] pDialoguesChoices) {
        // initialisation des variables d'instance
        this.aName = pName;
        this.aDescription = pDescription;
        this.aItem = pItem;
        this.aDialogues = pDialogues;
        this.aDialoguesChoices = pDialoguesChoices;
        this.aGoodChoices = pGoodChoices;
        this.aCounter = 0;
        this.aGoodChoiceCount = 0;
        this.aGiven = false;
        this.aTrusted = false;
    }

    public void talk(final UserInterface pGui, final Room pRoom) {
        int vNbrTotalTalks = (this.aDialogues.length + 1) / 2;
        // nbr de conversation active +1 reponse pareil pour ts reste
        if (this.aCounter < vNbrTotalTalks - 1) {
            String vDialogue = this.aDialogues[this.aCounter];
            pGui.println(this.aName + " : \n" + vDialogue);
            pGui.println(
                    "\nYou can now choose your response.\n(Hint : You can send talk + number to choose your response.)");
            pGui.println(2 * this.aCounter + ". " + this.aDialoguesChoices[2 * this.aCounter]);
            pGui.println((2 * this.aCounter + 1) + ". " + this.aDialoguesChoices[2 * this.aCounter + 1] + "\n");
            // car 0 a reponse 0 et 1 ; 1 a reponse 2 et 3 ; etc
            this.aCounter++;
        } else {
            if (this.aGiven)
                pGui.println("I've already given you what I can. Don't push your luck.");
            else if (this.aCounter > vNbrTotalTalks - 1)
                pGui.println("I've said all I can. Please, just leave me alone.");
            else {
                this.aCounter++;
                pGui.println(this.aName + " : \n" + this.aDialogues[vNbrTotalTalks - 1 + this.aGoodChoiceCount]
                        + giveItem(pRoom));
            } // pour 0 bon reponse, position [vNbrTotalTalks -1 +0], etc
        }
    }

    public void talk(final UserInterface pGui, final int pChoice, final Room pRoom) {
        int vNbrTotalTalks = (this.aDialogues.length + 1) / 2;
        setGoodChoiceCounter(pChoice, vNbrTotalTalks - 1);
        talk(pGui, pRoom);
    }

    public void setGoodChoiceCounter(final int pChoice, final int pGoodMax) {
        if (this.aGoodChoices.contains(pChoice))
            this.aGoodChoiceCount++;
        if (this.aGoodChoiceCount == pGoodMax)
            this.aTrusted = true;
    }

    public String giveItem(Room pRoom) {
        if (this.aTrusted) {
            pRoom.addItem(this.aItem);
            this.aGiven = true;
            return "\n" + this.aName + " dropped " + this.aItem.getItemName() + " in the room.";
        }
        return "";
    }

    public String getDescription() {
        return this.aDescription;
    }

    public String getName() {
        return this.aName;
    }
}
