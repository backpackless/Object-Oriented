package librarysearch;

import java.util.Scanner;

/** LibrarySearchDemo class
 *
 * @author Ben Walker
 *
 * Runs the program based on user input
 * ACKNOWLEDGEMENT: Professor Song's code was used as the basis for this code
 */
public class LibrarySearchDemo {
    public static void main( String[] args ) {
        String in, out = null;
        
        /*ensure the user has supplied the correct command line arguments*/
        /*IMPORTANT NOTE: files must be in root directory for LoadRecords() to work*/
        if(args.length != 2) {
            System.out.println("System Error: Incorrect Arguments\n"
                    + "Requires form: java LibrarySearch <input file> <output file>\n"
                    + "      OR\n"
                    + "Form: java LibrarySearch <output file>");
            System.exit(0);
        }
            
	Scanner input = new Scanner( System.in );
	LibrarySearch library = new LibrarySearch();
	String command;
        
        /*determine user's desired operation, load records from the desired file*/
        if(args.length == 2) {
            in = args[0];
            out = args[1];
            library.LoadRecords(in);
        } else if(args.length == 1) {
            out = args[0];
        }
        
        /*run the remainder of the program using user input*/
	do {
            System.out.print( "Enter add, search, or quit> " );
            command = input.nextLine();
            if( command.equalsIgnoreCase("add") || command.equalsIgnoreCase("a") )
		library.add(input);
            else if( command.equalsIgnoreCase("search") || command.equalsIgnoreCase("s") )
		library.search(input);			
	} while( !command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q") );
        library.WriteToFile(out);
    }
}


