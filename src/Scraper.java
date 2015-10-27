import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;

/**
 * class is responsible for scraping steam web page
 */
public class Scraper
{
    ArrayList<Game> steamStore = new ArrayList<Game>();
    //ArrayList<Elements> titles = new ArrayList<Elements>();

    public static void main(String[] args) throws IOException
    {
        /*information to be put into database*/
        Elements appIdElems, gameNameElems, photoUrlElems, priceElems, tagsElems, releaseDateElems, operatingSystemElems,
                ratingElems, originalPriceElems, salePriceElems;

        String baseWebPage = "http://store.steampowered.com/search/?sort_by=&sort_order=0&page=";
        /*storing HTTP response from steam website*/
        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();

        /*retrieving Elements of all relevent information for initial page.*/
        appIdElems = doc.select("#search_result_container a"); //CHECK
        gameNameElems = doc.select("span.title");
        photoUrlElems = doc.select("div.col.search_capsule img");
        //priceElems = doc.select("div.col.search_price.responsive_secondrow");
        priceElems = doc.select("div.col.search_price.responsive_secondrow");
        //tags = get by crawling into more details of the game
        releaseDateElems = doc.select("div.col.search_released.responsive_secondrow");//using multiple class selectors in css
        //operatingSystem =
        ratingElems = doc.select("div.col.search_reviewscore.responsive_secondrow span");

        originalPriceElems = doc.select("div.col.search_discount.responsive_secondrow");

        /*loops through every page on steam store to gather data*/
        for(int i=1; i<=5; i++) //get rid of hardcoding later. change to real number
        {
            System.out.println("in loop");
            String link = baseWebPage + String.valueOf(i);
            doc = connectToWebpage(link);

            /*get Elements objects for game*/
            gameNameElems = getGameNames(doc);
            releaseDateElems = getReleaseDates(doc);
            appIdElems = getAppId(doc);
            //String appId = appIdElems.attr("data-ds-appid");
            photoUrlElems = getPhotoUrl(doc);
            //String url = photoUrlElems.attr("src");
            ratingElems = getRatings(doc);
            String review = ratingElems.attr("data-store-tooltip");

            //priceElems = doc.select("div.col.search_discount.responsive_secondrow");
            priceElems = doc.select("div.col.search_price.responsive_secondrow");
            for(Element var: priceElems)
            {

                if(var.text().equalsIgnoreCase("Free To Play"))//free games
                {
                    //System.out.println(var.text());
                }else
                {
                    //sale and non sale prices
                    String[] bothPrices = var.text().split("\\s+");
                    if(bothPrices.length == 2)//game has original and sale price
                    {
                        //store both prices
                    }else//game only has original price. not on sale
                    {
                        //store one price
                    }
                }
                //split var.text by space
                //if(var.text().equalsIgnoreCase("Free To Play"))
            }

            //System.out.println(originalPrice.text());
        }
    }

    /**
     * Method ConnectToWebsite takes the current webpage and loads the page into a Document object
     * @param url Link used for HTTP Get request on website
     * @return html page stored in a Document object
     * @throws IOException
     */
    private static Document connectToWebpage(String url) throws IOException
    {
        Document doc = Jsoup.connect(url).timeout(0).get();
        return doc;
    }

    /**
     * Method getAppId gets the unique identifier for the games on the Steam webpage
     * @param webpage Webpage of current url that contains information about steam games
     * @return Elements object that holds all appids on the webpage
     */
    private static Elements getAppId(Document webpage)
    {
        Elements appId = webpage.select("#search_result_container a");
        return appId;
    }

    /**
     * Method getGameTitle takes the current webpage and obtains the title of the steam game
     * @param webpage Webpage of current url that contains information about steam games
     * @return Elements objects that hold all game titles on the webpage
     */
    private static Elements getGameNames(Document webpage)
    {
        //Game gameInfo = new Game();
        Elements titleElements = webpage.select("span.title");
        return titleElements;
    }

    /**
     * Method getReleaseDates gets the release dates of the game on the current webpage
     * @param webpage Webpage of current url that contains information about steam games
     * @return Elements object that holds all game release dates on the webpage
     */
    private static Elements getReleaseDates(Document webpage)
    {
        Elements dateElems = webpage.select("div.col.search_released.responsive_secondrow");
        return dateElems;
    }

    /**
     * Method getPhotoUrl gets the photoUrl of the game on the current webpage
     * @param webpage Webpage of the current url that contains information about steam games
     * @return Elements object that holds all photoUrls of the game
     */
    private static Elements getPhotoUrl(Document webpage)
    {
        Elements photoUrls = webpage.select("div.col.search_capsule img");
        return photoUrls;
    }

    /**
     * Method getRatings gets the rating of the game on the current webpage
     * @param webpage Webpage of the current url that contains information about steam  
     * @return
     */
    private static Elements getRatings(Document webpage)
    {
        Elements ratings = webpage.select("div.col.search_reviewscore.responsive_secondrow span");
        return ratings;
    }
}
