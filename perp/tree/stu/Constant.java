package perp.tree.stu;

import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phuvit Kittisapkajon
 *
 *Store the integer value in this new Constant.
 */
public class Constant implements ExpressionNode {


    private int value;
    //the integer it will hold
    /**
     * Store the integer value in this new Constant.
     * @param value - the integer it will hold
     */
    public Constant(int value) {this.value = value;}

    /**
     * Print this Constant's value on standard output.
     */
    @Override
    public void infixDisplay() {
        System.out.print(value);
    }

    /**
     * Emit an instruction to push the value onto the stack.
     *
     * @return a list containing that one instruction
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> data = new ArrayList<>();
        Machine.Instruction temp = new Machine.PushConst(value);
        data.add(temp);
        return data;
    }

    /**
     * Evaluate the constant
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this Constant's value;
     */
    @Override
    public int evaluate(SymbolTable symTab) {
        return value;
    }
}
