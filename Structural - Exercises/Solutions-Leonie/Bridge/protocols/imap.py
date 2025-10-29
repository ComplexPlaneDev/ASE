from protocols.protocol_interface import ProtocolInterface

class IMAPProtocol(ProtocolInterface):
    def initialize(self):
        print("IMAP: Initializing email protocol")

    def send_email(self, message: str):
        print(f"IMAP: Sending email: {message}")
        self.provider.process_email(message)

    def shutdown(self):
        print("IMAP: Shutting down protocol")
