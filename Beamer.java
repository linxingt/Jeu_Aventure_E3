
/**
 * Beamer est un objet special qui peut etre charge et utilise pour teleporter
 * le joueur
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class Beamer extends Item {
    /** si le beamer est charge */
    private boolean aIsCharged;
    /** si le beamer est utilise */
    private boolean aIsUsed;
    /** salle chargee (destination) */
    private Room aRoomCharged;

    /**
     * Constructeur d'objets de classe Beamer
     * 
     * @param pName nom de beamer
     */
    public Beamer(final String pName) {
        super("a beamer is a device that can be charged (only one time), and fired (only one time). \nWhen you charge the beamer, it memorizes the current room. When you fire the beamer, \nit transports you immediately back to the room it was charged in, then commande back will be disabled",
                70, pName, true, true);
        this.aIsCharged = false;
        this.aIsUsed = false;
    }

    /**
     * ajouter une salle a charger/destination
     * 
     * @return oui si la salle est chargee a nouveau, non sinon
     * @param pRoom salle a charger
     */
    public boolean chargeBeamer(final Room pRoom) {
        if (!this.aIsCharged) {
            this.aRoomCharged = pRoom;
            this.aIsCharged = true;
            return true;
        }
        return false;
    }

    /**
     * @return la salle chargee/destination si le beamer peut etre utilise, null
     *         sinon
     * @param pCurrentRoom salle actuelle
     */
    public Room fireBeamer(final Room pCurrentRoom) {
        if (this.aIsCharged && !pCurrentRoom.getImgName().equals(this.aRoomCharged.getImgName())) {
            this.aIsUsed = true;
            return this.aRoomCharged;
        }
        return null;
    }

    /**
     * @return la description de beamer si utilisable, sinon la message d'erreur
     */
    @Override
    public String getItemDescription() {
        if (this.aIsUsed)
            return "a beamer has only one time to use, you can't use it anymore";
        else
            return super.getItemDescription();
    }

    /**
     * @return la salle chargee
     */
    public Room getRoomCharged() {
        return this.aRoomCharged;
    }

    /**
     * @return si le beamer est deja utilise
     */
    public boolean getIsUsed() {
        return this.aIsUsed;
    }
}
