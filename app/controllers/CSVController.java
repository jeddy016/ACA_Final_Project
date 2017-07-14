package controllers;

import models.CompletedService;
import models.CompletedServiceDetail;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVController
{
    public static void writeCSVFile(String csvFileName, List<CompletedServiceDetail> serviceList) {
        ICsvBeanWriter beanWriter = null;
        CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), // Service Name
                new NotNull(), //date
                new ParseBigDecimal(), // total cost;
                new ParseBigDecimal(), // parts cost;
                new ParseBigDecimal(), // labor cost;
                new NotNull() // shop
        };

        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
                    CsvPreference.STANDARD_PREFERENCE);
            String[] header = {"name", "date", "totalCost", "partsCost", "laborCost", "shop"};
            beanWriter.writeHeader(header);

            for (CompletedServiceDetail service : serviceList) {
                beanWriter.write(service, header, processors);
            }

        } catch (IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }
}
