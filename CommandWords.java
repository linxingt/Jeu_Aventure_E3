 /**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // static : pour que ce tableau n'existe qu'en un seul exemplaire 
    // dans cette classe (même si on l'instancie plusieurs fois)
     
    // On ne peut pas afficher les valeurs d'un tableau par un simple S.o.p(vTab); 
    // qui se contenterait d'afficher l'adresse (= la référence) de ce tableau.
    
    // a constant array that will hold all valid command words
    private static final String aValidCommands[] = {"go", "help", "quit", "look","eat"};

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing at the moment
    } // CommandWords()

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI<aValidCommands.length; vI++ ) {
            // i++ est strictement équivalent à i = i+1 
            // (exécuté à la fin de chaque tour de boucle)
            if ( aValidCommands[vI].equals( pString ) )
                return true;
        } // for
        // if we get here, the string was not found in the commands :
        return false;
    } // isCommand()

    /**
     * Affihce toutes les commandes valides.
     */
    public void showAll()
    {
        for ( String vCommand : aValidCommands ) {
            System.out.print( vCommand + "  " );
        }
        System.out.println();
    }
} // CommandWords
