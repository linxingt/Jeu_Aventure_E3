/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String aDescription;
    private Room aEastExit;
    private Room aWestExit;
    private Room aSouthExit;
    private Room aNorthExit;
    public static final Room UNKNOWN_DIRECTION = new Room( "nowhere" );
    // a dmd si ca c un attribut faut ajout a au debut
    
    public Room (final String pDescription) {
        this.aDescription=pDescription;
    }
    
    public String getDescription(){
        return this.aDescription;
    }
    
    public Room getExit(String pDirection){
        if(pDirection.equals("east")) return this.aEastExit;
        else if(pDirection.equals("west")) return this.aWestExit;
        else if(pDirection.equals("south")) return this.aSouthExit;
        else if(pDirection.equals("north")) return this.aNorthExit;
        else return UNKNOWN_DIRECTION;
    }
    
    public void setExits(
        final Room pEast, 
        final Room pWest,
        final Room pSouth, 
        final Room pNorth){
            this.aEastExit=pEast;
            this.aWestExit=pWest;
            this.aSouthExit=pSouth;
            this.aNorthExit=pNorth;
    }
    
    public String getExitString(){
        String vString="Exits: ";
        if (this.aEastExit!= null)
            vString=vString+"east ";
        if (this.aWestExit != null)
            vString=vString+"west ";
        if (this.aSouthExit != null)
            vString=vString+"south ";
        if (this.aNorthExit != null)
            vString=vString+"north ";
        return vString;
    }    
} // Room
