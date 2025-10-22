from providers.provider_interface import ProviderInterface

class GmailProvider(ProviderInterface):
    def authenticate(self):
        print("Gmail: Authenticating with Google tokens")

    def process_email(self, email_data):
        print(f"Processing email through Google infrastructure")

    def logout(self):
        print("Gmail: Logging out from session")
