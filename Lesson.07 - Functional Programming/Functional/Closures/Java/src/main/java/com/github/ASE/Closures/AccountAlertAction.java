package com.github.ASE.Closures;

@FunctionalInterface
interface AccountAlertAction {
    void trigger(Account account, double amount);
}
