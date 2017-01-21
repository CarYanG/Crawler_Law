package WangJF;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetContent {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		String fileName = "urlList.txt";
		Scanner scanner = new Scanner(new File(fileName));
		int count = 197000;
		while (scanner.hasNextLine()) {
			String url = scanner.nextLine();
			System.out.println(url);
			try {
				Document document = getPage(url);
				if (document == null) {
					System.err.println("Fail: " + url);
					continue;
				}
				String text = document.text();
				PrintWriter writer = new PrintWriter("zj\\doc"+count+".txt");
				writer.write(text);
				writer.close();
				count ++;
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("fail: " + url);
			}
		}
	}

	/**
	 * 获取指定URL的页面
	 * @param url
	 * @return
	 */
	public static Document getPage(String url) {
		Connection c = Jsoup.connect(url);
		c.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2017.2 Safari/537.36 OPR/24.0.1537.0 (Edition Developer)");
		Connection.Response res = null;
		while (true) {
			try {
				c.timeout(10000);
				res = c.execute();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("We will try it later...");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println("Try again...");
		}
		Document doc = null;
		try {
			doc = res.parse();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Parse failed!");
		}
		System.out.println("Succeed!");
		return doc;
	}
}
