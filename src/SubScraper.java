import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import jxl.write.WriteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class SubScraper obtains information from webpages that are subsections of the search page
 */
public class SubScraper extends Scraper
{
    public Scraper currScraper = new Scraper();

    /*Main method for subscraping*/
    public void beginSubScraping() throws IOException
    {
        scrapeOperatingSystems();
        scrapeTopSellers();
        scrapeUpcoming();
        scrapeSpecials();
    }

    private void scrapeOperatingSystems()
    {

    }

    private void scrapeTopSellers() throws IOException
    {
        String url = "http://store.steampowered.com/search/?filter=topsellers&os=win#sort_by=&sort_order=0&filter=topsellers&os=win&page=";

        Document topSellersDoc = currScraper.connectToWebpage(url.concat("1"));
        for(int i=0; i < 5; i++)
        {

        }
    }

    private  void scrapeUpcoming()
    {

    }

    private void scrapeSpecials()
    {

    }

}
