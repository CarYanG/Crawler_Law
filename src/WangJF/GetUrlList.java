package WangJF;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetUrlList {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String> urlList = new LinkedList<String>();

        int current_id = 3505228;
        int maximum_id = 3705227;

        while(current_id <= maximum_id){
            String currentUrl = "http://zjsfgkw.cn/document/JudgmentDetail/" + current_id;
            Document pageDocument = getURLDocument(currentUrl);
            List<String> list = getUrlList(pageDocument);
            if(list.size() != 0) {
                String url = list.get(0);
                urlList.add("http://zjsfgkw.cn" + url);
            }
            current_id ++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(current_id + ": " + urlList.size());
        }
        PrintWriter writer = new PrintWriter("urlList.txt");
        for(String url : urlList){
            writer.println(url);
        }
        writer.close();
    }

    /**
     * 获取指定URL的页面
     * @param url
     * @return
     */
    public static Document getURLDocument(String url) {
        Connection c = Jsoup.connect(url);
        c.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2017.2 Safari/537.36 OPR/24.0.1537.0 (Edition Developer)");
        Connection.Response res = null;

        int tryCount = 0;
        final int MAX_TRY_COUNT = 100;
        while (true && tryCount < MAX_TRY_COUNT) {
            try {
                c.timeout(10000);
                res = c.execute();
                break;
            } catch (Exception e) {
                System.err.println("[GET URL ERROR]try to get " + url + ", " + tryCount + " time fails");
                System.err.println("we will try it later");
            }
            try {
                Thread.sleep(5000);//5s后重试
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.err.println("Try again.");
            tryCount++;
        }
        if(tryCount == MAX_TRY_COUNT){
            System.err.println("[GET URL ERROR] url : " + url + " reaches max try times.Fails.\n");
            return null;//返回null对象
        }
        Document doc = null;
        try {
            doc = res.parse();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[GET URL ERROR]Parse fails for " + url);
        }
        return doc;
    }

    /**
     * 获取某一页上的所有文书的URL链接列表
     * @param page 裁判文书列表页URL
     * @return 文书URL列表
     */
    public static List<String> getUrlList(Document page){
        LinkedList<String> urlList = new LinkedList<String>();
        Elements elements = page.select("iframe");
        for(Element element :elements){
            String url =  element.attr("src");
            urlList.add(url);
        }
        return urlList;
    }
}
