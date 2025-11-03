public class FullBackup implements BackupStrategy{
    @Override
    public void backup(String[] files){
        System.out.println("Full Backup...");
        for(String file: files){
            System.out.println("Backing up: " + file);
        }
        System.out.println("Backup Complete.\n");
    }
}
