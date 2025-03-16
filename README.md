# Calculator
In my code, I divided the main tasks into separate functions to make the code easier to read and understand. 
I also added calculation history, so the user can see previous results. To store history, I used ArrayList<String>, 
where expressions and their results are saved. If the user types "history", the program shows past calculations.
To process expressions, I used:
Map (Map<String, Integer>) — stores operator priorities.
List (List<String>) — stores numbers, operators, and functions.
Stack (Stack<String> and Stack<Double>) — helps to work with Reverse Polish Notation (RPN).

Code explanation:
-----------
The user enters an expression. If "history" is entered, the program shows past calculations.
Operator priorities are set in Map<String, Integer>.
The expression is split into tokens in tokenize().
Tokens are converted to RPN in toRPN().
The result is calculated in evaluateDouble().
The result is displayed and saved in history.
The user decides whether to continue or exit.

Functions:
-----------
displayHistory
This function shows calculation history from ArrayList<String> history.
------------
tokenize
This function splits the expression into tokens: numbers, operators, and functions.
Numbers are added as they are.
Operators (+, -, *, /, %) and functions (sqrt, pow, abs, round) are recognized separately.
Parentheses are also separate tokens.
If there is an unknown symbol, the program shows an error.
-------------
toRPN
This function converts an expression into Reverse Polish Notation (RPN).
RPN is a way to write mathematical expressions where operators come after operands. 
For example, instead of 3 + 5, in RPN it is written as 3 5 +. This format is convenient because it does not require parentheses and follows a clear execution order.

I used RPN because it makes calculations easier with a stack.
How does toRPN work?

A stack operators is used to store operators.
Numbers are added directly to the out list.
Operators are compared by priority and arranged correctly.
Parentheses are processed separately to keep the expression correct.
---------------
evaluateDouble
This function calculates the result using RPN.

Numbers are added to the stack numbers.
Operators take numbers from the stack, perform an operation, and push the result back.
Functions (sqrt, abs, round) work with one number.
At the end, the stack contains only one final result.


Expamples of screenshots:
![image](https://github.com/user-attachments/assets/e79938a2-4944-4659-b535-a3d9e3f78e12)
![image](https://github.com/user-attachments/assets/53abe4dc-586b-43ba-8bcf-936fb0d87ac3)

