from protocols.protocol_interface import ProtocolInterface

class POP3Protocol(ProtocolInterface):
    def initialize(self):
        print("POP3: Initializing email protocol")

    def send_email(self, message: str):
        print(f"POP3: Sending email: {message}")
        self.provider.process_email(message)

    def shutdown(self):
        print("POP3: Shutting down protocol")
