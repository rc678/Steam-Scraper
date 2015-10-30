import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * class Scraper is responsible for scraping steam web page
 */
public class Scraper {
    //HashMap where key is the appid and the value is the game information
    protected static HashMap<String, Game> steamStore = new HashMap<String, Game>();

    public static void main(String[] args) throws IOException {
        /*information to be put into database*/
        Elements appIdElems, gameNameElems, photoUrlElems, tagsElems, releaseDateElems, operatingSystemElems,
                ratingElems, originalPriceElems, salePriceElems;

        ArrayList<String> appIdList = new ArrayList<String>();
        String baseWebPage = "http://store.steampowered.com/search/?sort_by=&sort_order=0&page=";
        /*storing HTTP response from steam website*/
        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();

        //UNNECESSARY?????-----CHECK
        /*retrieving Elements of all relevent information for initial page.*/
        //appIdElems = getAppId(doc);
        /*gameNameElems = getGameNames(doc, appIdList.get(j));
        photoUrlElems = getPhotoUrl(doc, appIdList.get(j));
        originalPriceElems = getOriginalPrice(doc, appIdList.get(j));
        //tags = get by crawling into more details of the game?
        releaseDateElems = getReleaseDates(doc, appIdList.get(j));//using multiple class selectors in css
        //operatingSystem =
        ratingElems = getRatings(doc, appIdList.get(j));
        */

        /*loops through every page on steam store to gather data*/
        for (int i = 1; i <= 5; i++) //get rid of hardcoding later. change to real number
        {
            System.out.println("in loop");
            String link = baseWebPage + String.valueOf(i);
            doc = connectToWebpage(link);

            appIdList = getAppId(doc);
            System.out.println(appIdList.toString());

            /*get all information for each appid(game) on the page*/
            for(int j = 0; j < appIdList.size(); j++)
            {
                /*get Elements objects for game*/
                gameNameElems = getGameNames(doc, appIdList.get(j));
                releaseDateElems = getReleaseDates(doc, appIdList.get(j));

                //String appId = appIdElems.attr("data-ds-appid");
                photoUrlElems = getPhotoUrl(doc, appIdList.get(j));
                //String url = photoUrlElems.attr("src");
                ratingElems = getRatings(doc, appIdList.get(j));
                String review = ratingElems.attr("data-store-tooltip");
                originalPriceElems = getOriginalPrice(doc, appIdList.get(j)); //fix method to have original price

            /*FIX THESE AND THEN ADD TO OBJECT ARRAY*/
                //System.out.println(priceElems);
                //System.out.println(photoUrlElems);
                //System.out.println(ratingElems);

            /*creates Game objects for all game information on page*/
                //createGameObjectsonPage(gameNameElems, releaseDateElems, appIdList, photoUrlElems, ratingElems);
            }
        }
    }

    /**
     * Method ConnectToWebsite takes the current webpage and loads the page into a Document object
     *
     * @param url Link used for HTTP Get request on website
     * @return html page stored in a Document object
     * @throws IOException
     */
    private static Document connectToWebpage(String url) throws IOException {
        Document doc = Jsoup.connect(url).timeout(0).get();
        return doc;
    }

    /**
     * Method getAppId gets the unique identifier for the games on the Steam webpage
     *
     * @param webpage Webpage of current url that contains information about steam games
     * @return Elements object that holds all appids on the webpage
     */
    private static ArrayList<String> getAppId(Document webpage) {
        Elements appId = webpage.select("#search_result_container a");
        Game currGame;
        currGame = new Game(null, null, null, null, null, null, null);
        String appIdString;

        ArrayList<String> ids = new ArrayList<String>();
        for (Element a : appId) {
            appIdString = a.attr("data-ds-appid");
            //System.out.println(a.attr("data-ds-appid"));
            ids.add(appIdString);
            currGame.setAppID(appIdString);
            steamStore.put(appIdString, currGame);
        }
        //System.out.println(ids.toString());
        return ids;
    }

    /**
     * Method getGameTitle takes the current webpage and obtains the title of the steam game
     *
     * @param webpage Webpage of current url that contains information about steam games
     * @param s
     * @return Elements objects that hold all game titles on the webpage
     */
    private static Elements getGameNames(Document webpage, String s) {
        //Game gameInfo = new Game();
        Elements titleElements = webpage.select("span.title");
        return titleElements;
    }

    /**
     * Method getReleaseDates gets the release dates of the game on the current webpage
     *
     * @param webpage Webpage of current url that contains information about steam games
     * @param s
     * @return Elements object that holds all game release dates on the webpage
     */
    private static Elements getReleaseDates(Document webpage, String s) {
        Elements dateElems = webpage.select("div.col.search_released.responsive_secondrow");
        return dateElems;
    }

    /**
     * Method getPhotoUrl gets the photoUrl of the game on the current webpage
     *
     * @param webpage Webpage of the current url that contains information about steam games
     * @param s
     * @return Elements object that holds all photoUrls of the game
     */
    private static Elements getPhotoUrl(Document webpage, String s) {
        Elements photoUrls = webpage.select("div.col.search_capsule img");
        return photoUrls;
    }

    /**
     * Method getRatings gets the rating of the game on the current webpage
     *
     * @param webpage Webpage of the current url that contains information about steam
     * @param s
     * @return Elements object that holds both positive and negative ratings of game
     */
    private static Elements getRatings(Document webpage, String s) {
        Elements ratings = webpage.select("div.col.search_reviewscore.responsive_secondrow span");
        return ratings;
    }

    /**
     * Method getOriginalPrice gets the price of the game on the current webpage without a sale
     *
     * @param webpage Webpage of the current url that contains information about steam
     * @param s
     * @return Elements object that holds both the sale price and original price
     */
    private static Elements getOriginalPrice(Document webpage, String s) {
        Elements price = webpage.select("div.col.search_price.responsive_secondrow");

        return price;
    }

    private static Elements getSalePrice(Document webpage) {
        return null;
    }
}
