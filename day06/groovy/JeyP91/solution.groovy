#!/usr/bin/env groovy

// Read input
// ArrayList<String> orbitsAsString = Arrays.asList(new File('input.txt').text.split("\\r?\\n"))
ArrayList<String> orbitsAsString = Arrays.asList(new File('input_test.txt').text.split("\\r?\\n"))

OrbitSystem orbSys = new OrbitSystem(orbitsAsString)

println "Solution Part 1: " + orbSys.getTotalOrbitsInSystem()

class OrbitSystem {
    ArrayList<String> orbitsAsString
    List<OrbitPair> orbitsAsOrbitPairs = new ArrayList<String>()

    public OrbitSystem(ArrayList<String> orbitsAsString) {
        this.orbitsAsString = orbitsAsString
        orbitsAsString.each { orbit ->
            orbitsAsOrbitPairs.add(new OrbitPair(orbit))
        }
    }

    String getCentralOfOuter(String outer) {
        for(int i = 0; i < orbitsAsOrbitPairs.size(); i++) {
            if(orbitsAsOrbitPairs[i].getOuter() == outer) {
                return orbitsAsOrbitPairs[i].getCentral()
            }
        }
        return null
    }

    int getDistanceBetweenCentralAndOuter(String central, String outer) {
        int jumps = 0
        String nextJump = outer
        while(nextJump != central) {
            nextJump = getCentralOfOuter(nextJump)
            jumps++
            // println central + ", " + nextJump + ", " + outer
            // println jumps
            // println ""
        }
        return jumps
    }

    int getTotalOrbitsInSystem() {
        int total = 0
        for(int i = 0; i < orbitsAsOrbitPairs.size(); i++) {
            total += getDistanceBetweenCentralAndOuter("COM", orbitsAsOrbitPairs[i].getOuter())
        }
        return total
    }
}

class OrbitPair {
    String central
    String outer
    public OrbitPair(String orbitAsString) {
        int seperator = orbitAsString.indexOf(")")
        this.central = orbitAsString.substring(0, seperator)
        this.outer = orbitAsString.substring(seperator + 1, orbitAsString.length())
    }
    public OrbitPair(String central, String outer) {
        this.central = central
        this.outer = outer
    }
    public String getCentral() {
        return this.central
    }
    public String getOuter() {
        return this.outer
    }
}