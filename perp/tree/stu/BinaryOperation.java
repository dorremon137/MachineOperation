package perp.tree.stu;

import org.omg.CORBA.Object;
import perp.Errors;
import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Phuvit Kittisapajon
 *
 * A calculation represented by a binary operator and its two operands.
 */
public class BinaryOperation implements ExpressionNode {

    public static final String ADD = "+";
    //The operator symbol used for addition

    public static final String SUB = "-";
    //The operator symbol used for subtraction

    public static final String MUL = "*";
    //The operator symbol used for multiplication

    public static final String DIV = "//";
    //The operator symbol used for division

    public static final Collection<String> OPERATORS = new ArrayList<>(Arrays.asList(ADD, SUB,MUL,DIV));
    //Container of all legal binary operators, for use by parsers

    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    /**
     * Create a new BinaryOperation node.
     *
     * @param operator - the string rep. of the operation
     * @param leftChild - the left operand
     * @param rightChild - the right operand
     * @pre-condition OPERATORS.contains( operator ), leftChild != null, rightChild != null
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild){
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }


    /**
     * Print, on standard output, the infixDisplay of the two child nodes separated by the operator
     * and surrounded by parentheses. Blanks are inserted throughout.
     */
    @Override
    public void infixDisplay() {
        System.out.print("( ");
        leftChild.infixDisplay();
        System.out.print(" " + operator + " ");
        rightChild.infixDisplay();
        System.out.print(" )");
    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this BinaryOperation.
     * The operator itself is realized by an instruction that pops two values off the stack,
     * applies the operator, and pushes the answer.
     *
     * @return a list containing instructions for the left operand, instructions for the right operand,
     *         and the instruction to perform the operation
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> data = new ArrayList<>();
        data.addAll(leftChild.emit());
        data.addAll(rightChild.emit());
        switch(operator){
            case ADD:
                data.add(new Machine.Add());
                break;
            case DIV:
                data.add(new Machine.Divide());
                break;
            case MUL:
                data.add(new Machine.Multiply());
                break;
            case SUB:
                data.add(new Machine.Subtract());
                break;
        }
        return data;
    }

    /**
     * Compute the result of evaluating both operands and applying the operator to them.
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    @Override
    public int evaluate(SymbolTable symTab) {
        if (operator.equals(ADD)) {
            return leftChild.evaluate(symTab) + rightChild.evaluate(symTab);
        } else if (operator.equals(SUB)) {
            return leftChild.evaluate(symTab) - rightChild.evaluate(symTab);
        } else if ((operator.equals(MUL))) {
            return leftChild.evaluate(symTab) * rightChild.evaluate(symTab);
        } else if (operator.equals(DIV)) {
            int nominator = leftChild.evaluate(symTab);
            int denominator = rightChild.evaluate(symTab);
            if (denominator == 0){
                Errors.error("Cannot perform division because the denominator is 0", nominator + "/" + denominator);
            }
            else {
                return nominator/denominator;
            }

        }
        return 0;
    }
}

