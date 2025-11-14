public class DifferentialBackup implements BackupStrategy {
    @Override
    public void backup(String[] files){
        System.out.println("Differentail backup...");
        for(String file: files){
            System.out.println("Backing up files after last backup: "+ file);
        }
        System.out.println("Backup for untracked files completed.\n");
    }
    
}
