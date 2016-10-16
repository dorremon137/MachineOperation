package perp.machine.stu;

import java.util.List;
import java.util.Stack;

import perp.Errors;
import perp.SymbolTable;

/**
 * An abstraction of a computing machine that reads instructions
 * and executes them. It has an instruction set, a symbol table
 * for variables (instead of general-purpose memory), and a
 * value stack on which calculations are performed.
 *
 * (Everything is static to avoid the need to master the subtleties
 * of nested class instantiation or to pass the symbol table and
 * stack into every instruction when it executes.)
 *
 * THIS CLASS IS INCOMPLETE. The student must add code to it.
 *
 * @author James Heliotis
 * @author Phuvit Kittisapkajon
 */
public class Machine {

    /** Do not instatiate this class. */
    private Machine() {}

    public static interface Instruction {
        /**
         * Run this instruction on the Machine, using the Machine's
         * value stack and symbol table.
         */
        void execute();

        /**
         * Show the instruction using text so it can be understood
         * by a person.
         * @return a short string describing what this instruction will do
         */
        @Override
        String toString();
    }

    private static SymbolTable table = null;
    private static Stack< Integer > stack = null;

    /**
     * Reset the Machine to a pristine state.
     * @see Machine#execute
     */
    private static void reset() {
        stack = new Stack<>();
        table = new SymbolTable();
    }

    /**
     * Generate a listing of a program on standard output by
     * calling the toString() method on each instruction
     * contained therein, in order.
     *
     * @param program the list of instructions in the program
     */
    public static void displayInstructions(
            List< Machine.Instruction > program ) {
        System.out.println( "\nCompiled code:" );
        for ( Machine.Instruction instr: program ) {
            System.out.println( instr );
        }
        System.out.println();
    }

    /**
     * Run a "compiled" program by executing in order each instruction
     * contained therein.
     * Report on the final size of the stack (should normally be empty)
     * and the contents of the symbol table.
     * @param program a list of Machine instructions
     */
    public static void execute( List< Instruction > program ) {
        reset();
        System.out.println("Executing compiled code...");
        for ( Instruction instr: program ) {
            instr.execute();
        }
        System.out.println( "Machine: execution ended with " +
                stack.size() + " items left on the stack." );
        System.out.println();
        table.dump();
    }

    /**
     * The ADD instruction
     */
    public static class Add implements Instruction {
        /**
         * Run the microsteps for the ADD instruction.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push( op1 + op2 );
        }

        /**
         * Show the ADD instruction as plain text.
         * @return "ADD"
         */
        @Override
        public String toString() {
            return "ADD";
        }
    }

    /**
     * The STORE instruction
     */
    public static class Store implements Instruction {
        /** stores name of target variable */
        private String name;

        /**
         * Create a STORE instruction
         * @param ident the name of the target variable
         */
        public Store( String ident ) {
            this.name = ident;
        }
        /**
         * Run the microsteps for the STORE instruction.
         */
        @Override
        public void execute() {
            table.put( this.name, stack.pop() );
        }
        /**
         * Show the STORE instruction as plain text.
         * @return "STORE" followed by the target variable name
         */
        @Override
        public String toString() {
            return "STORE\t" + this.name;
        }
    }

    //
    // ENTER YOUR CODE FOR THE OTHER INSTRUCTION CLASSES HERE.
    //

    /**
     * The SUB instruction
     */
    public static class Subtract implements Instruction {
        public Subtract(){}

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 - op2);
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         * @return a short string describing what this instruction will do
         */
        public String toString() {return "SUB";}
    }

    /**
     * The MUL instruction
     */
    public static class Multiply implements Instruction {
        public Multiply(){}

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            stack.push(op1 * op2);
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         * @return a short string describing what this instruction will do
         */
        public String toString() {return "MUL";}
    }

    /**
     * The DIV instruction
     */
    public static class Divide implements Instruction {
        public Divide(){}

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {
            int op2 = stack.pop();
            int op1 = stack.pop();
            if (op2 == 0) {
                Errors.error("Cannot perform division operation because the denominator is 0 ", 0);
            }
            stack.push( op1 / op2);
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         *
         * @return a short string describing what this instruction will do
         */
        public String toString() {return "DIV";}
    }

    /**
     * The NEG instruction
     */
    public static class Negate implements Instruction {
        public Negate(){}

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {
            int op = stack.pop();
            stack.push(-op);
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         * @return a short string describing what this instruction will do
         */
        public String toString() {return "NEG";}
    }

    /**
     * The SQRT instruction
     */
    public static class SquareRoot implements Instruction {

        public SquareRoot(){}

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {
            int op = stack.pop();
            if (op < 0) {
                Errors.error("Can't do Square Root on a Negative Integer", op);
            } else {
                stack.push((int) Math.sqrt(op));
            }
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         *
         * @return - a short string describing what this instruction will do
         */
        public String toString() {return "SQRT";}
    }

    public static class PushConst implements Instruction {
        private int constant;

        public PushConst(int constant){
            this.constant = constant;
        }

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {stack.push(constant);}

        /**
         * Show the instruction using text so it can be understood by a person.
         *
         * @return a short string describing what this instruction will do
         */
        public String toString() {
            return "PUSH\t" + String.valueOf(constant);
        }
    }

    /**
     * The LOAD instruction
     */
    public static class Load implements Instruction {

        private String ident;

        public Load(String ident){
            this.ident = ident;
        }

        /**
         * Run this instruction on the Machine, using the Machine's value stack and symbol table.
         */
        @Override
        public void execute() {stack.push(table.get(ident));}

        /**
         * Show the instruction using text so it can be understood by a person.
         * @return a short string describing what this instruction will do
         */
        public String toString(){return "LOAD\t" + ident;}
    }

    /**
     * The PRINT instruction
     */
    public static class Print implements Instruction {

        public Print(){}

        /**
         *  Output "*** " followed by the value popped from the stack.
         */
        @Override
        public void execute() {
            System.out.println("*** " + stack.pop());
        }

        /**
         * Show the instruction using text so it can be understood by a person.
         * @return a short string describing what this instruction will do
         */
        public String toString(){ return "PRINT"; }
    }
}
