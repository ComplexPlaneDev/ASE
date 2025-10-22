from providers.provider_interface import ProviderInterface

class YahooProvider(ProviderInterface):
    def authenticate(self):
        print("Authenticating with Yahoo API key")

    def process_email(self, email_data):
        print(f"Processing email through Yahoo Mail infrastructure")

    def logout(self):
        print("Yahoo: Logging out from session")
