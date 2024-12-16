/**
 * Cette classe est utilisee pour stocker les informations d'une commande entree
 *
 * @author LIN Xingtong
 * @version 12/2024
 */
public class Command {
    /** le premier mot de la commande */
    private String aCommandWord;
    /** le second mot de la commande si il y en a un sinon null */
    private String aSecondWord;

    /**
     * Cree une nouvelle commande avec 2 mots.
     * 
     * @param pCommandWord premier mot
     * @param pSecondWord  second mot
     */
    public Command(final String pCommandWord, final String pSecondWord) {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    }

    /**
     * @return le premier mot de la commande
     */
    public String getCommandWord() {
        return this.aCommandWord;
    }

    /**
     * @return le second mot de la commande
     */
    public String getSecondWord() {
        return this.aSecondWord;
    }

    /**
     * @return true si la commande a un second mot, false sinon
     */
    public boolean hasSecondWord() {
        return this.aSecondWord != null;
    }

    /**
     * @return true si le premier mot de la commande est null, false sinon
     */
    public boolean isUnknown() {
        return this.aCommandWord == null;
    }
} // Command
