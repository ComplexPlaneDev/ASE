# Advanced Software Engineering Exam

The exam topic is the design, development and testing of a function plotter software, MathPlot.

## Content

The student should implement the following features:

- The user inputs a AOS or RPN formatted expression, named as *function*. [RPN](https://en.wikipedia.org/wiki/Reverse_Polish_notation)
- The software should be able to calculate the first-order derivative of the expression passed as input. [Tables of Derivatives](https://en.wikibooks.org/wiki/Calculus/Tables_of_Derivatives)
- The software should be able to print (as a pure text) the *function* back to the screen both in AOS and RPN format, regardless of the selected input format.
- The software should be able to plot both *function* and its first-derivative order in cartesian and polar coordinates (so, two plots per plot type). The domain range can be chosen arbitrarly, as well the plot step. [Polar coordinate system](https://en.wikipedia.org/wiki/Polar_coordinate_system)
- The software should be able to calculate the area below the curve related to the *function* between an arbitrary range with an arbitrary step, both with the Rectangular and Trapezoidal method. [Area under a curve](https://mathspace.co/textbooks/syllabuses/Syllabus-1059/topics/Topic-20588/subtopics/Subtopic-268703/?activeTab=theory)
- The software should be able to simplify both *function* and its first-order derivative.
- The code already provides a Facade class (MathPlot), a Plotter Interface, and two parsers, one for AOS, one for RPN. The student should add everything that is missing to achieve the Mathplot implementation.

## Notes

- The Plotter implementation performs the affine transformations from real coordinates to screen ones automatically, so points must be specified in real cartesian coordinates. (0,0) is the center of the canvas, positives on top-right, negatives on bottom-left.

## Exam Requirements

- The software must be written in Java language.
- **The deadline is set on December 5th 2025**, the project must be uploaded on Moodle (further details will come).
- The code already present **must not be touched**, the student is allowed to modify the Mathplot class only, where the *YOU CAN CHANGE HERE* comment appears. The student can add more methods to the MathPlot class only (no changes to the Plotter!!), if needed.
- Although JavaFX is used for the GUI part, no JavaFX knowledge is required. The PlotterInterface (returned by the private Plotter class in MathPlot) can be used to draw object on the Canvas.

## Scores and minimum acceptance criteria

The overall exam score is split according to the following schema:

|   Design.  | Implementation |  Testing  | Code Quality |
|  :----:    |     :----:     |   :----:  |   :----:     |
|    30      |      30        |     30    |     10       |

Regardless of the scores above, the **minimum acceptance criteria** for the evaluation is:

- The software must implement at least one input format, AOS or RPN.
- The software must accept the +, -, *, /, ^ operators and at least one well-known function (like *sin*, *cos*, *exp*, *ln*, etc.). Of course each choice could bring other functions indirectly (ex. if the student chooses to implement *sine* function only, *cosine* must be implemented too, due to the derivative).
- The software must implement at least one plot format, either Cartesian or Polar.
- The plot must contain the reference axis and the grid, according to the selected plot format
- The software must print back to the screen the function and the first-order derivative in at least one format (AOS or RPN).
- The software must implement the first-order derivative.
- The software must have unit tests and coverage >= 80% on:
  - the code that the student has written
  - the parser used.
- The design of the code requires that, no matter which choice is performed, adding the alternate method must not require any change in the implemented code.

That means:

- if the student selects AOS only, the software must be designed to allow the introduction of the RPN without changing the design (and vice versa)
- if the student select the Cartesian plot, the software must be designed to allow the introduction of the Polar plot without changing the design (and vice versa)
- if the student decides to implement the area calculation, and only one of the two methods, adding the other method must not require any design change
- the simplification algorithm implementation must not affect the design of expression evaluation.

If one of this criteria is not satisfied, the project is not valid.

Optional features, that gives additional score:

- Both expression input formats supported
- Both plot formats supported
- Both print formats supported
- Area calculation
- Expression simplifier

**If the student implements an optional feature, it must be tested using the above criteria, otherwise it's not taken into account for the final evaluation.**

Test exceptions allowed:

- Function print

### Nice to have (Hard challenge)

To reduce the complexity, the student can choose a always-continuous function, so the Point.Iterator hasBreak() method can always return false. Extra points will be received in case the plot can handle non-continuous functions.

## How To Run

Usually any IDE should be able to start the application selecting the App.java file in the MathPlot folder. In case Maven is chosen as Runner, the application can the be started using the Maven command "mvn javafx:run".
