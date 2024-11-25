
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
     * @param pItemDescription description de l'objet
     * @param pItemWeight poids de l'objet
     */
    public Item(final String pItemDescription,final int pItemWeight)
    {
        // initialisation des variables d'instance
        this.vDescription = pItemDescription;
        this.vWeight = pItemWeight;
    }

    /**
     * @return description de l'objet
     */
    public String getItemDescription()
    {
        return this.vDescription;
    }

    /**
     * @return le poids de l'objet
     */
    public int getItemWeight()
    {
        return this.vWeight;
    }
    
    /**
     * @return la description longue de l'objet
     */
    public String getLongDescription(){
        return "There is "+this.getItemDescription() + " (weight: " + this.getItemWeight() + "g).";
     }  
}
