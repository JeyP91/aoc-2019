class IntCodeComputer {
    List<Integer> instructions
    List<Integer> outputValues
    List<Integer> inputValues

    IntCodeComputer(List<Integer> instructions) {
        this.instructions = instructions
        outputValues = new ArrayList<Integer>()
        inputValues = new ArrayList<Integer>()
    }

    void setInput(List<Integer> input) {
        this.inputValues = input
    }

    List<Integer> execute() {
        // Iterate over complete program and execute operations
        int i = 0
        while (i < instructions.size()) {
            Instruction inst = new Instruction(instructions[i])

            switch (inst.getOpcode()) {
            // Addition
                case 1:
                    i = addition(i, inst)
                    continue

            // Multiplication
                case 2:
                    i = multiplication(i, inst)
                    continue

            // Input
                case 3:
                    i = input(i, inst)
                    continue

            // Output
                case 4:
                    i = output(i, inst)
                    continue

            // jump-if-true
                case 5:
                    i = jumpIfTrue(i, inst)
                    continue

            // jump-if-false
                case 6:
                    i = jumpIfFalse(i, inst)
                    continue

            // less-then
                case 7:
                    i = lessThen(i, inst)
                    continue

            // equals
                case 8:
                    i = equals(i, inst)
                    continue

                case 99:
                    break

            // Error Case
            // Just print some helpful debug information.
                default:
                    println "Current location: $i"
                    println "Opcode: ${inst.getOpcode()}"
                    println instructions[i] + "," + instructions[i+1] + "," + instructions[i+2] + "," + instructions[i+3]
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
    private int input(int i, Instruction inst) {
        int input
        if(inputValues.size() > 0) {
            input = this.inputValues[0]
            inputValues.remove(0)
        }
        else {
            print "Input: "
            input = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine())
        }
        instructions[instructions[i+1]] = input

        // Jump to next instruction
        return i + 2
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