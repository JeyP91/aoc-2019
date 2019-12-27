#!/usr/bin/env groovy

test()

// Read input
ArrayList<String> instructionsAsString = Arrays.asList(new File('input.txt').text.split("\\s*,\\s*"))
List<Integer> instructions
IntCodeComputer icc
ArrayList<Integer> inputValues
ArrayList<Integer> outputValues

/******
 PART 1
 ******/
// Convert string array to text
instructions = new ArrayList<Integer>()
for(String s : instructionsAsString) instructions.add(Integer.valueOf(s))

// Create computer object
icc = new IntCodeComputer(instructions)
inputValues = new ArrayList<>(Arrays.asList(1))
icc.setInput(inputValues)
outputValues = icc.execute()
println "Solution Part 1: " + outputValues[outputValues.size()-1]

/******
 PART 2
 ******/
// Convert string array to text
instructions = new ArrayList<Integer>()
for(String s : instructionsAsString) instructions.add(Integer.valueOf(s))

// Create computer object
icc = new IntCodeComputer(instructions)
inputValues = new ArrayList<>(Arrays.asList(5))
icc.setInput(inputValues)
outputValues = icc.execute()
println "Solution Part 2: " + outputValues[outputValues.size()-1]

static void test() {
    ArrayList<Integer> instructions
    IntCodeComputer icc
    ArrayList<Integer> inputValues
    ArrayList<Integer> outputValues

    // Using position mode, consider whether the input is equal to 8; output 1 (if it is).
    instructions = new ArrayList<>(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(8))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // Using position mode, consider whether the input is equal to 8; output 0 (if it is not).
    instructions = new ArrayList<>(Arrays.asList(3,9,8,9,10,9,4,9,99,-1,8))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(7))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Using position mode, consider whether the input is less than 8; output 1 (if it is).
    instructions = new ArrayList<>(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(7))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // Using position mode, consider whether the input is less than 8; output 0 (if it is not).
    instructions = new ArrayList<>(Arrays.asList(3,9,7,9,10,9,4,9,99,-1,8))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(8))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Using immediate mode, consider whether the input is equal to 8; output 1 (if it is).
    instructions = new ArrayList<>(Arrays.asList(3,3,1108,-1,8,3,4,3,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(8))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // Using immediate mode, consider whether the input is equal to 8; output 0 (if it is not).
    instructions = new ArrayList<>(Arrays.asList(3,3,1108,-1,8,3,4,3,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(7))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Using immediate mode, consider whether the input is less than 8; output 1 (if it is).
    instructions = new ArrayList<>(Arrays.asList(3,3,1107,-1,8,3,4,3,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(7))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // Using immediate mode, consider whether the input is less than 8; output 0 (if it is not).
    instructions = new ArrayList<>(Arrays.asList(3,3,1107,-1,8,3,4,3,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(8))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Here are some jump tests using position mode that take an input, then output 0 if the input was zero.
    instructions = new ArrayList<>(Arrays.asList(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(0))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Here are some jump tests using position mode that take an input, then output 1 if the input was non-zero.
    instructions = new ArrayList<>(Arrays.asList(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(1))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // Here are some jump tests using immediate mode that take an input, then output 0 if the input was zero.
    instructions = new ArrayList<>(Arrays.asList(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(0))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 0

    // Here are some jump tests using immediate mode that take an input, then output 1 if the input was non-zero.
    instructions = new ArrayList<>(Arrays.asList(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(1))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1

    // The above example program uses an input instruction to ask for a single number.
    // The program will then output 999 if the input value is below 8.
    instructions = new ArrayList<>(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(7))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 999

    // The above example program uses an input instruction to ask for a single number.
    // The program will then output 1000 if the input value is equal to 8.
    instructions = new ArrayList<>(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(8))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1000

    // The above example program uses an input instruction to ask for a single number.
    // The program will then output 1001 if the input value is greater than 8.
    instructions = new ArrayList<>(Arrays.asList(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
    icc = new IntCodeComputer(instructions)
    inputValues = new ArrayList<>(Arrays.asList(9))
    icc.setInput(inputValues)
    outputValues = icc.execute()
    assert outputValues[0] == 1001
}