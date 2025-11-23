package com.github.ASE.Closures;

import java.util.ArrayList;
import java.util.List;

public class FinancialAlertSystem {
    private List<AlertRule> rules = new ArrayList<>();

    public void addRule(AccountCondition condition, AccountAlertAction action) {
        rules.add(new AlertRule(condition, action));
    }

    public void check(Account account) {
        for (var rule : rules) {
            rule.check(account);
        }
    }
}
