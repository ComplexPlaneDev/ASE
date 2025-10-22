import java.util.Map;
import java.io.File;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Create files with different security levels
        Map<String, File> fileSystem = new HashMap<>();
        fileSystem.put("public_memo.txt", new File("public_memo.txt", "This is a public memo.", SecurityLevel.PUBLIC));
        fileSystem.put("internal_report.txt", new File("internal_report.txt", "Q3 sales figures are up by 15%.", SecurityLevel.INTERNAL));
        fileSystem.put("project_phoenix.spec", new File("project_phoenix.spec", "Project Phoenix uses a quantum entanglement core.", SecurityLevel.CONFIDENTIAL));
        fileSystem.put("secret_formula.txt", new File("secret_formula.txt", "The secret ingredient is love... and a bit of paprika.", SecurityLevel.SECRET));

        // Create employees with different clearance levels
        Employee intern = new Employee("intern-007", SecurityLevel.INTERNAL);
        Employee manager = new Employee("manager-01", SecurityLevel.CONFIDENTIAL);
        Employee director = new Employee("director-evans", SecurityLevel.SECRET);

        // The client interacts only with the proxy
        FileAccessor fileAccessor = new SecureFileProxy();

        System.out.println("--- Starting File Access Tests ---\n");

 

        // Test 1: Successful access by authorized employee
        System.out.println("--- Test Case 1: Intern accessing an internal report (Should Succeed) ---");
        String content1 = fileAccessor.readFile(fileSystem.get("internal_report.txt"), intern);
        System.out.println("File Content: " + content1 + "\n");

        // Test 2: Access denial for employee with insufficient clearance
        System.out.println("--- Test Case 2: Intern trying to access a secret file (Should Fail) ---");
        String content2 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), intern);
        System.out.println("File Content: " + content2 + "\n");
        
        // Test 3: Manager accessing a confidential file (Should Succeed)
        System.out.println("--- Test Case 3: Manager accessing a confidential spec (Should Succeed) ---");
        String content3 = fileAccessor.readFile(fileSystem.get("project_phoenix.spec"), manager);
        System.out.println("File Content: " + content3 + "\n");
        
        // Test 4: Manager trying to access a secret file (Should Fail)
        System.out.println("--- Test Case 4: Manager trying to access a secret file (Should Fail) ---");
        String content4 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), manager);
        System.out.println("File Content: " + content4 + "\n");

        // Test 5: Director accessing the secret file (Should Succeed)
        System.out.println("--- Test Case 5: Director accessing the secret file (Should Succeed) ---");
        String content5 = fileAccessor.readFile(fileSystem.get("secret_formula.txt"), director);
        System.out.println("File Content: " + content5 + "\n");
    }
}
