class IntCodeComputer {
    ArrayList<Integer> instructions
    ArrayList<Integer> outputValues
    ArrayList<Integer> inputValues
    boolean Active = false
    int currentPosition = 0

    IntCodeComputer(List<Integer> instructions) {
        this.instructions = instructions
        this.outputValues = new ArrayList<Integer>()
    }

    void setInput(List<Integer> input) {
        this.inputValues = input
    }

    List<Integer> execute() {
        Active = true
        // Iterate over complete program and execute operations
        while (this.currentPosition < instructions.size()) {
            Instruction inst = new Instruction(instructions[this.currentPosition])

            switch (inst.getOpcode()) {
            // Addition
                case 1:
                    this.currentPosition = addition(this.currentPosition, inst)
                    continue

            // Multiplication
                case 2:
                    this.currentPosition = multiplication(this.currentPosition, inst)
                    continue

            // Input
                case 3:
                    int newInput = input(this.currentPosition)
                    if(newInput == -1) {
                        // wait for next input to continue by calling processInput(int input)
                        break
                    }
                    else {
                        this.currentPosition = newInput
                    }
                    continue

            // Output
                case 4:
                    this.currentPosition = output(this.currentPosition, inst)
                    continue

            // jump-if-true
                case 5:
                    this.currentPosition = jumpIfTrue(this.currentPosition, inst)
                    continue

            // jump-if-false
                case 6:
                    this.currentPosition = jumpIfFalse(this.currentPosition, inst)
                    continue

            // less-then
                case 7:
                    this.currentPosition = lessThen(this.currentPosition, inst)
                    continue

            // equals
                case 8:
                    this.currentPosition = equals(this.currentPosition, inst)
                    continue

                case 99:
                    Active = false
                    break

            // Error Case
            // Just print some helpful debug information.
                default:
                    println "Current position: ${this.currentPosition}"
                    println "Opcode: ${inst.getOpcode()}"
                    println instructions[this.currentPosition] + "," + instructions[this.currentPosition+1] + "," + instructions[this.currentPosition+2] + "," + instructions[this.currentPosition+3]
                    throw new Exception()
            }
            break
        }
        return outputValues
    }

    // Calculate values and location based on opcode-parameters
    private int addition(int i, Instruction inst) {
        int value1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int value2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]
        int resultLocation = instructions[i+3]

        // Calculate addition
        instructions[resultLocation] = value1 + value2

        // Jump to next instruction
        return i + 4
    }

    // Calculate values and location based on opcode-parameters
    private int multiplication(int i, Instruction inst) {
        int value1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int value2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]
        int resultLocation = instructions[i+3]

        // Calculate multiplication
        instructions[resultLocation] = value1 * value2

        // Jump to next instruction
        return i + 4
    }

    // Read input and write to location
    private int input(int i) {
        int input
        if(this.inputValues.size() > 0) {
            input = this.inputValues[0]
            this.inputValues.remove(0)
        }
        else {
            return -1
            // print "Input: "
            // input = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine())
        }
        instructions[instructions[i+1]] = input

        // Jump to next instruction
        return i + 2
    }

    // Read input and write to location
    List<Integer> processInput(int input) {
        instructions[instructions[this.currentPosition+1]] = input
        this.currentPosition = this.currentPosition + 2
        return execute()
    }

    // Calculate output source based on opcode-parameters and print the output
    private int output(int i, Instruction inst) {
        int output = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        // println "Output: $output"
        this.outputValues.add(output)

        // Jump to next instruction
        return i + 2
    }

    // Calculate parameters based on opcode-parameters
    private int jumpIfTrue(int i, Instruction inst) {
        int par1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int par2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]

        // Evaluate jump to next instruction based on true parameter
        if(par1 != 0) {
            return par2
        }
        else {
            return i + 3
        }
    }

    // Calculation parameters based on opcode-parameters
    private int jumpIfFalse(int i, Instruction inst) {

        int par1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int par2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]

        // Evaluate jump to next instruction based on false parameter
        if(par1 == 0) {
            return par2
        }
        else {
            return i + 3
        }
    }

    private int lessThen(int i, Instruction inst) {
        // Calculation parameters based on opcode-parameters
        int par1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int par2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]
        int resultLocation = instructions[i+3]

        // Evaluate less-then check and write 1 or 0 to location
        instructions[resultLocation] = par1 < par2 ? 1 : 0

        // Jump to next instruction
        return i + 4
    }

    // Calculation parameters and location based on opcode-parameters
    private int equals(int i, Instruction inst) {
        int par1 = inst.getParameter1() == 0 ? instructions[instructions[i+1]] : instructions[i+1]
        int par2 = inst.getParameter2() == 0 ? instructions[instructions[i+2]] : instructions[i+2]
        int resultLocation = instructions[i+3]

        // Evaluate equals check and write 1 or 0 to location
        instructions[resultLocation] = par1 == par2 ? 1 : 0

        // Jump to next instruction
        return i + 4
    }
}