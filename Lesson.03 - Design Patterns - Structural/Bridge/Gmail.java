public class Gmail implements EmailProvider {
    @Override
    public void authenticate() {
        System.out.println("Gmail: Authenticating with OAuth tokens");
    }

    @Override
    public void processEmail(String emailData) {
        System.out.println("Gmail: Processing email through Google infrastructure");
    }

    @Override
    public void logout() {
        System.out.println("Gmail: Logging out from session");
    }
}
