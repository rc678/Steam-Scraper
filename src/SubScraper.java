import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
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
    public void beginSubScraping() throws IOException, WriteException {
        //scrapeOperatingSystems();
        scrapeTopSellers();
        //scrapeUpcoming();
        //scrapeSpecials();
    }

    private void scrapeOperatingSystems()
    {

    }

    private void scrapeTopSellers() throws IOException, WriteException {
        File output = new File("top98Games.xls");
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(output);
        WritableSheet sheet = writableWorkbook.createSheet("Sheet1", 0);

        Document topSellersDoc = Jsoup.connect("http://store.steampowered.com/stats/").get();

        ArrayList<String> ids = new ArrayList<String>();

        Elements appId = topSellersDoc.select("a.gameLink");
        String appIdString;

        Label appIdLabel = new Label(0, 0, "App Id");
        Label currPlayers = new Label(1, 0, "Current Players");
        Label peakPlayers = new Label(2, 0, "Peak Players Today");
        sheet.addCell(appIdLabel);
        sheet.addCell(currPlayers);
        sheet.addCell(peakPlayers);

        int count = 1;
        /*getting appids of top 98 games*/
        for (Element a : appId)
            {
                String link = a.attr("href").toString();
                String appID = link.replace("http://store.steampowered.com/app/", "");
                appID = appID.replaceAll("/", "");
                Label appIdCell = new Label(0, count, appID);
                sheet.addCell(appIdCell);
                //System.out.println(appID);
                count++;
            }

        Elements currentPlayers = topSellersDoc.select("tr.player_count_row");

        count = 1;
        for(Element servers: currentPlayers)
        {
            String[] row = servers.text().split(" ");
            //System.out.println(servers.text());
            //System.out.println(row[0]);
            String currPlayer = row[0];
            String peak = row[1];
            Label currPlayerCell = new Label(1, count, currPlayer);
            Label peakCell = new Label(2, count, peak);
            sheet.addCell(currPlayerCell);
            sheet.addCell(peakCell);
            count++;
        }

        writableWorkbook.write();
        writableWorkbook.close();

    }

    private  void scrapeUpcoming()
    {

    }

    private void scrapeSpecials()
    {

    }

    private void writeTopSellersToWorkBook(ArrayList<String> appids) throws IOException, WriteException {
        System.out.println("sfflkjf;lsdfksfd;mlsdf");
        File output = new File("steamTopSellers.xls");
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(output);

        WritableSheet sheet = writableWorkbook.createSheet("Sheet1", 0);

        /*creating Labels for top of excel sheet*/
        Label appIdLabel = new Label(0, 0, "App Id"); //col, row
        sheet.addCell(appIdLabel);

        for(int i = 1; i < appids.size(); i++)
        {
            System.out.println(i);
            String appid = appids.get(i);
            Label appIdCell = new Label(0, i, appid);
            sheet.addCell(appIdCell);
        }

        writableWorkbook.write();
        writableWorkbook.close();

    }

}
