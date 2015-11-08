import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jxl.write.WriteException;
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

    public static void main(String[] args) throws IOException, WriteException {
        /*information to be put into database*/
        Elements appIdElems, gameNameElems, photoUrlElems, tagsElems, releaseDateElems, operatingSystemElems,
                ratingElems, originalPriceElems, salePriceElems;

        ArrayList<String> appIdList = new ArrayList<String>();
        ArrayList<String> photoUrlList = new ArrayList<String>();
        ArrayList<String> ratingsList = new ArrayList<String>();
        ArrayList<String> releaseDateList = new ArrayList<String>();

        String baseWebPage = "http://store.steampowered.com/search/?sort_by=&sort_order=0&page=";
        /*storing HTTP response from steam website*/
        Document doc = Jsoup.connect("http://store.steampowered.com/search/").get();

        int lastPageNum = getLastPageNum(doc);

        /*loops through every page on steam store to gather data*/
        for (int i = 1; i <= 4  ; i++) //get rid of hardcoding later. change to real number
        {
            System.out.println("in loop");
            String link = baseWebPage + String.valueOf(i);
            doc = connectToWebpage(link);

            appIdList = getAppId(doc);
            appIdList.removeAll(Arrays.asList("", null));
            photoUrlList = getPhotoUrl(doc);
            releaseDateList = getReleaseDates(doc, "");
            ratingsList = getRatings(doc, "");
            //System.out.println(releaseDateList.toString());

            setGameNames(doc, appIdList);
            setPrice(doc, appIdList);
            setPhotoUrls(appIdList, photoUrlList);
            setReleaseDate(appIdList, releaseDateList);
            //System.out.println(ratingsList.toString());
            setRatings(appIdList, ratingsList);
        }

        /*view hashtable for testing*/

        for(String key : steamStore.keySet())
        {
            System.out.println("Key is:   " + key);
            System.out.println("Game name is: " + steamStore.get(key).getName());
            System.out.println("Appid:   " + steamStore.get(key).getAppID());
            System.out.println("Rating:   " + steamStore.get(key).getRating());
            System.out.println("Date:   " + steamStore.get(key).getDate());
            System.out.println("Original Price:   " + steamStore.get(key).getOriginalPrice());
            System.out.println("Sale Price:    " + steamStore.get(key).getSalePrice());
            //System.out.println("Game Url:    " + steamStore.get(key).getGameUrl());
            System.out.println("Photo Url:   " + steamStore.get(key).getPhotoUrl());
            //System.out.println("" + steamStore.get(key).);
        }


        new SteamWorkbook().writeStoreInfoDatabase();


    }

    /**
     * Gets the last page number in order to connect to all pages
     * @param webpage Document object containing the first page in this case
     * @return int representing the last page on the steam search app
     */
    private static int getLastPageNum(Document webpage)
    {
        int lastPage = 0;
        Element last = webpage.select("div.search_pagination_right").first();
        String[] parts = last.text().split(" ");
        int i;

        /*loops through botton page number in an array*/
        for(i = 0; i < parts.length; i++)
        {
            System.out.println("index is: " + i + " " + parts[i]);

        }

        String[] lastPageNum = parts[4].split("\\.");
        char[] lastPageNumArray = lastPageNum[3].toCharArray();
        String finalNum = "";

        /*removes unnecessary whitespace found when extracting the last page number*/
        for(i = 0; i < lastPageNumArray.length; i++)
        {
            /*if the character is a number, then add it to the finalNum String */
            if(lastPageNumArray[i] == '1' || lastPageNumArray[i] == '2' || lastPageNumArray[i] == '3' ||
                    lastPageNumArray[i] == '4' || lastPageNumArray[i] == '5' || lastPageNumArray[i] == '6'
                    || lastPageNumArray[i] == '7' || lastPageNumArray[i] == '8' || lastPageNumArray[i] == '9'
                    || lastPageNumArray[i] == '0')
            {
                finalNum = finalNum.concat(String.valueOf(lastPageNumArray[i]));
            }
        }

        /*converts finalNum string into an int representing the last page of the steam search*/
        lastPage = Integer.parseInt(finalNum);

        return lastPage;
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
        currGame = new Game();
        String appIdString;

        ArrayList<String> ids = new ArrayList<String>();
        for (Element a : appId) {
            appIdString = a.attr("data-ds-appid");
            ids.add(appIdString);
            currGame.setAppID(appIdString);
            steamStore.put(appIdString, currGame);
        }
        return ids;
    }

    /**
     * Method getGameTitle takes the current webpage and obtains the title of the steam game
     *
     * @param webpage Webpage of current url that contains information about steam games
     * @param appIds
     * @return Elements objects that hold all game titles on the webpage
     */
    private static void setGameNames(Document webpage, ArrayList<String> appIds) {
        //Game gameInfo = new Game();
        Elements titleElements = webpage.select("span.title");
        String game;
        int pointer = 0;
        String appid;
        Game currGame;

        /* loops through all games on the page*/
        for (Element e : titleElements) {
            appid = appIds.get(pointer);
            game = e.text();
            currGame = new Game();
            currGame.setAppID(appid);
            currGame.setName(game);
            currGame.setDate(steamStore.get(appid).getDate());
            currGame.setGameUrl(steamStore.get(appid).getGameUrl());
            currGame.setOriginalPrice(steamStore.get(appid).getOriginalPrice());
            currGame.setDiscount(steamStore.get(appid).getDiscount());
            currGame.setRating(steamStore.get(appid).getRating());
            currGame.setSalePrice(steamStore.get(appid).getSalePrice());
            steamStore.remove(appid);
            steamStore.put(appid, currGame);
            pointer++;
        }

    }

    /**
     * Method getReleaseDates gets the release dates of the game on the current webpage
     *
     * @param webpage Webpage of current url that contains information about steam games
     * @param s
     * @return Elements object that holds all game release dates on the webpage
     */
    private static ArrayList<String> getReleaseDates(Document webpage, String s) {
        Elements dateElems = webpage.select("div.col.search_released.responsive_secondrow");
        String date;
        ArrayList<String> output = new ArrayList<String>();

        for (Element e : dateElems) {
            date = e.text();
            output.add(date);
        }
        return output;
    }

    /**
     * Method getPhotoUrl gets the photoUrl of the game on the current webpage
     *
     * @param webpage Webpage of the current url that contains information about steam games
     * @return Elements object that holds all photoUrls of the game
     */
    private static ArrayList<String> getPhotoUrl(Document webpage) {
        Elements photoUrls = webpage.select("div.col.search_capsule img");
        String urlString;
        ArrayList<String> urls = new ArrayList<String>();

        for (Element e : photoUrls) {
            urlString = e.attr("src");
            urls.add(urlString);
        }
        return urls;
    }

    /**
     * Method getRatings gets the rating of the game on the current webpage
     *
     * @param webpage Webpage of the current url that contains information about steam
     * @param s
     * @return Elements object that holds both positive and negative ratings of game
     */
    private static ArrayList<String> getRatings(Document webpage, String s) {
        Elements ratings = webpage.select("div.col.search_reviewscore.responsive_secondrow span");
        Boolean isInitial = true;
        String finalRating = null;
        ArrayList<String> review = new ArrayList<String>();
        for (Element e : ratings) {
            if (isInitial == true) {
                isInitial = false;
                continue;
            } else {
                finalRating = e.attr("data-store-tooltip");
                finalRating = finalRating.replaceAll("<br>", " ");
                review.add(finalRating);
            }
        }
        return review;
    }

    /**
     * Method getOriginalPrice gets the price of the game on the current webpage without a sale
     *
     * @param webpage Webpage of the current url that contains information about steam
     * @param appids  List that stores the appids on the current page
     * @return Elements object that holds both the sale price and original price
     */
    private static void setPrice(Document webpage, ArrayList<String> appids) {
        Elements price = webpage.select("div.col.search_price.responsive_secondrow");
        ArrayList<String> output = new ArrayList<String>();
        String currPrice;
        int pointer = 0;
        String currAppid;
        Game currGame;

        /*Goes through all prices on web pages*/
        for (Element e : price) {
            currGame = new Game();
            currPrice = e.text();
            currAppid = appids.get(pointer);
            currGame.setAppID(currAppid);
            currGame.setName(steamStore.get(currAppid).getName());
            currGame.setRating(steamStore.get(currAppid).getRating());
            currGame.setGameUrl(steamStore.get(currAppid).getGameUrl());
            currGame.setDate(steamStore.get(currAppid).getDate());
            currGame.setPhotoUrl(steamStore.get(currAppid).getPhotoUrl());

            if (currPrice.equalsIgnoreCase("Free To Play")) //checks if price is labeled as Free-To-Play
            {
                currGame.setSalePrice("0.00");
                currGame.setOriginalPrice("0.00");
                steamStore.remove(currAppid);
                //ADD SALE PERCENT FIELD HERE. NEED TO ADD TO GAME CLASS
                steamStore.put(currAppid, currGame);
            } else //checks other games that are not "Free-To-Play"
            {
                String parts[] = currPrice.split(" ");
                if (parts.length == 1)//no sale if one price is listed
                {
                    currGame.setSalePrice(parts[0].replace("$", ""));
                    currGame.setOriginalPrice(parts[0].replace("$", ""));
                    steamStore.remove(currAppid);
                    steamStore.put(currAppid, currGame);

                } else if (parts.length == 2)//sale price and original price stored at 0 and 1
                {
                    currGame.setSalePrice(parts[1].replace("$", ""));
                    currGame.setOriginalPrice(parts[0].replace("$", ""));
                    steamStore.remove(currAppid);
                    steamStore.put(currAppid, currGame);
                } else {
                    System.out.println("Error: Cannot have more than the orginal or sale price");
                }
            }
            pointer++;
        }//end of for loop
    }

    /**
     * Sets the photoUrl in the appropriate object in the hashtable
     * @param appids
     * @param urls
     */
    private static void setPhotoUrls(ArrayList<String> appids, ArrayList<String> urls)
    {
        Game currGame;
        String url;
        String appid;

        /*goes through list of urls and adds them to global hashtable (steamStore)*/
        for(int i = 0; i < urls.size(); i++)
        {
            currGame = new Game();
            appid = appids.get(i);
            url = urls.get(i);
            currGame.setAppID(appid);
            currGame.setPhotoUrl(url);
            currGame.setDate(steamStore.get(appid).getDate());
            currGame.setRating(steamStore.get(appid).getRating());
            currGame.setGameUrl(steamStore.get(appid).getGameUrl());
            currGame.setRating(steamStore.get(appid).getRating());
            currGame.setName(steamStore.get(appid).getName());
            currGame.setOriginalPrice(steamStore.get(appid).getOriginalPrice());
            currGame.setSalePrice(steamStore.get(appid).getSalePrice());
            steamStore.remove(appid);
            steamStore.put(appid, currGame);
        }
    }

    private static void setReleaseDate(ArrayList<String> appids, ArrayList<String> releaseDate)
    {
        Game currGame;
        String date;
        String appid;

        /*goes through list of urls and adds them to global hashtable (steamStore)*/
        for(int i = 0; i < releaseDate.size(); i++)
        {
            currGame = new Game();
            appid = appids.get(i);
            date = releaseDate.get(i);
            currGame.setAppID(appid);
            currGame.setPhotoUrl(steamStore.get(appid).getPhotoUrl());
            currGame.setDate(date);
            currGame.setRating(steamStore.get(appid).getRating());
            currGame.setGameUrl(steamStore.get(appid).getGameUrl());
            currGame.setRating(steamStore.get(appid).getRating());
            currGame.setName(steamStore.get(appid).getName());
            currGame.setOriginalPrice(steamStore.get(appid).getOriginalPrice());
            currGame.setSalePrice(steamStore.get(appid).getSalePrice());
            steamStore.remove(appid);
            steamStore.put(appid, currGame);
        }
    }

    private static void setRatings(ArrayList<String> appids, ArrayList<String> ratings)
    {
        Game currGame;
        String rating;
        String appid;

        /*goes through list of urls and adds them to global hashtable (steamStore)*/
        for(int i = 0; i < ratings.size(); i++)
        {
            currGame = new Game();
            appid = appids.get(i);
            rating = ratings.get(i);
            //System.out.println(rating);
            currGame.setAppID(appid);
            currGame.setPhotoUrl(steamStore.get(appid).getPhotoUrl());
            currGame.setDate(steamStore.get(appid).getDate());
            currGame.setRating(rating);
            currGame.setGameUrl(steamStore.get(appid).getGameUrl());
            currGame.setName(steamStore.get(appid).getName());
            currGame.setOriginalPrice(steamStore.get(appid).getOriginalPrice());
            currGame.setSalePrice(steamStore.get(appid).getSalePrice());
            steamStore.remove(appid);
            steamStore.put(appid, currGame);
        }
    }

    public static HashMap<String, Game> getSteamStore()
    {
        return steamStore;
    }

}
