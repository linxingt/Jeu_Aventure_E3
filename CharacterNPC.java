import java.util.ArrayList;

/**
 * Decrivez votre classe CharacterNPC ici.
 *
 * @author LIN Xingtong
 * @version 01/2025
 */
public class CharacterNPC {
    // variables d'instance - remplacez l'exemple qui suit par le vetre
    /** le nom du personnage non-joueur */
    private String aName;
    /** la description du personnage non-joueur */
    private String aDescription;
    /** l'item que le personnage non-joueur peut donner */
    private Item aItem;
    /** les dialogues du personnage non-joueur */
    private String[] aDialogues;
    /** les dialogues à choisir pour joueur */
    private String[] aDialoguesChoices;
    /**
     * le compteur de dialogues qui permet de naviger dans le tableau de dialogues
     */
    private int aCounter;
    /** le boolean qui indique si le personnage non-joueur peut donner l'item */
    private boolean aTrusted;
    /** le boolean qui indique si le personnage non-joueur a donné l'item */
    private boolean aGiven;
    /** le nombre de bonnes réponses que le joueur a choisies */
    private int aGoodChoiceCount;
    /** les position des bonnes réponses dans le tableau de dialogues */
    private ArrayList<Integer> aGoodChoices;

    /**
     * Constructeur d'objets de classe CharacterNPC
     * 
     * @param pName             le nom du personnage non-joueur
     * @param pDescription      la description du personnage non-joueur
     * @param pItem             l'item que le personnage non-joueur peut donner
     * @param pDialogues        les dialogues du personnage non-joueur
     * @param pGoodChoices      les position des bonnes réponses dans le tableau de
     *                          dialogues
     * @param pDialoguesChoices les dialogues à choisir pour joueur
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

    /**
     * la methode qui organise les diaglogues, notament pour ce qui commence par le
     * personnage non-joueur
     * 
     * @param pGui  l'interface utilisateur qui affiche
     * @param pRoom la salle actuelle
     */
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

    /**
     * la methode qui organise les diaglogues, et les choix du joueur
     * 
     * @param pGui    l'interface utilisateur qui affiche
     * @param pChoice le choix du joueur
     * @param pRoom   la salle actuelle
     */
    public void talk(final UserInterface pGui, final int pChoice, final Room pRoom) {
        int vNbrTotalTalks = (this.aDialogues.length + 1) / 2;
        if(pChoice<0||pChoice>this.aDialoguesChoices.length-1){
            pGui.println("Invalid choice. Please read the number before the response.");
            return;
        }
        setGoodChoiceCounter(pChoice, vNbrTotalTalks - 1);
        talk(pGui, pRoom);
    }

    /**
     * la methode qui compte le nombre de bonnes reponses et donner valeur à boolean
     * aTrusted
     * 
     * @param pChoice  le numéro de la réponse choisie
     * @param pGoodMax le nombre de bonnes réponses maximum
     */
    public void setGoodChoiceCounter(final int pChoice, final int pGoodMax) {
        if (this.aGoodChoices.contains(pChoice))
            this.aGoodChoiceCount++;
        if (this.aGoodChoiceCount == pGoodMax)
            this.aTrusted = true;
    }

    /**
     * la methode qui donne l'item au joueur en jetant l'item dans la salle
     * 
     * @param pRoom la salle actuelle
     * @return le message qui indique que l'item est donné
     */
    public String giveItem(Room pRoom) {
        if (this.aTrusted) {
            pRoom.addItem(this.aItem);
            this.aGiven = true;
            return "\n" + this.aName + " dropped " + this.aItem.getItemName() + " in the room.";
        }
        return "";
    }

    /**
     * @return la description du personnage non-joueur
     */
    public String getDescription() {
        return this.aDescription;
    }

    /**
     * @return le nom du personnage non-joueur
     */
    public String getName() {
        return this.aName;
    }
}
