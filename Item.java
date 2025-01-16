
/**
 * Cette classe est utilisee pour stocker les informations d'un objet
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class Item {
    /** description de l'objet */
    private String aDescription;
    /** poids de l'objet */
    private int aWeight;
    /** nom de l'objet */
    private String aName;
    /** si l'objet peut etre ramasse */
    private boolean aCanBePickedUp;
    /** si l'objet est visible */
    private boolean aVisible;
    /** si l'objet est verrouille */
    private boolean aIsLocked;
    /** le nom de l'objet qui peut ouvrir l'objet verrouille */
    private String aNameKey;

    /**
     * Constructeur d'objets de classe Item
     * 
     * @param pItemDescription   description de l'objet
     * @param pItemWeight        poids de l'objet
     * @param pItemName          nom de l'objet
     * @param pItemCanBePickedUp si l'objet peut etre ramasse
     * @param pItemVisible       si l'objet est visible
     */
    public Item(final String pItemDescription, final int pItemWeight, final String pItemName,
            final boolean pItemCanBePickedUp, final boolean pItemVisible) {
        // initialisation des variables d'instance
        this.aDescription = pItemDescription;
        this.aWeight = pItemWeight;
        this.aName = pItemName;
        this.aCanBePickedUp = pItemCanBePickedUp;
        this.aVisible = pItemVisible;
        this.aIsLocked = false;
    }

    /**
     * @return si l'objet est visible
     */
    public boolean getVisible() {
        return this.aVisible;
    }

    /**
     * setter pour boolean aIsLocked
     * 
     * @param pIsLocked si l'objet est verrouille
     * @param pNameKey  le nom de l'objet qui peut ouvrir l'objet verrouille
     */
    public void setIsLocked(final boolean pIsLocked, final String pNameKey) {
        this.aIsLocked = pIsLocked;
        this.aNameKey = pNameKey;
    }

    /**
     * @return le nom de l'objet qui peut ouvrir l'objet verrouille
     */
    public String getNameKey() {
        return this.aNameKey;
    }

    /**
     * @return si l'objet est verrouille
     */
    public boolean getIsLocked() {
        return this.aIsLocked;
    }

    /**
     * @return le nom de l'objet
     */
    public String getItemName() {
        return this.aName;
    }

    /**
     * @return si l'objet peut etre ramasse
     */
    public boolean getCanBePickedUp() {
        return this.aCanBePickedUp;
    }

    /**
     * @return description de l'objet
     */
    public String getItemDescription() {
        return this.aDescription;
    }

    /**
     * @return le poids de l'objet
     */
    public int getItemWeight() {
        return this.aWeight;
    }

    /**
     * @return le nom de l'objet et son poids
     */
    public String getLongName() {
        return this.aName + " (weight: " + this.getItemWeight() + "%)";
    }
}
