public class Main {
    public static void main(String[] args) {

        System.out.println("Setting up SMTP with Gmail...");
        EmailProtocol smtp = new SMTP();
        EmailProvider gmail = new Gmail();
        EmailService emailService1 = new EmailClient(smtp, gmail);
        emailService1.execute("Hello from SMTP!");

        System.out.println("\nSetting up IMAP with Outlook...");
        EmailProtocol imap = new IMAP();
        EmailProvider outlook = new Outlook();
        EmailService emailService2 = new EmailClient(imap, outlook);
        emailService2.execute("Hello from IMAP!");
    }
}
