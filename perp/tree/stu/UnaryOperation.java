package perp.tree.stu;

import perp.Errors;
import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Phuvit Kittisapkajon
 *
 * A calculation represented by a unary operator and its operand.
 */

public class UnaryOperation implements ExpressionNode{
    private String operator;

    private ExpressionNode expr;

    public static final String NEG = "_";
    //arithmetic negation operator

    public static final String SQRT = "#";
    //square root operator

    public static final List<String> OPERATORS = new ArrayList<>(Arrays.asList(NEG, SQRT));
    //Container of all legal unary operators, for use by parsers

    /**
     * Create a new UnaryOperation node.
     *
     * @param operator - the string rep. of the operation
     * @param expr - the operand
     * @pre-condition OPERATORS.contains( operator ), expr != null
     */
    public UnaryOperation(String operator, ExpressionNode expr){
        this.operator = operator;
        this.expr = expr;
    }

    /**
     * Print, on standard output, the infixDisplay of the child nodes preceded by the operator
     * and without an intervening blank.
     */
    @Override
    public void infixDisplay() {
        System.out.print(operator);
        expr.infixDisplay();

    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this UnaryOperation.
     * The operator itself is realized by an instruction that pops a value off the stack,
     * applies the operator, and pushes the answer.
     *
     * @return a list containing instructions for the expression and the instruction to perform the operation
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> data = new ArrayList<>();
        data.addAll(expr.emit());
        switch(operator){
            case NEG:
                data.add(new Machine.Negate());
                break;
            case SQRT:
                data.add(new Machine.SquareRoot());
                break;
        }
        return data;
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    @Override
    public int evaluate(SymbolTable symTab) {
        if (operator.equals(NEG)) {
            return -expr.evaluate(symTab);
        }
        else if (operator.equals(SQRT)) {
            int result = expr.evaluate(symTab);
            if (expr.evaluate(symTab) < 0) {
                Errors.error("Can't do Square Root on a Negative Integer", result);
            } else {
                return (int) (Math.sqrt(result));
            }
        }
        return 0;
    }
}
