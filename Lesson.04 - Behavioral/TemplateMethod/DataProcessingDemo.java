/**
 * DataProcessingDemo — runs three format-specific processors
 * through the same ETL template method.
 */
public class DataProcessingDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║  Template Method Pattern — ETL Data Processing       ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        DataProcessor[] processors = {
            new CSVDataProcessor(3),
            new JSONDataProcessor("https://api.example.com/records"),
            new XMLDataProcessor(),
        };

        String[] sources = {
            "sales_2026.csv",
            "https://api.example.com/records",
            "catalog.xml",
        };

        for (int i = 0; i < processors.length; i++) {
            processors[i].process(sources[i]);
        }
    }
}
