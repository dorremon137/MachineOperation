package perp.tree.stu;

import perp.SymbolTable;
import perp.machine.stu.Machine;
import perp.tree.ActionNode;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Phuvit Kittisapkajon
 * An ActionSequence contains an ordered sequence of ActionNodes.
 */
public class ActionSequence implements ActionNode {

    public ArrayList<ActionNode> actions;

    /**
     * Initialize this instance as an empty sequence of ActionNode children.
     */
    public ActionSequence() {
        this.actions = new ArrayList<>();
    }

    /**
     * Add a child of this ActionSequence node.
     * @param newNode - the node representing the action that will execute last
     */
    public void addAction(ActionNode newNode){
        this.actions.add(newNode);
    }

    /**
     * Show the infix displays of all children on standard output. The order is first-added to last-added.
     */
    @Override
    public void infixDisplay() {
        for (ActionNode n : actions) {
            n.infixDisplay();
        }

    }

    @Override
    /**
     * Create a list of instructions emitted by each child, from the first-added child to the last-added.
     * @Return: mach - a list of Machine Instruction
     */
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> mach = new ArrayList<>();
        for (ActionNode n : actions){
            mach.addAll(n.emit());
        }
        return mach;
    }

    /**
     * Execute each ActionNode in this object, from first-added to last-added.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(SymbolTable symTab) {
        for (ActionNode n : actions) {
            n.execute(symTab);
        }

    }
}
