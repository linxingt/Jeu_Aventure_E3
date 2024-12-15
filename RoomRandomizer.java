import java.util.List;
import java.util.Random;

/**
 * Decrivez votre classe RoomRandomizer ici.
 *
 * @author LIN Xingtong
 * @version (un numero de version ou une date)
 */
public class RoomRandomizer {
    /**
     * @param pRooms liste des salles
     * @return une salle aleatoire entre 1er et 4eme element de la liste
     */
    public static Room findRandomRoom(List<Room> pRooms) {
        Random vRandom = new Random();
        // nextInt(n) génère un entier entre 0 (inclus) et n (exclus).
        int vIndex = vRandom.nextInt(4);
        return pRooms.get(vIndex);
    }
}
