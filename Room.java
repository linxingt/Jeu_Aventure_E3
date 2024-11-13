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
    private String aDescription;
    private HashMap<String, Room> aExits;
    private String aImgName;
    private Item aItem;
    
    /**
     * Cree une nouveau salle avec la description et aExits par defaut.
     * @param pDescription description souhaitee pour cette salle
     */
    public Room (final String pDescription, final String pImgName) {
        this.aDescription=pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aImgName = pImgName;
    }
    
    /**
     * Retourne la description de la salle.
     */
    public String getDescription(){
        return this.aDescription;
    }
    
    /**
     * Retourne la sortie de la salle selon la direction saisie.
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
     * Retourne tous les sortie disponibles de la salle.
     */
    public String getExitString(){
        String vString="Exits:";
        for(String vDirection : this.aExits.keySet()){
            vString+=" "+vDirection;
        }
        return vString;
    }    
    
    /**
     * Retourne la description detaillee de la salle.
     */
    public String getLongDescription(){
       return "You are " + this.aDescription + ".\n" + this.getItemDescription() + ".\n" + this.getExitString();
    }  
    
    /**
     * Retourne le nom de l'image de la salle.
     */
    public String getImgName(){
        return this.aImgName;
    }

    /**
     * Configure l'item de la salle.
     * @param pDescription description de l'item
     * @param pWeight poids de l'item
     */
    public void setItem(final String pDescription, final int pWeight){
        Item vItem = new Item(pDescription, pWeight);
        this.aItem = vItem;
    }

    public String getItemDescription(){
        if(this.aItem == null){
            return "There is no item in this room.";
        }
        return this.aItem.getLongDescription();
    }
} // Room
