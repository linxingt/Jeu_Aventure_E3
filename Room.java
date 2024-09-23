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
        if(pDirection.equals("west")) return this.aWestExit;
        if(pDirection.equals("south")) return this.aSouthExit;
        if(pDirection.equals("north")) return this.aNorthExit;
        return UNKNOWN_DIRECTION;
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
    } // Room
