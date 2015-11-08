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
public class SteamWorkbook extends Scraper{
    public Scraper currScraper = new Scraper();

    /**
     * Gets the most recent steam store information
     * @return
     */
    private HashMap<String, Game> getCurrSteamStore() {
        HashMap<String, Game> currStore;
        currStore = currScraper.getSteamStore();
        return currStore;
    }

    public void writeStoreInfoDatabase() throws IOException, WriteException {
        HashMap<String, Game> store = getCurrSteamStore();
        File output = new File("steamDatabase.xls");
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(output);

        WritableSheet sheet = writableWorkbook.createSheet("Sheet1", 0);

        //column, row
        Label appIdLabel = new Label(0, 0, "App Id");
        Label gameNameLabel = new Label(1, 0, "Game Name");
        Label releaseDateLabel = new Label(2, 0, "Release Date");
        Label discountLabel = new Label(3, 0, "Discount");
        Label originalPriceLabel = new Label(4,0, "Original Price");
        Label salePriceLabel = new Label(5, 0, "Sale Price");
        Label photoUrlLabel = new Label(6, 0, "Photo URL");

        sheet.addCell(appIdLabel);
        sheet.addCell(gameNameLabel);
        sheet.addCell(releaseDateLabel);
        sheet.addCell(discountLabel);
        sheet.addCell(originalPriceLabel);
        sheet.addCell(salePriceLabel);
        sheet.addCell(photoUrlLabel);

        writableWorkbook.write();
        writableWorkbook.close();

    }





} //end of Workbook class
