import java.util.List;

/**
 * Decrivez votre classe TransporterRoom ici.
 *
 * @author LIN Xingtong
 * @version (un numero de version ou une date)
 */
public class TransporterRoom extends Room {
    /** Liste des salles */
    private List<Room> aRooms;
    /** Index de la salle qu'on veut aller selon cmd alea */
    private Integer aIndexTestRoom;

    /**
     * Constructeur d'objets de classe TransporterRoom
     * 
     * @param pImgName nom de l'image
     * @param pRooms   liste des salles
     */
    public TransporterRoom(final String pImgName, final List<Room> pRooms) {
        super("in the transporter room. You can be transported to a random room. \nAttention it's a one way trip!",
                pImgName);
        this.aRooms = pRooms;
    }

    public void setIndexRoom(final Integer pIndex) {
        this.aIndexTestRoom = pIndex;
    }

    /**
     * @param pDirection direction
     * @return une salle aleatoire
     */
    @Override
    public Room getExit(final String pDirection) {
        return RoomRandomizer.findRandomRoom(aRooms, this.aIndexTestRoom);
    }

    /**
     * @return une chaine de caractere qui decrit les sorties de la salle
     */
    @Override
    public String getExitString() {
        return "Exits: all directions you want";
    }
}
