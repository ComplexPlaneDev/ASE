class ProtocolInterface:
    def __init__(self, provider):
        self.provider = provider

    def initialize(self):
        raise NotImplementedError
    
    def send_email(self, message):
        raise NotImplementedError
    
    def shutdown(self):
        raise NotImplementedError