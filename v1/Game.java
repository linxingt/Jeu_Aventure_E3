package v1;
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;
    
    public Game(){
        this.createRooms();
        this.aParser=new Parser();
        play();
    }
    
    private void createRooms(){
        Room vOutside = new Room("outside the main entrance of the university");
        Room vTheatre = new Room("in a lecture theatre");
        Room vPub = new Room("in the campus pub");
        Room vLab = new Room("in a computing lab");
        Room vOffice = new Room("in the computing admin office");
        
        vOutside.setExits(vTheatre,vPub,vLab,null);
        vTheatre.setExits(null,vOutside,null,null);
        vPub.setExits(vOutside,null,null,null);
        vLab.setExits(vOffice,null,null,vOutside);
        vOffice.setExits(null,vLab,null,null);
        
        this.aCurrentRoom=vOutside;
    }
    
    private void goRoom(final Command pCmd){
        // a
        if(!pCmd.hasSecondWord()){
            System.out.println("Go where ?");
            return;
        }
        // b
        // 1
        Room vNextRoom = null;
        // 2
        String vDirection=pCmd.getSecondWord();
        // 3
        if(vDirection.equals("east"))
            vNextRoom=this.aCurrentRoom.aEastExit;
        else if(vDirection.equals("west"))
            vNextRoom=this.aCurrentRoom.aWestExit;
        else if(vDirection.equals("south"))
            vNextRoom=this.aCurrentRoom.aSouthExit;
        else if(vDirection.equals("north"))
            vNextRoom=this.aCurrentRoom.aNorthExit;
        else{
            System.out.println("Unknown direction !");
            return;
        }
        // 4 & c
        if(vNextRoom==null){
            System.out.println("There is no door !");
            // 2
            System.out.println("U are "+this.aCurrentRoom.getDescription());
            // 3
            this.printExits();
            return;
        }
        else{
            // d
            // 1
            this.aCurrentRoom=vNextRoom;
        }
    }
    
    private void printExits(){
        System.out.println("Exits: ");
            if(this.aCurrentRoom.aEastExit!=null) System.out.print("east ");
            if(this.aCurrentRoom.aWestExit!=null) System.out.print("west ");
            if(this.aCurrentRoom.aSouthExit!=null) System.out.print("south ");
            if(this.aCurrentRoom.aNorthExit!=null) System.out.print("north ");
    }
    
    private void printWelcome(){
        System.out.println("Welcome to the World of Zuul!"+"\n"
        +"World of Zuul is a new, incredibly boring adventure game."+"\n"
        +"Type 'help' if you need help."+"\n"+"\n"
        +"You are "+this.aCurrentRoom.getDescription());
        this.printExits();
    }
    
    private void printHelp (){
        System.out.println("You are lost. You are alone."+"\n"
        +"You wander around at the university."+"\n"+"\n"
        +"Your command words are:"+"\n"
        +"  go quit help");
    }
    
    private boolean quit (final Command pCmd){
        if(pCmd.hasSecondWord()){
            System.out.println("Quit what ?");
            return false;
        }
        return true;
    }
    
    public boolean processCommand(final Command pCmd){
        
        Command vCmd=pCmd;
        
        if(vCmd.isUnknown()){
            System.out.println("Erreur du programmeur : commande non reconnue !");
        }
        else if(vCmd.getCommandWord().equals("go")){
            goRoom(vCmd);
        }
        else if(vCmd.getCommandWord().equals("quit")){
            return this.quit(vCmd);
        }
        else if(vCmd.getCommandWord().equals("help")){
            this.printHelp();
        }
        else{
            System.out.println("I don't know what you mean...");
        }
        return false;
    }
    
    private void play(){
        boolean vFinished = false;
        printWelcome();
        while(!vFinished){
            Command vCmd= this.aParser.getCommand();
            vFinished=processCommand(vCmd);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
} // Game
