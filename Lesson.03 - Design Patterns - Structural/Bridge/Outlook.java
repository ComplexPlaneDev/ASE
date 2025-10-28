public class Outlook implements EmailProvider {
    @Override
    public void authenticate() {
        System.out.println("Outlook: Authenticating with Exchange credentials");
    }

    @Override
    public void processEmail(String emailData) {
        System.out.println("Outlook: Processing email through Microsoft Exchange");
    }

    @Override
    public void logout() {
        System.out.println("Outlook: Logging out from session");
    }
}
