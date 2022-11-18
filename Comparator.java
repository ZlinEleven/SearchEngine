import java.util.*;


class IndexComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        if(w1.getIndex() == w2.getIndex()){
            return 0;
        }
        else if(w1.getIndex() < w2.getIndex()){
            return -1;
        }
        else{
            return 1;
        }
    }
    
}

class URLComparator implements Comparator{
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        return w1.getURL().compareTo(w2.getURL());
    }

}

class RankComparator implements Comparator{
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        if(w1.getRank() == w2.getRank()){
            return 0;
        }
        else if(w1.getRank() < w2.getRank()){
            return 1;
        }
        else{
            return -1;
        }
    }

}