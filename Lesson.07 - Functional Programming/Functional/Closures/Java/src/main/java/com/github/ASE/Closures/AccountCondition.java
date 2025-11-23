package com.github.ASE.Closures;

@FunctionalInterface
interface AccountCondition {
    boolean test(Account account);
}
