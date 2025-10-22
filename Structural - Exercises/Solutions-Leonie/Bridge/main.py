from protocols.smtp import SMTPProtocol
from protocols.imap import IMAPProtocol
from protocols.pop3 import POP3Protocol
from providers.gmail import GmailProvider
from providers.outlook import OutlookProvider
from providers.yahoo import YahooProvider

def main():
    # example 1: SMTP with Gmail
    print("\nSetting up SMTP with Gmail...")
    gmail = GmailProvider()
    smtp = SMTPProtocol(gmail)

    gmail.authenticate()
    smtp.initialize()
    smtp.send_email("This message is sent via Gmail using SMTP!")
    smtp.shutdown()
    gmail.logout()

    # example 2: IMAP with Outlook
    print("\nSetting up IMAP with Outlook...")
    outlook = OutlookProvider()
    imap = IMAPProtocol(outlook)

    outlook.authenticate()
    imap.initialize()
    imap.send_email("This message is sent via Outlook using IMAP!")
    imap.shutdown()
    outlook.logout()


    # example 3: POP3 with Yahoo
    print("\nSetting up POP3 with Yahoo...")
    yahoo = YahooProvider()
    pop3 = POP3Protocol(yahoo)

    yahoo.authenticate()
    pop3.initialize()
    pop3.send_email("This message is sent via Yahoo using POP3!")
    pop3.shutdown()
    yahoo.logout()

if __name__ == "__main__":
    main()
