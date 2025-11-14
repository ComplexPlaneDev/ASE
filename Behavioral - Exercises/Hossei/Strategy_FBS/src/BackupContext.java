public class BackupContext {
    private BackupStrategy strategy;

    public void setStrategy(BackupStrategy strategy){
        this.strategy = strategy;
    }
    public void executeBackup(String[] files){
        if(strategy == null){
            System.out.println("No strategy selected");
            return;
        }
        strategy.backup(files);
    }
}
