import java.util.Comparator;

public class URLComparator implements Comparator{
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        return w1.getURL().compareTo(w2.getURL());
    }
}