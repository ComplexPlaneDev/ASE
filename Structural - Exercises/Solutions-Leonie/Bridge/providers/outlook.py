from providers.provider_interface import ProviderInterface

class OutlookProvider(ProviderInterface):
    def authenticate(self):
        print("Authenticating with Microsoft credentials")

    def process_email(self, email_data):
        print("Processing email through Microsoft infrastructure")

    def logout(self):
        print("Outlook: Logging out from session")
