#!/usr/bin/env groovy

test(true)

// Read input
ArrayList<String> instructionsAsString = Arrays.asList(new File('input.txt').text.split("\\s*,\\s*"))

// Convert string array to text
List<Integer> instructions = new ArrayList<Integer>()
for(String s : instructionsAsString) instructions.add(Integer.valueOf(s))

// Run code for part 1
println "Solution part 1: " + checkPermutationsPart1(instructions)

// Run code for part 2
println "Solution part 2: " + checkPermutationsPart2(instructions)

static int startAmplifierControllerSoftware(List<Integer> instructions, ArrayList<Integer> phaseSetting) {
    int input = 0
    phaseSetting.each{
        IntCodeComputer icc = new IntCodeComputer(instructions)
        ArrayList<Integer> inputValues = new ArrayList<>(Arrays.asList(it, input))
        icc.setInput(inputValues)
        ArrayList<Integer> outputValues = icc.execute()
        input = outputValues[0]
    }
    return input
}

static int checkPermutationsPart1(List<Integer> instructions) {
    int highestAmp = 0
    [0,1,2,3,4].eachPermutation { phaseSetting ->
        int out = startAmplifierControllerSoftware(instructions, phaseSetting)
        if(highestAmp < out) {
            highestAmp = out
        }
    }
    return highestAmp
}

static int startAmplifierControllerSoftwareLoop(List<Integer> instructions, ArrayList<Integer> phaseSetting) {
    ArrayList<Integer> inputValues

    IntCodeComputer iccAmp1 = new IntCodeComputer(new ArrayList<Integer>(instructions))
    inputValues = new ArrayList<>(Arrays.asList(phaseSetting[0], 0))
    iccAmp1.setInput(inputValues)
    ArrayList<Integer> outputValuesAmp1 = iccAmp1.execute()

    IntCodeComputer iccAmp2 = new IntCodeComputer(new ArrayList<Integer>(instructions))
    inputValues = new ArrayList<>(Arrays.asList(phaseSetting[1], outputValuesAmp1.get(outputValuesAmp1.size()-1)))
    iccAmp2.setInput(inputValues)
    ArrayList<Integer> outputValuesAmp2 = iccAmp2.execute()

    IntCodeComputer iccAmp3 = new IntCodeComputer(new ArrayList<Integer>(instructions))
    inputValues = new ArrayList<>(Arrays.asList(phaseSetting[2], outputValuesAmp2.get(outputValuesAmp2.size()-1)))
    iccAmp3.setInput(inputValues)
    ArrayList<Integer> outputValuesAmp3 = iccAmp3.execute()

    IntCodeComputer iccAmp4 = new IntCodeComputer(new ArrayList<Integer>(instructions))
    inputValues = new ArrayList<>(Arrays.asList(phaseSetting[3], outputValuesAmp3.get(outputValuesAmp3.size()-1)))
    iccAmp4.setInput(inputValues)
    ArrayList<Integer> outputValuesAmp4 = iccAmp4.execute()

    IntCodeComputer iccAmp5 = new IntCodeComputer(new ArrayList<Integer>(instructions))
    inputValues = new ArrayList<>(Arrays.asList(phaseSetting[4], outputValuesAmp4.get(outputValuesAmp4.size()-1)))
    iccAmp5.setInput(inputValues)
    ArrayList<Integer> outputValuesAmp5 = iccAmp5.execute()

    while(iccAmp5.isActive()) {
        outputValuesAmp1 = iccAmp1.processInput(outputValuesAmp5.get(outputValuesAmp5.size()-1))
        outputValuesAmp2 = iccAmp2.processInput(outputValuesAmp1.get(outputValuesAmp1.size()-1))
        outputValuesAmp3 = iccAmp3.processInput(outputValuesAmp2.get(outputValuesAmp2.size()-1))
        outputValuesAmp4 = iccAmp4.processInput(outputValuesAmp3.get(outputValuesAmp3.size()-1))
        outputValuesAmp5 = iccAmp5.processInput(outputValuesAmp4.get(outputValuesAmp4.size()-1))
    }
    return outputValuesAmp5.get(outputValuesAmp5.size()-1)
}

static int checkPermutationsPart2(List<Integer> instructions) {
    int highestAmp = 0
    [5,6,7,8,9].eachPermutation { phaseSetting ->
        int out = startAmplifierControllerSoftwareLoop(instructions, phaseSetting)
        if(highestAmp < out) {
            highestAmp = out
        }
    }
    return highestAmp
}

static void test(boolean debug = false) {
    ArrayList<Integer> instructions
    ArrayList<Integer> phaseSetting
    int output

    instructions = new ArrayList<>(Arrays.asList(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0))
    phaseSetting = new ArrayList<>(Arrays.asList(4,3,2,1,0))
    output = startAmplifierControllerSoftware(instructions, phaseSetting)
    assert output == 43210
    if(debug) {
        println "Test 1 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0))
    phaseSetting = new ArrayList<>(Arrays.asList(0,1,2,3,4))
    output = startAmplifierControllerSoftware(instructions, phaseSetting)
    assert output == 54321
    if(debug) {
        println "Test 2 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0))
    output = checkPermutationsPart1(instructions)
    assert output == 43210
    if(debug) {
        println "Test 3 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0))
    output = checkPermutationsPart1(instructions)
    assert output == 54321
    if(debug) {
        println "Test 4 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5))
    phaseSetting = new ArrayList<>(Arrays.asList(9,8,7,6,5))
    output = startAmplifierControllerSoftwareLoop(instructions, phaseSetting)
    assert output == 139629729
    if(debug) {
        println "Test 5 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10))
    phaseSetting = new ArrayList<>(Arrays.asList(9,7,8,5,6))
    output = startAmplifierControllerSoftwareLoop(instructions, phaseSetting)
    assert output == 18216
    if(debug) {
        println "Test 6 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5))
    output = checkPermutationsPart2(instructions)
    assert output == 139629729
    if(debug) {
        println "Test 7 successful"
    }

    instructions = new ArrayList<>(Arrays.asList(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10))
    output = checkPermutationsPart2(instructions)
    assert output == 18216
    if(debug) {
        println "Test 8 successful"
    }
}