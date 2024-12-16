import java.util.List;
import java.util.Random;

/**
 * Cette classe est concu pour generer une salle aleatoire.
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class RoomRandomizer {
    /** nombre de salles disponibles pour la choix aleatoire */
    public static int CNB_ROOMS = 4;

    /**
     * @param pRooms liste des salles
     * @param pIndex index de la salle qu'on veut aller si ya, sinon null
     * @return une salle aleatoire entre 1er et 4eme element de la liste
     */
    public static Room findRandomRoom(List<Room> pRooms, Integer pIndex) {
        if (pIndex != null) {
            return pRooms.get(pIndex);
        }
        Random vRandom = new Random();
        // nextInt(n) génère un entier entre 0 (inclus) et n (exclus).
        int vIndex = vRandom.nextInt(CNB_ROOMS);
        return pRooms.get(vIndex);
    }
}
