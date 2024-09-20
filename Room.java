/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String aDescription;
    public Room aEastExit;
    public Room aWestExit;
    public Room aSouthExit;
    public Room aNorthExit;
    
    public Room (final String pDescription) {
        this.aDescription=pDescription;
    }
    
    public String getDescription(){
        return this.aDescription;
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
