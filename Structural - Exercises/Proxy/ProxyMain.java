import java.util.Map;
import java.util.HashMap;

public class ProxyMain {
    public static void main(String[] args) {

        // Create files
        Map<String, File> fileSystem = new HashMap<>();
        fileSystem.put("public_memo.txt", new File("public_memo.txt", "This is a public memo.", SecurityLevel.PUBLIC));
        fileSystem.put("internal_report.txt", new File("internal_report.txt", "Q3 sales figures are up by 15%.", SecurityLevel.INTERNAL));
        fileSystem.put("project_phoenix.spec", new File("project_phoenix.spec", "Project Phoenix uses a quantum computer.", SecurityLevel.CONFIDENTIAL));
        fileSystem.put("secret_formula.txt", new File("secret_formula.txt", "The secret ingredient is sugar and vegan butter.", SecurityLevel.SECRET));

        // Create employees
        Employee intern = new Employee("intern-007", SecurityLevel.INTERNAL);
        Employee manager = new Employee("manager-01", SecurityLevel.CONFIDENTIAL);
        Employee director = new Employee("director-evans", SecurityLevel.SECRET);

        // The client interacts only with the proxy
        FileAccessor fileAccessor = new SecureFileProxy();

        System.out.println("--- Starting File Access Tests ---\n");

 

        // Test 1: Successful
        System.out.println("Test Case 1: Intern accessing an internal report");
        String content1 = fileAccessor.readFile(fileSystem.get("internal_report.txt"), intern);
        System.out.println("File Content: " + content1 + "\n");

        // Test 2: Access denial
        System.out.println("Test Case 2: Intern trying to access a secret file");
        String content2 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), intern);
        System.out.println("File Content: " + content2 + "\n");
        
        // Test 3: Sucessful
        System.out.println("Test Case 3: Manager accessing a confidential spec");
        String content3 = fileAccessor.readFile(fileSystem.get("project_phoenix.spec"), manager);
        System.out.println("File Content: " + content3 + "\n");
        
        // Test 4: Access denied
        System.out.println("Test Case 4: Manager trying to access a secret file");
        String content4 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), manager);
        System.out.println("File Content: " + content4 + "\n");

        // Test 5: sucessful
        System.out.println("Test Case 5: Director accessing the secret file");
        String content5 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), director);
        System.out.println("File Content: " + content5 + "\n");
    }
}
