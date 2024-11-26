import java.util.HashMap;
// import java.util.Set;
// import java.util.Iterator;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author LIN Xingtong
 */
public class Room
{
    /** description de la salle */
    private String aDescription;
    /** les sorties de la salle avec direction et salle correspondante */
    private HashMap<String, Room> aExits;
    /** les items de la salle avec nom et item correspondant */
    private HashMap<String, Item> aItems;
    /** le nom de l'image de la salle */
    private String aImgName;
    
    /**
     * Cree une nouveau salle avec la description et aExits par defaut.
     * @param pDescription description souhaitee pour cette salle
     * @param pImgName nom de l'image de la salle
     */
    public Room (final String pDescription, final String pImgName) {
        this.aDescription=pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aItems = new HashMap<String, Item>();
        this.aImgName = pImgName;
    }
    
    /**
     * @return la description de la salle
     */
    public String getDescription(){
        return this.aDescription;
    }
    
    /**
     * @return la sortie de la salle selon la direction saisie
     * @param pDirection direction souhaitee pour retourner la sortie
     */
    public Room getExit(final String pDirection){
        return this.aExits.get(pDirection);
    }
    
    /**
     * Configure la sortie de la salle en donnant la direction et la salle correspondante.
     * @param pDirection direction souhaitee pour configurer la sortie
     * @param pRoom salle souhaitee pour configurer la sortie
     */
    public void setExits(final String pDirection, final Room pRoom){
        this.aExits.put(pDirection,pRoom);
    }
    
    /**
     * @return tous les sortie disponibles de la salle
     */
    public String getExitString(){
        String vString="Exits:";
        for(String vDirection : this.aExits.keySet()){
            vString+=" "+vDirection;
        }
        return vString;
    }    
    
    /**
     * @return la description detaillee de la salle
     * @param pPlayer joueur actuel
     */
    public String getLongDescription(final Player pPlayer){
       return "You are " + this.aDescription + "\n" + this.getExitString()+ "\n" + this.getItemsNames(pPlayer) + "\n" ;
    }  
    
    /**
     * @return le nom de l'image de la salle
     */
    public String getImgName(){
        return this.aImgName;
    }

    /**
     * @return les noms des items dans la salle
     * @param pPlayer joueur actuel
     */
    public String getItemsNames(final Player pPlayer){
        if(this.aItems.isEmpty()){
            return "There is no item in this room.";
        }
        else{
            String vString = "You can see the following items:\n";
            for(String vName : this.aItems.keySet()){
                boolean vVisible = this.aItems.get(vName).getVisible();
                if(vVisible||(!vVisible&&(vName.equals("cloth")||vName.equals("cake"))&&pPlayer.getItemsNames().contains("glasses")))
                    vString += this.aItems.get(vName).getLongName() + "\n";
            }
            return vString;
        }
    }

    /**
     * Ajoute un item dans la salle.
     * @param pDescription description de l'item
     * @param pWeight poids de l'item
     * @param pName nom de l'item
     * @param pCanBePickedUp si l'item peut etre ramasse
     * @param pCanBeSeen si l'item peut etre vu
     */
    public void addItem (final String pDescription, final int pWeight, final String pName, final boolean pCanBePickedUp, final boolean pCanBeSeen){
        Item vItem = new Item(pDescription, pWeight, pName, pCanBePickedUp, pCanBeSeen);
        this.aItems.put(pName, vItem);
    }

    /**
     * Ajoute un item dans la salle.
     * @param pItem item a ajouter
     */
    public void addItem (final Item pItem){
        this.aItems.put(pItem.getItemName(), pItem);
    }

    /**
     * @return l'item selon le nom saisi
     * @param pItemName nom de l'item souhaite
     */
    public Item getOneItem(final String pItemName){
        if(this.aItems.containsKey(pItemName))
            return this.aItems.get(pItemName);
        return null;
    }

    /**
     * Supprime un item de la salle.
     * @param pItemName nom de l'item a supprimer
     */
    public void removeItem(final String pItemName){
        this.aItems.remove(pItemName);
    }
} // Room
