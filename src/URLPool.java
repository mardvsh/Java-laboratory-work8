import java.util.LinkedList;

public class URLPool { //список всех юрл адресов и глубина их поиска
    LinkedList<URLDepthPair> findLink;
    LinkedList<URLDepthPair> viewedLink;
    int maxDepth;
    int cWait;

    public URLPool(int maxDepth) {//конструктор
        this.maxDepth = maxDepth;
        findLink = new LinkedList<URLDepthPair>();
        viewedLink = new LinkedList<URLDepthPair>();
        cWait = 0;
    }

    public synchronized URLDepthPair getPair() {
        //получения пары
        //URL-глубина из пула
        while (findLink.size() == 0)
        {
            // чтобы URLPool отслеживал
            //сколько потоков ожидает новый URL-адрес
            cWait++;//которое будет увеличиваться непосредственно перед вызовом
            //wait()
            try
            {
                wait(); //ожидание, пока нет доступных адресов (раб пот)
            } catch (InterruptedException e) {
                System.out.println("Ignoring InterruptedException");
            }
            cWait--;
        }
        URLDepthPair nextPair = findLink.removeFirst();//находит новый
        return nextPair;
    }
//и удаления этой пары из списка одновременно
    public synchronized void addPair(URLDepthPair pair) {
        if(URLDepthPair.check(viewedLink,pair)) //есть ли она в листе
        {// добавления пары URL-глубина к пулу
            viewedLink.add(pair);
            if (pair.getDepth() < maxDepth) {
                findLink.add(pair);
                notify(); //когда новый юрл добавлен к пулу
            }
        }
    }

    public synchronized int getWait() {
        return cWait;
    }

    public LinkedList<URLDepthPair> getResult() {
        return viewedLink;
    }
}