/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author LIN Xingtong
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    
    /**
     * Cree une nouvelle commande avec 2 mots.
     * @param pCommandWord premier mot
     * @param pSecondWord second mot
     */
    public Command (final String pCommandWord, final String pSecondWord){
        this.aCommandWord=pCommandWord;
        this.aSecondWord=pSecondWord;
    }
    
    /**
     * Retourne le premier mot de la commande.
     */
    public String getCommandWord(){
        return this.aCommandWord;
    }
    
    /**
     * Retourne le second mot de la commande.
     */
    public String getSecondWord(){
        return this.aSecondWord;
    }
    
    /**
     * Retourne true si la commande a un second mot, false sinon.
     */
    public boolean hasSecondWord(){
        return this.aSecondWord != null;
    }
    
    /**
     * Retourne true si le premier mot de la commande est null, false sinon.
     */
    public boolean isUnknown(){
        return this.aCommandWord == null;
    }
} // Command
