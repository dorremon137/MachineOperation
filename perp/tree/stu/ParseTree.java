package perp.tree.stu;

import perp.Errors;
import perp.SymbolTable;
import perp.machine.InstructionReader;
import perp.machine.stu.Machine;
import perp.tree.ActionNode;
import perp.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.List;

import static sun.util.locale.LocaleUtils.isAlphaNumericString;

/**
 * Operations that are done on a Perp code parse tree.
 *
 *
 *
 * @author Phuvit Kittisapkajon.
 */
public class ParseTree {
    private static ActionSequence actions;
    private static SymbolTable table = new SymbolTable();
    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     *
     * @param program the token list (Strings). This list may be destroyed by this constructor.
     */
    public ParseTree( List< String > program ) {
        ArrayList<String> action = new ArrayList<>();
        actions = new ActionSequence();
        while (!program.isEmpty()) {
            String current = program.get(0);
            if (current.equals(":=") || current.equals("@")) {
                if (action.isEmpty()) {
                    action.add(current);
                    program.remove(current);
                } else {
                    actions.addAction(parseAction(action));
                    action = new ArrayList<>();
                    action.add(current);
                    program.remove(current);
                }
            } else {
                action.add(current);
                program.remove(current);
            }
        }
        if (!action.isEmpty()){
            actions.addAction(parseAction(action));
        }
    }
    /**
     * Parse the next action (statement) in the list.
     * (This method is not required, just suggested.)
     *
     * @param program the list of tokens
     * @return a parse tree for the action
     */
    private ActionNode parseAction(List< String > program ) {
        String statement = program.remove(0);
        if (statement.equals(":=")) {
            String ident = program.remove(0);
            return new Assignment(ident,parseExpr(program));
        } else if (statement.equals("@")){
            return new Print(parseExpr(program));
        }
        Errors.error("Invalid String Input for Determine Whether the program is an Assignment or a Print", statement);
        return null;
    }

    /**
     * Parse the next expression in the list.
     * (This method is not required, just suggested.)
     * @param program the list of tokens
     * @return a parse tree for this expression
     */
    private ExpressionNode parseExpr( List< String > program ) {
        String current;
        if (program.isEmpty()){
            return null;
        }
        else {
            current = program.remove(0);
            if (current.matches("[a-zA-Z].*")) {
                return new Variable(current);
            } else if (isAlphaNumericString(current)) {
                return new Constant(Integer.parseInt(current));
            } else if (BinaryOperation.OPERATORS.contains(current)) {
                return new BinaryOperation(current, parseExpr(program), parseExpr(program));
            } else if (UnaryOperation.OPERATORS.contains(current)) {
                return new UnaryOperation(current, parseExpr(program));
            }
        }
        Errors.error("Invalid String input for ExpressionNode Type (at parseExpr method)",current);
        return null;
    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see perp.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        System.out.println("\nThe program, with expressions in infix notation:\n");
        actions.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     * @see perp.tree.ActionNode#execute(SymbolTable)
     */
    public void interpret() {
        System.out.println("\n" + "Interpreting the parse tree...");
        actions.execute(table);
        System.out.println("Interpretation Completed.\n");
        table.dump();
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     * @return the Machine.Instruction list
     * @see perp.machine.stu.Machine.Instruction#execute()
     */
    public List< Machine.Instruction > compile() {return actions.emit();}

}
