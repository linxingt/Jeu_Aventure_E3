import java.util.HashMap;
// import java.util.Set;
// import java.util.Iterator;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author LIN Xingtong
 */
public class Room {
    /** description de la salle */
    private String aDescription;
    /** les sorties de la salle avec direction et salle correspondante */
    private HashMap<String, Room> aExits;
    /** les items de la salle avec nom et item correspondant */
    private ItemList aItems;
    /** le nom de l'image de la salle */
    private String aImgName;

    /**
     * Cree une nouveau salle avec la description et aExits par defaut.
     * 
     * @param pDescription description souhaitee pour cette salle
     * @param pImgName     nom de l'image de la salle
     */
    public Room(final String pDescription, final String pImgName) {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aItems = new ItemList();
        this.aImgName = pImgName;
    }

    /**
     * @return la description de la salle
     */
    public String getDescription() {
        return this.aDescription;
    }

    /**
     * @return la sortie de la salle selon la direction saisie
     * @param pDirection direction souhaitee pour retourner la sortie
     */
    public Room getExit(final String pDirection) {
        return this.aExits.get(pDirection);
    }

    /**
     * Configure la sortie de la salle en donnant la direction et la salle
     * correspondante.
     * 
     * @param pDirection direction souhaitee pour configurer la sortie
     * @param pRoom      salle souhaitee pour configurer la sortie
     */
    public void setExits(final String pDirection, final Room pRoom) {
        this.aExits.put(pDirection, pRoom);
    }

    /**
     * @return tous les sortie disponibles de la salle
     */
    public String getExitString() {
        String vString = "Exits:";
        for (String vDirection : this.aExits.keySet()) {
            vString += " " + vDirection;
        }
        return vString;
    }

    /**
     * @return la description detaillee de la salle
     * @param pPlayer joueur actuel
     */
    public String getLongDescription(final Player pPlayer) {
        return "You are " + this.aDescription + "\n" + this.getExitString() + "\n"
                + this.aItems.getItemsNames("room", pPlayer);
    }

    /**
     * @return le nom de l'image de la salle
     */
    public String getImgName() {
        return this.aImgName;
    }

    /**
     * @param pItemName nom de l'item a retirer
     * @return l'item retire si l'item existe, sinon null
     */
    public Item removeItem(final String pItemName) {
        return this.aItems.removeItem(pItemName);
    }

    /**
     * Ajoute un item dans la salle en passant un objet Item existant.
     * 
     * @param pItem item a ajouter
     */
    public void addItem(final Item pItem) {
        this.aItems.addItem(pItem);
    }

    /**
     * @param pItemName nom de l'item a chercher
     * @return l'item trouve si l'item existe, sinon null
     */
    public Item getOneItem(final String pItemName) {
        return this.aItems.getOneItem(pItemName);
    }

    /**
     * Ajoute un item dans la salle en creant un objet Item.
     * 
     * @param pDescription   description de l'item
     * @param pWeight        poids de l'item
     * @param pName          nom de l'item
     * @param pCanBePickedUp si l'item peut etre ramasse
     * @param pCanBeSeen     si l'item peut etre vu
     */
    public void addItem(final String pDescription, final int pWeight, final String pName, final boolean pCanBePickedUp,
            final boolean pCanBeSeen) {
        this.aItems.addItem(pDescription, pWeight, pName, pCanBePickedUp, pCanBeSeen);
    }

    /**
     * @param pRoom salle a chercher
     * @return si la salle est une sortie de la salle actuelle
     */
    public boolean isExit(final Room pRoom) {
        return this.aExits.containsValue(pRoom);
    }

    /**
     * @return le nom de la salle
     */
    public String getRoomName() {
        String vRes = "";
        int vDotIndex = this.aImgName.indexOf('.');
        String vBeforeDot = this.aImgName.substring(0, vDotIndex);
        vRes += vBeforeDot.substring(0, 1).toUpperCase() + vBeforeDot.substring(1);
        if (vBeforeDot.equals("garden") || vBeforeDot.equals("entrance"))
            return vRes;
        else {
            vRes += " room";
            return vRes;
        }
    }

} // Room
