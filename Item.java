
/**
 * Decrivez votre classe Item ici.
 *
 * @author (votre nom)
 * @version (un numero de version ou une date)
 */
public class Item
{
    /** description de l'objet */
    private String aDescription;
    /** poids de l'objet */
    private int aWeight;
    /** nom de l'objet */
    private String aName;
    /** si l'objet peut etre ramasse */
    private boolean aCanBePickedUp;
    /** si l'objet est visible */
    private boolean aVisible;

    /**
     * Constructeur d'objets de classe Item
     * @param pItemDescription description de l'objet
     * @param pItemWeight poids de l'objet
     * @param pItemName nom de l'objet
     * @param pItemCanBePickedUp si l'objet peut etre ramasse
     * @param pItemVisible si l'objet est visible
     */
    public Item(final String pItemDescription,final int pItemWeight,final String pItemName,final boolean pItemCanBePickedUp,final boolean pItemVisible)
    {
        // initialisation des variables d'instance
        this.aDescription = pItemDescription;
        this.aWeight = pItemWeight;
        this.aName = pItemName;
        this.aCanBePickedUp = pItemCanBePickedUp;
        this.aVisible = pItemVisible;
    }
    
    /**
     * Modifie si l'objet est visible
     * @param pItemVisible si l'objet est visible
     */
    public void setVisible(final boolean pItemVisible){
        this.aVisible = pItemVisible;
    }

    /**
     * @return si l'objet est visible
     */
    public boolean getVisible(){
        return this.aVisible;
    }

    /**
     * @return le nom de l'objet
     */
    public String getItemName()
    {
        return this.aName;
    }

    /**
     * @return si l'objet peut etre ramasse
     */
    public boolean getCanBePickedUp()
    {
        return this.aCanBePickedUp;
    }

    /**
     * @return description de l'objet
     */
    public String getItemDescription()
    {
        return this.aDescription;
    }

    /**
     * @return le poids de l'objet
     */
    public int getItemWeight()
    {
        return this.aWeight;
    }
    
    /**
     * @return le nom de l'objet et son poids
     */
    public String getLongName(){
        return this.aName + " (weight: " + this.getItemWeight() + "%)";
     }  
}
