import java.util.HashMap;
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
    private HashMap<String, Item> aItems;
    /** le poids autorise du joueur */
    private int aWeightAllowed;
    /** le poids actuel du joueur */
    private int aWeightActual;
    /** le nom du joueur */
    private String aName;

    /**
     * Constructeur d'objets de classe Player
     * @param pName nom du joueur
     */
    public Player(final String pName)
    {
        this.aName = pName;
        this.aPreviousRooms = new Stack<Room>();
        this.aItems = new HashMap<String, Item>();
        this.aWeightAllowed = 100;
    }

    /**
     * @return le nom du joueur
     */
    public String getName()
    {
        return this.aName;
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
     * @return une chaine de caractere avec les noms des items du joueur
     */
    public String getItemsNames()
    {
        if(this.aItems.isEmpty()){
            return "You don't have any items on you";
        }
        String vItemsNames = "You have the following items:\n";
        for (String vItemName : this.aItems.keySet())
        {
            vItemsNames += this.aItems.get(vItemName).getItemName() + "\n";
        }
        return vItemsNames;
    }

    /**
     * ramasser un item
     * @param pItem item a ramasser
     * @param pGui interface graphique pour afficher des messages
     */
    public void pickUpItem(final Item pItem, final UserInterface pGui)
    {
        if(pItem.getItemWeight()+this.aWeightActual > this.aWeightAllowed)
        {
            pGui.println("You can't pick up this item, you are reaching the 100% of your weight capacity.");
        }
        else
        {
            this.aItems.put(pItem.getItemName(), pItem);
            this.aWeightActual += pItem.getItemWeight();
            pGui.println("You picked up the item: " + pItem.getItemName());
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
        this.aWeightActual -= this.aItems.get(pItemName).getItemWeight();
        this.aItems.remove(pItemName);
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
     * @param pItemName nom de l'item souhaite
     * @return l'item selon le nom saisi
     */
    public Item getOneItem(final String pItemName)
    {
        if(this.aItems.containsKey(pItemName))
        {
            return this.aItems.get(pItemName);
        }
        return null;
    }

    /**
     * @return les informations sur le poids du joueur
     */
    public String getWeightInfo()
    {
        return "You are carrying " + this.aWeightActual + "% of your weight capacity (" + this.aWeightAllowed + "%)";
    }
}
