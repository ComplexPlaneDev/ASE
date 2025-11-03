# Boolean Expression Evaluator

## Problem Statement

You are tasked with building a boolean expression evaluator that can process boolean expressions with multiple operators. The system needs to parse text-based boolean expressions and evaluate them according to operator precedence rules.

## Expression Components

The system must support the following elements:

1. **Boolean Constants**:
   - `TRUE` - represents the boolean value true
   - `FALSE` - represents the boolean value false

2. **Boolean Variable**:
   - `VAR` - a boolean variable whose value is provided as input to the evaluator

3. **Logical Operators** (in order of precedence, highest to lowest):
   - `NOT` - logical negation (highest precedence)
   - `AND` - logical conjunction (medium precedence)
   - `OR` - logical disjunction (lowest precedence)

## Expression Rules

- Expressions are evaluated according to operator precedence
- `NOT` has the highest precedence and is evaluated first
- `AND` has higher precedence than `OR`
- Operators of the same precedence are evaluated left to right
- No parentheses are used - precedence is determined solely by operator priority
- All operators and constants are case-insensitive

## Requirements

Your system must be able to:

1. Parse boolean expressions with multiple operators
2. Build an internal tree representation respecting operator precedence
3. Evaluate expressions by traversing the tree structure recursively
4. Handle the boolean variable `VAR` with a value provided at evaluation time
5. Support multiple operators in a single expression
6. Provide clear error messages for malformed expressions

## Sample Input/Output

```
Input: "TRUE"
VAR = true
Output: "Result: TRUE"

Input: "VAR AND TRUE"
VAR = true
Output: "Result: TRUE"

Input: "NOT VAR OR FALSE"
VAR = false
Output: "Result: TRUE"
(Evaluation: NOT VAR = NOT false = true, then true OR false = true)

Input: "VAR AND TRUE OR NOT VAR AND FALSE"
VAR = true
Output: "Result: TRUE"
(Evaluation: (VAR AND TRUE) OR (NOT VAR AND FALSE) = (true AND true) OR (false AND false) = true OR false = true)

Input: "NOT VAR AND TRUE OR FALSE AND VAR"
VAR = false
Output: "Result: TRUE"
(Evaluation: (NOT VAR AND TRUE) OR (FALSE AND VAR) = (true AND true) OR (false AND false) = true OR false = true)
```

## Operator Precedence Examples

- `NOT VAR AND TRUE` evaluates as `(NOT VAR) AND TRUE`
- `VAR AND TRUE OR FALSE` evaluates as `(VAR AND TRUE) OR FALSE`
- `NOT VAR OR TRUE AND FALSE` evaluates as `(NOT VAR) OR (TRUE AND FALSE)`
