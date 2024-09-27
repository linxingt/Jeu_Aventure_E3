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
    
    /**
     * Cree une nouveau salle avec la description et aExits par defaut.
     * @param pDescription description souhaitee pour cette salle
     */
    public Room (final String pDescription) {
        this.aDescription=pDescription;
        this.aExits = new HashMap<String, Room>();
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
     * Retourne la description détaillée de la salle.
     */
    public String getLongDescription(){
       return "You are " + this.aDescription + ".\n" + this.getExitString();
    }    
} // Room
