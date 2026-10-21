/**
 * RomanContext
 *
 * Holds the mutable state shared across all expressions in the chain:
 *  - input  : the remaining integer value to be converted
 *  - output : the Roman numeral string built up so far
 *
 * Each RomanExpression reads from input, appends to output,
 * and reduces input by the amount it consumed.
 */
public class RomanContext {

    private int    input;
    private String output = "";

    public RomanContext(int number) {
        if (number <= 0 || number > 3999)
            throw new IllegalArgumentException("Roman numerals support 1–3999 only");
        this.input = number;
    }

    public int    getInput()              { return input;           }
    public void   setInput(int input)     { this.input = input;     }
    public String getOutput()             { return output;          }
    public void   appendOutput(String s)  { this.output += s;       }
}
