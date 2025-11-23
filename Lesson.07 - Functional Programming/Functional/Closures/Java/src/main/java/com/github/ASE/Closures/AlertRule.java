package com.github.ASE.Closures;

public class AlertRule {
    private final AccountCondition condition;
    private final AccountAlertAction action;

    public AlertRule(AccountCondition condition, AccountAlertAction action) {
        this.condition = condition;
        this.action = action;
    }

    public void check(Account account) {
        if (condition.test(account)) {
            action.trigger(account, account.getBalance());
        }
    }
}
