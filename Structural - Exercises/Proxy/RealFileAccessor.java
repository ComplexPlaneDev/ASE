public class RealFileAccessor implements FileAccessor {
    @Override
    public String readFile(File file, Employee employee) {
        System.out.println("Access granted to " + employee.getId() + " for file: " + file.getName());
        return file.getContent();
    }
    
}
