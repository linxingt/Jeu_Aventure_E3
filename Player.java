import java.util.Stack;

/**
 * Decrivez votre classe Player ici.
 *
 * @author (votre nom)
 * @version (un numero de version ou une date)
 */
public class Player
{
    /** la salle actuelle du joueur */
    private Room aCurrentRoom;
    /** les salles precedentes du joueur */
    private Stack<Room> aPreviousRooms;
    /** les items du joueur avec nom et item correspondant */
    private ItemList aItems;
    /** le poids autorise du joueur */
    private int aWeightAllowed;
    /** le poids actuel du joueur */
    private int aWeightActual;
    /** le nom du joueur */
    private String aName;
    /** le nombre de commandes realisees par le joueur */
    private int aNbrCmd;

    /**
     * Constructeur d'objets de classe Player
     */
    public Player()
    {
        // this.aName = pName;
        this.aPreviousRooms = new Stack<Room>();
        this.aItems = new ItemList();
        this.aWeightAllowed = 100;
        this.aNbrCmd = 0;
    }

    /**
     * @return le nom du joueur
     */
    public String getName()
    {
        return this.aName;
    }

    /**
     * @return le nombre de commandes realisees par le joueur
     */
    public int getNbrCmd()
    {
        return this.aNbrCmd;
    }

    /**
     * ajouter 1 au nombre de commandes realisees par le joueur
     */
    public void setNbrCmdAddOne(){
        this.aNbrCmd=this.aNbrCmd+1;
    }
    
    /**
     * Modifier la salle actuelle du joueur
     * @param pRoom salle actuelle du joueur
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }

    /**
     * @return la salle actuelle du joueur
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Modifier le poids autorise du joueur
     * @param pWeightAllowed poids autorise du joueur
     */
    public void setWeightAllowed(final int pWeightAllowed)
    {
        this.aWeightAllowed = pWeightAllowed;
    }

    /**
     * @return le poids autorise du joueur
     */
    public int getWeightAllowed()
    {
        return this.aWeightAllowed;
    }

    /**
     * ramasser un item
     * @param pItem item a ramasser
     * @param pGui interface graphique pour afficher des messages
     * @return oui si l'item est ramasse, non sinon
     */
    public boolean pickUpItem(final Item pItem, final UserInterface pGui)
    {
        if(pItem.getItemWeight()+this.aWeightActual > this.aWeightAllowed)
        {
            pGui.println("You can't pick up this item, you are reaching the 100% of your weight capacity.");
            return false;
        }
        else
        {
            this.aItems.addItem(pItem);
            this.aWeightActual += pItem.getItemWeight();
            pGui.println("You picked up the item: " + pItem.getItemName());
            return true;
        }
    }

    /**
     * deposer un item
     * @param pItemName nom de l'item a deposer
     * @param pGui interface graphique pour afficher des messages
     */
    public void dropItem(final String pItemName, final UserInterface pGui)
    {
        //test if item exists in class GameEngine
        Item vItem = this.aItems.getOneItem(pItemName);
        if(vItem == null)
        {
            pGui.println("You don't have this item.");
            return;
        }
        this.aWeightActual -= vItem.getItemWeight();
        this.aItems.removeItem(pItemName);
        pGui.println("You dropped the item: " + pItemName);
    }

    /**
     * @return si joueur a des salles precedentes
     */
    public boolean PreviousRoomIsEmpty()
    {
        return this.aPreviousRooms.isEmpty();
    }

    /**
     * @return la salle precedente du joueur
     */
    public Room getPreviousRoom()
    {
        return this.aPreviousRooms.peek();
    }

    /**
     * supprimer la salle precedente du joueur
     * @return la salle precedente du joueur qui vient d'etre supprimee
     */
    public Room removePreviousRoom()
    {
        return this.aPreviousRooms.pop();
    }

    /**
     * ajouter une salle precedente au joueur
     * @param pRoom salle precedente a ajouter
     */
    public void addPreviousRoom(final Room pRoom)
    {
        this.aPreviousRooms.push(pRoom);
    }

    /**
     * @return les informations sur le poids du joueur
     */
    public String getWeightInfo()
    {
        return "You are carrying " + this.aWeightActual + "% of your weight capacity (" + this.aWeightAllowed + "%)";
    }

    /**
     * @param vItemName nom de l'item que le joueur veut obtenir
     * @return l'item que le joueur veut obtenir
     */
    public Item getOneItem(String vItemName) {
        return this.aItems.getOneItem(vItemName);
    }

    /**
     * @return les noms des items du joueur
     */
    public String getItemsNames() {
        return this.aItems.getItemsNames("player",this);
    }

    /**
     * supprimer un item du joueur
     * @param vItemName nom de l'item a retirer
     */
    public void removeItem(String vItemName) {
        Item vItem=this.aItems.removeItem(vItemName);
        this.aWeightActual -= vItem.getItemWeight();
    }

    /**
     * @param pItemName nom de l'item
     * @return si le joueur a l'item
     */
    public boolean hasItem(String pItemName) {
        return this.aItems.hasItem(pItemName);
    }
    
    /**
     * supprimer toutes les salles precedentes du joueur
     */
    public void removeAllPreviousRooms(){
        this.aPreviousRooms.clear();
    }
}
