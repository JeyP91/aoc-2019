#!/usr/bin/env groovy

test()

println calcFuel(new File('input.txt'))

static int calcFuel(File masses) {
    int fuel = 0
    
    // iterate over all modules
    masses.eachLine('UTF-8') { line ->
        int mass = Integer.parseInt(line)
        fuel += calcFuelForSingleMass(mass)
    }
    return fuel
}

// tag::calcFuel[]
static int calcFuelForSingleMass(int mass) {
    return (mass / 3).trunc() - 2
}
// end::calcFuel[]

static void test() {
    assert calcFuelForSingleMass(1969) == 654
    assert calcFuelForSingleMass(100756) == 33583
}