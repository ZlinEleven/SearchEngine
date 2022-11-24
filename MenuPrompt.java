import java.util.Scanner;

public class MenuPrompt {
    String AP = "    (AP) - Add a new page to the graph.\n";
    String RP = "    (RP) - Remove a page from the graph.\n";
    String AL = "    (AL) - Add a link between pages in the graph.\n";
    String RL = "    (RL) - Remove a link between pages in the graph.\n";
    String P =  "    (P)  - Print the graph.\n";
    String S =  "    (S)  - Search for pages with a keyword.\n";
    String Q =  "    (Q)  - Quit.";
    String menu = "Menu:\n" + AP + RP + AL + RL + P + S + Q;


    public void printMenu(){
        System.out.println(menu);
    }

    public void skipLine(){
        System.out.println();
    }

    public String choosePrompt(Scanner scan){
        System.out.print("Please select an option: ");
        return scan.nextLine();
    }

    public String pPrompt(Scanner scan){
        skipLine();
        System.out.println("    (I) Sort based on index (ASC)");
        System.out.println("    (U) Sort based on URL (ASC)");
        System.out.println("    (R) Sort based on rank (DSC)");
        skipLine();
        System.out.print("Please select an option: ");

        return scan.nextLine();
    }

    public String sPrompt(Scanner scan){
        System.out.print("Search keyword: ");
        String selection = scan.nextLine();

        skipLine();
        return selection;
    }

    public String[] alPrompt(Scanner scan){
        System.out.print("Enter a source URL: ");
        String source = scan.nextLine();
        System.out.print("Enter a destination URL: ");
        String dest = scan.nextLine();
        skipLine();
        
        String[] urls = new String[2];
        urls[0] = source;
        urls[1] = dest;

        return urls;
    }

    public String[] rlPrompt(Scanner scan){
        System.out.print("Enter a source URL: ");
        String source = scan.nextLine();
        System.out.print("Enter a destination URL: ");
        String dest = scan.nextLine();
        skipLine();

        String[] ret = new String[2];
        ret[0] = source;
        ret[1] = dest;

        return ret;
    }
}
