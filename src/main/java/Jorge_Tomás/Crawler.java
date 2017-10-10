package Jorge_Tomás;

/**
 * Created by tomas on 09/10/2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;


public class Crawler {
    private List <String> pages;
    private List <String> adverts;
    private List <List<String>> advertDetails;


    public Crawler() {
        pages = new ArrayList <>();
        adverts = new ArrayList <>();
        advertDetails = new ArrayList <>();
    }

    // Gets all the 'destaques' web pages
    public void getWebPages(String URL) {
        pages.add(URL);
        try {
            Document document = Jsoup.connect(URL).get();
            Elements otherPages = document.select("a[href^=\"https://www.standvirtual.com/destaques/?page=\"]");

            for (Element page : otherPages) {
                String pageHref= page.attr("abs:href");
                if (!pages.contains(pageHref)) {
                    pages.add(pageHref);
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(pages);

    }

    //Connect to each 'destaques' page get all car advert links
    public void getAdvertLink() {
        pages.forEach(page -> {
            Document document;
            try {
                document = Jsoup.connect(page).get();
                Elements advertBoxes = document.getElementsByClass("rel   img-cover");
                for (Element advert : advertBoxes) {
                    String advertHref = advert.attr("abs:href");
                    if (!adverts.contains(advertHref)) {
                        adverts.add(advertHref);
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
        System.out.println(adverts);
    }

    public void getAdvertDetails() {
        adverts.forEach(advert -> {
            Document document;
            try {
                document = Jsoup.connect(advert).get();
                Elements advertDetails = document.getElementsByClass("offer-params__item");
                Elements advertExtras = document.getElementsByClass("offer-features__item");
                String advertID = document.getElementsByClass("offer-meta__value").last().text();
                String price = document.getElementsByClass("offer-price__number").text();
                System.out.println(advertID);
                System.out.println(advert);
                for (Element detail : advertDetails) {
                    Elements children = detail.children();
                    String detailKey = children.first().text();
                    String detailValue = children.last().text();
                    System.out.println(detailKey + ": " + detailValue);
                }

                for (Element extras : advertExtras) {
                    System.out.println(extras.text());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }
/*
    public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp = "- Title: " + a.get(0) + " (link: " + a.get(1) + ")\n";
                    //display to console
                    System.out.println(temp);
                    //save to file
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
*/
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getWebPages("https://www.standvirtual.com/destaques/");
        crawler.getAdvertLink();
        crawler.getAdvertDetails();
        //bwc.getCars();
        //bwc.writeToFile("´ars");
    }
}
