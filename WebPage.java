import java.util.ArrayList;
import java.util.Collection;

public class WebPage{
    private String url;
    private int index;
    private int rank;
    private Collection<String> keywords;
    private ArrayList<Integer> links;

    public WebPage(String url, Collection<String> keywords, int index){
        this.url = url;
        this.index = index;
        this.rank = 0;
        this.keywords = keywords;
    }

    public String getURL(){
        return url;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public int getRank(){
        return rank;
    }

    public void setLinks(ArrayList<Integer> links){
        this.links = links;
    }

    public ArrayList<Integer> getLinks(){
        return links;
    }

    public String linkToString(){
        String str = "";
        for(int i : links){
            str += i + ", ";
        }
        if(str.length() > 2){ str = str.substring(0, str.length() - 2); }
        return str;
    }

    public Collection<String> getKeywords(){
        return keywords;
    }

    public String toString(){
        String str = String.format("%3s%-24s%-11s%-18s%-1s", index, "   | " + url, "|    " + rank + "    |",  " " + linkToString(), "| ");
        for(String k : keywords){
            str += k + ", ";
        }
        str = str.substring(0, str.length() - 2);

        return str;
    }
}