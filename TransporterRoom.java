import java.util.List;

/**
 * TransporterRoom est une salle speciale qui transporte le joueur a une salle
 * aleatoire
 *
 * @author LIN Xingtong
 * @version 12/2024
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

    /**
     * @param pIndex index de la salle qu'on veut aller
     */
    public void setIndexRoom(final Integer pIndex) {
        this.aIndexTestRoom = pIndex;
    }

    /**
     * @param pDirection direction
     * @return une salle aleatoire
     */
    @Override
    public Room getExit(final String pDirection) {
        return RoomRandomizer.findRandomRoom(this.aRooms, this.aIndexTestRoom);
    }

    /**
     * @return une chaine de caractere qui decrit les sorties de la salle
     */
    @Override
    public String getExitString() {
        return "Exits: all directions you want";
    }
}
