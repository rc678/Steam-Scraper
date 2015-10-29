import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;

/**
 * class Scraper is responsible for scraping steam web page
 */
public class Scraper
{
    private ArrayList<Game> steamStore = new ArrayList<Game>();
    //ArrayList<Elements> titles = new ArrayList<Elements>();
    private Game currGame;

    public static void main(String[] args) throws IOException
    {
        /*information to be put into database*/
        Elements appIdElems, gameNameElems, photoUrlElems, priceElems, tagsElems, releaseDateElems, operatingSystemElems,
                ratingElems, originalPriceElems, salePriceElems;

        String baseWebPage = "http://store.steampowered.com/search/?sort_by=&sort_order=0&page=";
        /*storing HTTP response from steam website*/
        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();

        /*retrieving Elements of all relevent information for initial page.*/
        appIdElems = getAppId(doc);
        gameNameElems = getGameNames(doc);
        photoUrlElems = getPhotoUrl(doc);
        priceElems = getPrice(doc);
        //tags = get by crawling into more details of the game?
        releaseDateElems = getReleaseDates(doc);//using multiple class selectors in css
        //operatingSystem =
        ratingElems = getRatings(doc);

        //Game newGame = new Game();

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

            //System.out.println(appIdElems.attr("data-ds-appid"));
            //System.out.println(appIdElems.toArray().length);
            Elements results = doc.getElementsByClass("search_result_row");
            for(Element e: results);
            {
                String appid = results.attr("data-ds-appid");
                System.out.println(appid);
            }
            /*creates Game objects for all game information on page*/
            createGameObjectsonPage(gameNameElems, releaseDateElems, appIdElems, photoUrlElems, ratingElems);
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
     * @return Elements object that holds both positive and negative ratings of game
     */
    private static Elements getRatings(Document webpage)
    {
        Elements ratings = webpage.select("div.col.search_reviewscore.responsive_secondrow span");
        return ratings;
    }

    /**
     * Method getPrice gets the price of the game on the current webpage
     * @param webpage Webpage of the current url that contains information about steam
     * @return Elements object that holds both the sale price and original price
     */
    private static Elements getPrice(Document webpage)
    {
        Elements price = webpage.select("div.col.search_price.responsive_secondrow");
        return price;
    }

    /**
     *
     * @param gameNameElems
     * @param releaseDateElems
     * @param appIdElems
     * @param photoUrlElems
     * @param ratingElems
     */
    private static void  createGameObjectsonPage(Elements gameNameElems, Elements releaseDateElems, Elements appIdElems,
                                                 Elements photoUrlElems, Elements ratingElems)
    {

    }

}
