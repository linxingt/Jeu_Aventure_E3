import java.util.HashMap;

/**
 * Cette classe permet de gerer une collection d'items dans le jeu.
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class ItemList {
    /** la collection des items */
    private HashMap<String, Item> aItems;

    /**
     * Constructeur d'objets de classe ItemList
     */
    public ItemList() {
        this.aItems = new HashMap<String, Item>();
    }

    /**
     * @return tous les items dans la collection aItems
     * @param pType   "room" pour les items dans la salle, "player" pour les items
     *                du joueur
     * @param pPlayer le joueur
     */
    public String getItemsNames(final String pType, final Player pPlayer) {
        String vVideMsg = pType.equals("room") ? "There is no item in this room." : "You don't have any items on you.";
        String vShowMsg = pType.equals("room") ? "You can see the following items:" : "You have the following items:";
        if (this.aItems.isEmpty()) {
            return vVideMsg;
        }
        String vItemsNames = vShowMsg + "\n";
        for (String vItemName : this.aItems.keySet()) {
            boolean vVisible = this.aItems.get(vItemName).getVisible();
            boolean vHaveAbleToSee = (vItemName.equals("cloth") || vItemName.equals("magicCake"))
                    && pPlayer.hasItem("glasses");
            if (pType.equals("room") ? (vVisible || (!vVisible && vHaveAbleToSee)) : true)
                vItemsNames += this.aItems.get(vItemName).getLongName() + " . ";
        }
        return vItemsNames;
    }

    /**
     * Ajoute un item dans la collection aItems en creant un objet Item.
     * 
     * @param pDescription   description de l'item
     * @param pWeight        poids de l'item
     * @param pName          nom de l'item
     * @param pCanBePickedUp si l'item peut etre ramasse
     * @param pCanBeSeen     si l'item peut etre vu
     */
    public void addItem(final String pDescription, final int pWeight, final String pName, final boolean pCanBePickedUp,
            final boolean pCanBeSeen) {
        Item vItem = new Item(pDescription, pWeight, pName, pCanBePickedUp, pCanBeSeen);
        this.aItems.put(pName, vItem);
    }

    /**
     * Ajoute un item dans la collection aItems en passant un objet Item existant.
     * 
     * @param pItem item a ajouter
     */
    public void addItem(final Item pItem) {
        this.aItems.put(pItem.getItemName(), pItem);
    }

    /**
     * @return un item dans la collection aItems selon le nom de l'item
     * @param pItemName nom de l'item
     */
    public Item getOneItem(final String pItemName) {
        if (this.aItems.containsKey(pItemName))
            return this.aItems.get(pItemName);
        return null;
    }

    /**
     * @return Item si l'item est supprime sinon null
     * @param pItemName nom de l'item à supprimer
     */
    public Item removeItem(final String pItemName) {
        return this.aItems.remove(pItemName);
    }

    /**
     * @param pItemName nom de l'item à verifier
     * @return si l'item existe dans la collection aItems
     */
    public boolean hasItem(final String pItemName) {
        return this.aItems.containsKey(pItemName);
    }
}
