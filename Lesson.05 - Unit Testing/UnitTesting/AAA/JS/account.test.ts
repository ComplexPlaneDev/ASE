import { expect } from 'chai';
import { BankAccount } from './account';

describe('BankAccount', () => {
    let account: BankAccount;

    beforeEach(() => {
        console.log('Setting up...');
        account = new BankAccount(100); // Initial balance of 100
    });

    afterEach(() => {
        console.log('Tearing down...');
        account = null as any; // Cleanup
    });

    it('should have correct initial balance', () => {
        expect(account.balance).to.equal(100);
    });

    it('should increase balance after deposit', () => {
        account.deposit(50);
        expect(account.balance).to.equal(150);
    });

    it('should decrease balance after withdrawal', () => {
        account.withdraw(30);
        expect(account.balance).to.equal(70);
    });

    it('should throw error on insufficient funds', () => {
        expect(() => account.withdraw(200)).to.throw('Insufficient funds');
    });

    it('should not allow negative deposit', () => {
        expect(() => account.deposit(-10)).to.throw('Deposit amount must be positive');
    });
});
