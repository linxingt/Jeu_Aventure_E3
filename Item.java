
/**
 * D�crivez votre classe Item ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class Item
{
    // variables d'instance - remplacez l'exemple qui suit par le v�tre
    private String vDescription;
    private int vWeight;

    /**
     * Constructeur d'objets de classe Item
     */
    public Item(final String pItemDescription,final int pItemWeight)
    {
        // initialisation des variables d'instance
        this.vDescription = pItemDescription;
        this.vWeight = pItemWeight;
    }

    /**
     * Retourne la description de l'objet.
     */
    public String getItemDescription()
    {
        return this.vDescription;
    }

    /**
     * Retourne le poids de l'objet.
     */
    public int getItemWeight()
    {
        return this.vWeight;
    }
    
    public String getLongDescription(){
        return "There is "+this.getItemDescription() + " (weight: " + this.getItemWeight() + "g).";
     }  
}
