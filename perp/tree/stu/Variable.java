package perp.tree.stu;

import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phuvit Kittisapkajon
 *
 * The ExpressionNode for a simple variable
 */
public class Variable implements ExpressionNode {
    private String name;

    /**
     * Set the name of this new Variable. Note that it is not wrong for more than one Variable node
     * to refer to the same variable. Its actual value is stored in a SymbolTable.
     *
     * @param name - the name of this variable
     */
    public Variable(String name){
        this.name = name;
    }

    /**
     * Print on standard output the Variable's name.
     */
    @Override
    public void infixDisplay() {
        System.out.print(name);
    }

    /**
     * Emit a LOAD instruction that pushes the Variable's value onto the stack.
     *
     * @return a list containing a single LOAD instruction
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> data = new ArrayList<>();
        data.add(new Machine.Load(name));
        return data;
    }

    /**
     * Evaluate a variable by fetching its value
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this variable's current value in the symbol table
     */
    @Override
    public int evaluate(SymbolTable symTab) {
        return symTab.get(name);
    }
}
