import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    ArrayList<Game> steamStore = new ArrayList<Game>();
    //ArrayList<Elements> titles = new ArrayList<Elements>();

    public static void main(String[] args) throws IOException
    {
        /*information to be put into database*/
        Elements appId, gameName, photoUrl, price, tags, releaseDate, operatingSystem, negReview,
                posReview,originalPrice, salePrice, salePercent;

        String baseWebPage = "http://store.steampowered.com/search/?sort_by=&sort_order=0&page=";

        /*storing HTTP response from steam website*/
        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();

        /*retrieving Elements of all relevent information for initial page.*/
        appId = doc.select("#search_result_container a"); //CHECK
        gameName = doc.select("span.title");
        photoUrl = doc.select("div.col.search_capsule img");
        price = doc.select("div.col.search_price.responsive_secondrow");
        releaseDate = doc.select("div.col.search_released.responsive_secondrow");//using multiple class selectors in css
        tags = 

        /*loops through every page on steam store to gather data*/
        for(int i=1; i<=5; i++) //get rid of hardcoding later. change to real number
        {
            System.out.println("in loop");
            String link = baseWebPage + String.valueOf(i);
            doc = Jsoup.connect(link).get();

            /*data scraped from steam store page*/
            gameName = doc.select("span.title");
            releaseDate = doc.select("div.col.search_released.responsive_secondrow");

            /* FIX GETTING APPID.*/
            appId = doc.select("#search_result_container a");
            String appidVal = appId.attr("data-ds-appid");


            /*FIGURE OUT HOW TO GET MULTIPLE ATTRIBUTES ON PAGE LIKE WITH APPID
            photoUrl = doc.select("div.col.search_capsule img");
            String url = photoUrl.attr("src");
            */

            System.out.println();
        }
    }


    private static Document ConnectToWebsite(String url) throws IOException {
        Document doc = Jsoup.connect(url).timeout(0).get();
        return doc;
    }

    private static void buildStore(Document website) throws IOException {
        //create game object
        String nextPage;
        //Document doc = Jsoup.connect("http://store.steampowered.com/search/").timeout(0).get();
        /*for(int i=0; i<=10; i++)//fix hardcoding later
        {
            titles = getGameTitle(website);
            nextPage = getNextWebPage(website);
            //System.out.println(nextPage);
            website = ConnectToWebsite(nextPage);
        }
        */

}

    private static Elements getGameTitle(Document website)
    {
        //Game gameInfo = new Game();
        Elements titles = website.select("span.title");
        return titles;
    }

    private static String getNextWebPage(Document website) throws IOException {
        String test;
        test = "http://store.steampowered.com/search/#sort_by=_ASC&sort_order=ASC&page=";
        Elements page = website.select("div.search_pagination_right a");
        System.out.println("page is" + page);
        String href = page.attr("href");
        String currLink;
        for(int i = 0; i < 10; i++)
        {
            currLink = test + String.valueOf(i);
            System.out.println(currLink);
            //website = ConnectToWebsite(test);
        }
        return href;
    }

    private static String getPrice(Document website)
    {
        return "";
    }
}
