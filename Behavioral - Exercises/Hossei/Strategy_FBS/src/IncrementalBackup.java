public class IncrementalBackup implements BackupStrategy {
    @Override
    public void backup(String[] files){
        System.out.println("Incremental Backup...");
        for(String file: files){
            System.out.println("Backing up changed files: " + file);
        }
        System.out.println("Only modified files backed up \n");
    }
}
