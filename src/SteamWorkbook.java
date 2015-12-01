import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import com.sun.corba.se.spi.ior.Writeable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 */
public class SteamWorkbook extends Scraper
{
    public Scraper currScraper = new Scraper();

    /**
     * Gets the most recent steam store information
     *
     * @return
     */
    private HashMap<String, Game> getCurrSteamStore()
    {
        HashMap<String, Game> currStore;
        currStore = currScraper.getSteamStore();
        return currStore;
    }

    public void writeStoreInfoDatabase() throws IOException, WriteException
    {
        HashMap<String, Game> store = getCurrSteamStore();

        File output = new File("steamDatabase.xls");
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(output);

        WritableSheet sheet = writableWorkbook.createSheet("Sheet1", 0);

        /*creating Labels for top of excel sheet*/
        Label appIdLabel = new Label(0, 0, "App Id");
        Label gameNameLabel = new Label(1, 0, "Game Name");
        Label releaseDateLabel = new Label(2, 0, "Release Date");
        Label discountLabel = new Label(3, 0, "Discount");
        Label originalPriceLabel = new Label(4, 0, "Original Price");
        Label salePriceLabel = new Label(5, 0, "Sale Price");
        Label photoUrlLabel = new Label(6, 0, "Photo URL");

        sheet.addCell(appIdLabel);
        sheet.addCell(gameNameLabel);
        sheet.addCell(releaseDateLabel);
        sheet.addCell(discountLabel);
        sheet.addCell(originalPriceLabel);
        sheet.addCell(salePriceLabel);
        sheet.addCell(photoUrlLabel);

        int col = 0;
        int row = 1;

        for (String key : steamStore.keySet())
        {
            String appid = store.get(key).getAppID();
            String name = store.get(key).getName();
            String date = store.get(key).getDate();
            String discount = store.get(key).getDiscount();
            String originalPrice = store.get(key).getOriginalPrice();
            String salePrice = store.get(key).getSalePrice();
            String photoURL = store.get(key).getPhotoUrl();

            Label appIdCell = new Label(col, row, appid);
            Label nameCell = new Label(col + 1, row, name);
            Label dateCell = new Label(col + 2, row, date);
            Label discountCell = new Label(col + 3, row, discount);
            Label originalPriceCell = new Label(col + 4, row, originalPrice);
            Label salePriceCell = new Label(col + 5, row, salePrice);
            Label photoURLCell = new Label(col + 6, row, photoURL);

            sheet.addCell(appIdCell);
            sheet.addCell(nameCell);
            sheet.addCell(dateCell);
            sheet.addCell(discountCell);
            sheet.addCell(originalPriceCell);
            sheet.addCell(salePriceCell);
            sheet.addCell(photoURLCell);

            row++;
        }

        sheet.removeRow(1);
        writableWorkbook.write();
        writableWorkbook.close();

    }


} //end of Workbook class
