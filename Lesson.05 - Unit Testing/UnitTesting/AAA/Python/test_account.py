import unittest
from account import BankAccount

class TestBankAccount(unittest.TestCase):

    def setUp(self):
        # This method is called before each test
        print("Setting up...")
        self.account = BankAccount(100)  # Starting balance of 100

    def tearDown(self):
        # This method is called after each test
        print("Tearing down...")
        del self.account

    def test_initial_balance(self):
        self.assertEqual(self.account.balance, 100)

    def test_deposit(self):
        self.account.deposit(50)
        self.assertEqual(self.account.balance, 150)

    def test_withdraw(self):
        self.account.withdraw(30)
        self.assertEqual(self.account.balance, 70)

    def test_withdraw_insufficient_funds(self):
        with self.assertRaises(ValueError):
            self.account.withdraw(200)

if __name__ == '__main__':
    unittest.main()
