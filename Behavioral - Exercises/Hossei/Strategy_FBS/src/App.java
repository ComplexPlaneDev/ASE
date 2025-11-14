import java.util.Scanner;

public class App {
    public static void main(String[] args){
        String[] files = {"file1.txt", "file2.txt", "file65.txt", "file100.txt", "file30.txt" };
        BackupContext context = new BackupContext();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter backup type (f for full /i for incremental /d for differential): ");
        String type = scanner.nextLine().toLowerCase();

        switch(type){
            case "f" -> context.setStrategy(new FullBackup());
            case "i" -> context.setStrategy(new IncrementalBackup());
            case "d" -> context.setStrategy(new DifferentialBackup());
            default -> {
                System.out.println("Invalid choice");
                return;
            }
        }
        context.executeBackup(files);
    }
}
