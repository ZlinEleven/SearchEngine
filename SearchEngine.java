import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE =  "links.txt";

    public static void main(String[] args){
        WebGraph web = new WebGraph();
        Scanner scan = new Scanner(System.in);
        MenuPrompt menu = new MenuPrompt();

        try{
            web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        menu.skipLine();
        menu.printMenu();
        menu.skipLine();
        String selection = menu.choosePrompt(scan).toUpperCase();

        while(!selection.equals("Q")){
            if(selection.equals("AP")){
                System.out.print("Enter a source URL: ");
                String source = scan.nextLine();
                System.out.print("Enter keywords (space-separated): ");
                String[] keywords = scan.nextLine().split(" ");
                try{
                    web.addPage(source, new ArrayList<>(Arrays.asList(keywords)));
                    menu.skipLine();
                    System.out.println(source + " successfully added to the WebGraph!");
                }
                catch(IllegalArgumentException e){
                    menu.skipLine();
                    System.out.println(e.getMessage());
                }
            }

            else if(selection.equals("RP")){
                System.out.print("Enter a URL: ");
                String url = scan.nextLine();

                web.removePage(url);
                menu.skipLine();
                System.out.println(url + " has been removed from the graph!");
            }

            else if(selection.equals("AL")){
                String[] urls = menu.alPrompt(scan);
                try{
                    web.addLink(urls[0], urls[1]);
                    System.out.println("Link successfully added from " + urls[0] + " to " + urls[1] + "!");
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(selection.equals("RL")){
                String[] urls = menu.rlPrompt(scan);
                try{
                    web.removeLink(urls[0], urls[1]);
                    System.out.println("Link removed from " + urls[0] + " to " + urls[1] + "!");
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(selection.equals("P")){
                String printOrder = menu.pPrompt(scan).toUpperCase();
                menu.skipLine();
                web.printTable(printOrder);
            }

            else if(selection.equals("S")){
                String keyword = menu.sPrompt(scan);
                
                web.printByKeyword(keyword);
            }

            menu.skipLine();
            menu.printMenu();
            menu.skipLine();
            selection = menu.choosePrompt(scan).toUpperCase();
        }

    }
}
