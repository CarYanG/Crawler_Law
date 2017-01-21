/**
 * Created by carl on 2016/12/14.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;

public class Sample {
    public static void main(String[] args) {


        try{
//            Document doc = Jsoup.connect("http://wwww.baidu.com/").get();
//            String title=doc.title();
//            System.out.println("标题是:"+title);
//
//
//            String a=doc.html();
//            Elements links=doc.getElementsByTag("a");
//            for (Element link : links) {
//                String linkHref = link.attr("href");
//                System.out.println("linkHref："+linkHref);
//                String linkText = link.text();
//                System.out.println("linkText："+linkText);
//            }


            Document doc=Jsoup.connect("http://wenshu.court.gov.cn/content/content?DocID=fa7dd7c2-df9e-4977-984e-ba0566e7b344").get();
            String title=doc.title();
            System.out.println("标题是:"+title);
            System.out.println(doc.html());

            Element element=doc.getElementById("hidCaseInfo");
//            System.out.println(element.val());
//            String value=element.val();



        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
