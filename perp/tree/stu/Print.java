package perp.tree.stu;

import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ActionNode;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phuvit Kittisapkajon
 *
 * A node that represents the displaying of the value of an expression on the console
 */
public class Print implements ActionNode {

    private ExpressionNode printee;

    /**
     * Set up a Print node.
     *
     * @param printee - the expression to be evaluated and printed
     */
    public Print(ExpressionNode printee){
        this.printee = printee;
    }

    /**
     * Show this statement on standard output as the word "Print" followed by the infix form of the expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print("Print ");
        printee.infixDisplay();
        System.out.println();


    }

    /**
     * This method returns the code emitted by the printee node that pushes the value of the printee
     * expression onto the stack, followed by a PRINT instruction
     *
     * @return a list of instructions ending in the ones that compute the value to be printed, and print it.
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> data = new ArrayList<>();
        data.addAll(printee.emit());
        data.add(new Machine.Print());
        return data;
    }

    /**
     * Evaluate the expression and display the result on the console.
     * Precede it with three equal signs so it stands out a little.
     *
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(SymbolTable symTab) {
        System.out.println("=== " + printee.evaluate(symTab));

    }
}
