from protocols.protocol_interface import ProtocolInterface

class SMTPProtocol(ProtocolInterface):
    def initialize(self):
        print("SMTP: Initializing email protocol")

    def send_email(self, message: str):
        print(f"SMTP: Sending email: {message}")
        self.provider.process_email(message)

    def shutdown(self):
        print("SMTP: Shutting down protocol")
