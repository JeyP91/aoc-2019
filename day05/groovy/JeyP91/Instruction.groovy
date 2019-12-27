// Converts instruction number into opcode and up to 3 parameters
class Instruction {
    private int opcode
    private int parameter1
    private int parameter2
    private int parameter3

    Instruction(int instruction) {
        // tag::opcodeParameters[]
        this.opcode = (int)Math.floor((instruction % 100))
        this.parameter1 = (int)Math.floor((instruction % 1000) / 100)
        this.parameter2 = (int)Math.floor((instruction % 10000) / 1000)
        this.parameter3 = (int)Math.floor(instruction / 10000)
        // end::opcodeParameters[]
    }

    int getOpcode() {return this.opcode}

    int getParameter1() {return this.parameter1}

    int getParameter2() {return this.parameter2}

    int getParameter3() {return this.parameter3}
}