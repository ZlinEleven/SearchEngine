import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class WebGraph {
    private static int MAX_PAGES = 40;

    private Collection<WebPage> pages;
    private ArrayList<String> urls;
    private int[][] edges = new int[MAX_PAGES][MAX_PAGES];;
    private int pageIndex = 0;

    public WebGraph(){
        urls = new ArrayList<String>();
        pages = new ArrayList<WebPage>();
    }

    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException{
        WebGraph wg = new WebGraph();
        Scanner pageScan;
        Scanner linkScan;
        try{
            System.out.println("Loading WebGraph data...");
            pageScan = new Scanner(new File(pagesFile));
            linkScan = new Scanner(new File(linksFile));
        }
        catch(FileNotFoundException e){
            throw new IllegalArgumentException("Error: Files do not reference a valid text file.");
        }
 
        wg.createPages(pageScan);
        wg.createEdges(linkScan);
        wg.updatePageRanks();


        System.out.println("Success!");

        return wg;
    }

    public void createPages(Scanner scan){
        String[] currentLine;
        String url;
        while(scan.hasNextLine()){
            ArrayList<String> keywords = new ArrayList<String>();
            currentLine = scan.nextLine().trim().split(" ");
            url = currentLine[0];
            for(int i = 1; i < currentLine.length; i++){
                keywords.add(currentLine[i]);
            }
            addPage(url, keywords);
        }
    }

    public void createEdges(Scanner scan){
        String[] currentLine;

        while(scan.hasNextLine()){
            currentLine = scan.nextLine().trim().split(" ");
            addLink(currentLine[0], currentLine[1]);
        }
    }

    public void addPage(String url, Collection<String> keywords) throws IllegalArgumentException{
        if(urls.contains(url) || url == null || keywords == null){
            throw new IllegalArgumentException("Error: " + url + " already exists in the WebGraph. Could not add new WebPage.");
        }
        if(url == null || keywords == null){
            throw new IllegalArgumentException("Error: invalid url.");
        }
        WebPage newPage = new WebPage(url, keywords, pageIndex);
        pages.add(newPage);
        urls.add(url);
        pageIndex++;
    }
    
    public void addLink(String source, String destination) throws IllegalArgumentException{
        if(!urls.contains(source)){
            throw new IllegalArgumentException("Error: " + source +  " could not be found in the WebGraph.");
        }
        if(!urls.contains(destination)){
            throw new IllegalArgumentException("Error: " + destination +  " could not be found in the WebGraph.");
        }
        if(source == null || destination == null){
            throw new IllegalArgumentException("ERROR: URL cannot be found on web.");
        }
        int index = 0;
        int index1 = -1;
        int index2 = -1;
        for(WebPage web : pages){
            if(web.getURL().equals(source)){
                index1 = index;
            }
            if(web.getURL().equals(destination)){
                index2 = index;
            }
            index++;
        }

        if(index1 == -1 || index2 == -1){
            throw new IllegalArgumentException("ERROR: URL cannot be found on web.");
        }

        if(edges[index1][index2] == 1){
            throw new IllegalArgumentException("ERROR: link was already established");
        }
        edges[index1][index2] = 1;
    }

    public void removePage(String url){
        if(url != null && urls.contains(url)){
            ArrayList<WebPage> indexOrder = new ArrayList<WebPage>();

            for(WebPage wp : pages){
                indexOrder.add(wp);
            }

            Collections.sort(indexOrder, new IndexComparator());

            int index = 0;
            boolean flag = false;
            for(WebPage wp : indexOrder){
                if(wp.getURL().equals(url)){
                    pages.remove(wp);
                    urls.remove(url);
                    for(int k = 0; k < pages.size() + 1; k++){
                        for(int j = index; j < pages.size(); j++){
                            edges[k][j] = edges[k][j+1];
                        }
                    }
                    for(int k = 0; k < pages.size() + 1; k++){
                        for(int j = index; j < pages.size(); j++){
                            edges[j][k] = edges[j+1][k];
                        }
                    }

                    flag = true;
                    
                }
                if(flag){
                    wp.setIndex(wp.getIndex() - 1);
                }
                index++;
            }
            updatePageRanks();
            updateLinks();
        }
    }

    public void removeLink(String source, String destination){
        if(source == null || destination == null){
            throw new IllegalArgumentException("ERROR: URL cannot be found on web.");
        }
        int index = 0;
        int index1 = -1;
        int index2 = -1;
        for(WebPage web : pages){
            if(web.getURL().equals(source)){
                index1 = index;
            }
            if(web.getURL().equals(destination)){
                index2 = index;
            }
            index++;
        }

        if(index1 == -1 || index2 == -1){
            throw new IllegalArgumentException("ERROR: URL cannot be found on web.");
        }

        edges[index1][index2] = 0;
    }

    public void updatePageRanks(){
        int index = 0;
        for(WebPage wp : pages){
            int rank = 0;
            for(int i = 0; i < pages.size(); i++){
                if(edges[i][index] == 1){
                    rank++;
                }
                
            }
            index++;
            wp.setRank(rank);
        }
    }

    public void updateLinks(){
        int index = 0;
        for(WebPage wp : pages){
            ArrayList<Integer> links = new ArrayList<Integer>();
            for(int i = 0; i < pages.size(); i++){
                if(edges[index][i] == 1){
                    links.add(i);
                }
            }
            index++;
            wp.setLinks(links);
        }
    }

    public void printEdge(){
        for(int r = 0; r < pages.size(); r++){
            for(int c = 0; c < pages.size(); c++){
                System.out.print(edges[r][c] + "\t");
            }
            System.out.println();
        }
    }

    public void printTable(String order){
        updatePageRanks();
        updateLinks();
        System.out.println(String.format("%-10s%-18s%-10s%-19s%-1s", "Index", "URL", "PageRank", "Links", "Keywords"));
        System.out.println("------------------------------------------------------------------------------------------------------");
        
        //printEdge();
        if(order.equals("I")){
            printTableIndex();   
        }
        else if(order.equals("R")){
            printTableRank();
        }
        else if(order.equals("U")){
            printTableURL();
        }
    }

    public void printTableIndex(){
        ArrayList<WebPage> indexOrder = getIndexOrder();

        for(WebPage wp : indexOrder){
            System.out.println(wp);
        }
    }

    public ArrayList<WebPage> getIndexOrder(){
        ArrayList<WebPage> indexOrder = new ArrayList<WebPage>();

        for(WebPage wp : pages){
            indexOrder.add(wp);
        }

        Collections.sort(indexOrder, new IndexComparator());
        return indexOrder;
    }

    public void printTableRank(){
        ArrayList<WebPage> rankOrder = getRankOrder();

        for(WebPage wp : rankOrder){
            System.out.println(wp);
        }
    }

    public ArrayList<WebPage> getRankOrder(){
        ArrayList<WebPage> rankOrder = new ArrayList<WebPage>();

        for(WebPage wp : pages){
            rankOrder.add(wp);
        }

        Collections.sort(rankOrder, new RankComparator());
        return rankOrder;
    }

    public void printTableURL(){
        ArrayList<WebPage> URLOrder = getURLOrder();

        for(WebPage wp : URLOrder){
            System.out.println(wp);
        }
    }

    public ArrayList<WebPage> getURLOrder(){
        ArrayList<WebPage> URLOrder = new ArrayList<WebPage>();

        for(WebPage wp : pages){
            URLOrder.add(wp);
        }

        Collections.sort(URLOrder, new URLComparator());
        return URLOrder;
    }

    public void printByKeyword(String keyword){
        updatePageRanks();

        ArrayList<WebPage> rankOrder = new ArrayList<WebPage>();

        int count = 0;
        for(WebPage wp : pages){
            if(wp.getKeywords().contains(keyword)){
                count++;
            }
            rankOrder.add(wp);
        }

        if(count == 0){
            System.out.println("No search results found for the keyword " + keyword + ".");
            return;
        }

        
        Collections.sort(rankOrder, new RankComparator());


        int index = 1;
        System.out.println(String.format("%-7s%-12s%-1s", "Rank", "PageRank", "URL"));
        System.out.println("---------------------------------------------");
        for(WebPage wp : rankOrder){
            if(wp.getKeywords().contains(keyword)){
                System.out.println(String.format("%3s%-10s%1s", index, "  |    " + wp.getRank() + "     |", " " + wp.getURL()));
                index++;
            }
        }
    }
}
