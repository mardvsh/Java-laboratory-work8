import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDepthPair {
    public static final String URL_PREFIX = "http:";//строковая константа

    public String URL;//поле типа стринг. ключ дальше будет
    private int depth;//значение дальше будет
    URL host_path; //и и п

    public URLDepthPair (String URL, int depth){//конструктор пар
        this.URL=URL;
        this.depth=depth;
        try {
            this.host_path= new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getHost(){
        return host_path.getHost();
    }
    public String getPath(){
        return host_path.getPath();
    }
    public int getDepth() {
        return depth;
    }
    public String getURL() {
        return URL;
    }

    public static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) {//проверяет есть ли ссылка уже в списке
        boolean isAlready = true;
        for (URLDepthPair c : resultLink)
            if (c.getURL().equals(pair.getURL()))
                isAlready=false;
        return isAlready;
    }
}