#!/usr/bin/env ts-node

export {};

interface User {
    name: string;
    balance: number;
}

const users = new Map<number, User>([
    [1, { name: "Alice", balance: 200 }],
    [2, { name: "Bob", balance: -50 }],
    [3, { name: "Trudy", balance: 3 }]
]);

class Optional<T> {
    private constructor(
        private value: T | null
    ) {}

    static Some<T>(value: T) {
        return new Optional(value);
    }

    static None<T>() {
        return new Optional<T>(null);
    }

    public isSome(): boolean {
        return this.value !== null;
    }

    public isNone(): boolean {
        return this.value === null;
    }

    public map<U>(fn: (val: T) => U): Optional<U> {
        return this.isSome()
            ? Optional.Some(fn(this.value as T))
            : Optional.None<U>();
    }

    public andThen<U>(fn: (val: T) => Optional<U>): Optional<U> {
        return this.value !== null
            ? fn(this.value)
            : Optional.None<U>();
    }

    public unwrapOr(defaultValue: T): T {
        return this.value !== null
            ? this.value
            : defaultValue;
    }
}

class Result<T, E> {
    private constructor(
        private readonly value?: T,
        private readonly error?: E
    ) {}

    static Ok<T>(value: T): Result<T, never> {
        return new Result(value);
    }

    static Err<E>(error: E): Result<never, E> {
        return new Result(undefined, error) as Result<never, E>;
    }

    public isOk(): this is Result<T, never> {
        return this.error === undefined;
    }

    public isErr(): this is Result<never, E> {
        return this.value === undefined;
    }

    public map<U>(fn: (val: T) => U): Result<U, E> {
        return this.isOk()
            ? Result.Ok(fn(this.value as T))
            : Result.Err(this.error as E);
    }

    public andThen<U>(fn: (val: T) => Result<U, E>): Result<U, E> {
        return this.isOk()
            ? fn(this.value as T)
            : Result.Err(this.error as E);
    }

    public unwrapOrError(fn: (error: E) => T): T {
        return this.isOk()
            ? this.value!
            : fn(this.error!);
    }
}

function findUser(userId: number): Optional<User> {
    return users.has(userId)
        ? Optional.Some(users.get(userId)!)
        : Optional.None();
}

function getUserBalance(user: User): Result<number, string> {
    return user.balance >= 0
        ? Result.Ok(user.balance)
        : Result.Err("Invalid balance");
}

function applyBonus(balance: number): Result<number, string> {
    return balance > 0
        ? Result.Ok(balance * 1.10)
        : Result.Err("Balance too low");
}

let a = findUser(1);
let b = a.map(getUserBalance);
let c = b.unwrapOr(Result.Err("User not found"));
let d = c.andThen(applyBonus);
let e = d.map((finalBalance) => `New Balance: ${finalBalance}`);
let f = e.unwrapOrError((err) => err as string);

a = findUser(10);
b = a.map(getUserBalance);
c = b.unwrapOr(Result.Err("User not found"));
d = c.andThen(applyBonus);
e = d.map((finalBalance) => `New Balance: ${finalBalance}`);
f = e.unwrapOrError((err) => err as string);


console.log(findUser(1)
    .map(getUserBalance)
    .unwrapOr(Result.Err("User not found"))
    .andThen(applyBonus)
    .map((finalBalance) => `New Balance: ${finalBalance}`)
    .unwrapOrError((err) => err as string));
