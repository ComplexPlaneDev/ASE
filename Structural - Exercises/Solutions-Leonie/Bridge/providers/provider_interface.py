class ProviderInterface:
    def authenticate(self):
        raise NotImplementedError
    
    def process_email(self, email_data):
        raise NotImplementedError
    
    def logout(self):
        raise NotImplementedError