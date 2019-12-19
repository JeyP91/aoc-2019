#!/usr/bin/env groovy

// Read input
ArrayList<String> orbitsAsString = Arrays.asList(new File('input.txt').text.split("\\r?\\n"))

test()

OrbitSystem orbSys = new OrbitSystem(orbitsAsString)

println "Solution Part 1: " + orbSys.getTotalOrbitsInSystem()
println "Solution Part 2: " + orbSys.getOrbitalTransfersBetween("YOU", "SAN")

class OrbitSystem {
    ArrayList<String> orbitsAsString
    List<OrbitPair> orbitsAsOrbitPairs = new ArrayList<OrbitPair>()

    OrbitSystem(ArrayList<String> orbitsAsString) {
        this.orbitsAsString = orbitsAsString
        orbitsAsString.each { orbit ->
            orbitsAsOrbitPairs.add(new OrbitPair(orbit))
        }
    }

    OrbitPair getOrbitPairOfOuter(String outer) {
        for(int i = 0; i < orbitsAsOrbitPairs.size(); i++) {
            if(orbitsAsOrbitPairs[i].getOuter() == outer) {
                return orbitsAsOrbitPairs[i]
            }
        }
        return null
    }

    String getCentralOfOuter(String outer) {
        getOrbitPairOfOuter(outer).getCentral()
    }

    int getDistanceBetweenCentralAndOuter(String central, String outer) {
        int jumps = 0
        String nextJump = outer
        while(nextJump != central) {
            nextJump = getCentralOfOuter(nextJump)
            jumps++
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

    int getOrbitalTransfersBetween(String start, String finish) {
        Integer jumps = null
        List<OrbitPair> pathStartCentral = getPathFromObjectToCentral(start)
        List<OrbitPair> pathFinishCentral = getPathFromObjectToCentral(finish)
        if(pathStartCentral[0].getCentral() == finish) {
            return 0
        }
        for(int i = 0; i < pathStartCentral.size(); i++) {
            if(pathStartCentral[i].getCentral() == finish) {
                return i
            }
            for(int j = 0; j < pathFinishCentral.size(); j++) {
                if(pathFinishCentral[j].getCentral() == start) {
                    return j
                }
                if(pathStartCentral[i].getCentral() == pathFinishCentral[j].getCentral()) {
                    return i + j
                }
            }
        }
        return jumps
    }

    ArrayList<OrbitPair> getPathFromObjectToCentral(String object) {
        List<OrbitPair> path = new ArrayList<OrbitPair>()
        OrbitPair orbit = getOrbitPairOfOuter(object)
        path.add(orbit)
        while (orbit.getCentral() != "COM") {
            orbit = getOrbitPairOfOuter(orbit.getCentral())
            path.add(orbit)
        }
        return path
    }

    OrbitPair findOrbit
}

class OrbitPair {
    String central
    String outer
    OrbitPair(String orbitAsString) {
        int separator = orbitAsString.indexOf(")")
        this.central = orbitAsString.substring(0, separator)
        this.outer = orbitAsString.substring(separator + 1, orbitAsString.length())
    }
    OrbitPair(String central, String outer) {
        this.central = central
        this.outer = outer
    }
    String getCentral() {
        return this.central
    }
    String getOuter() {
        return this.outer
    }
}

static void test() {
    ArrayList<String> orbitsAsString
    OrbitSystem orbSys

    // Test part1 example with result 42
    orbitsAsString = Arrays.asList(new File('input_test1.txt').text.split("\\r?\\n"))
    orbSys = new OrbitSystem(orbitsAsString)
    assert orbSys.getCentralOfOuter("J") == "E"
    assert orbSys.getDistanceBetweenCentralAndOuter("COM", "H") == 3
    assert orbSys.getTotalOrbitsInSystem() == 42

    // Test part2 example with result 4
    orbitsAsString = Arrays.asList(new File('input_test2.txt').text.split("\\r?\\n"))
    orbSys = new OrbitSystem(orbitsAsString)
    assert orbSys.getPathFromObjectToCentral("YOU").size() == 7
    assert orbSys.getPathFromObjectToCentral("SAN").size() == 5
    assert orbSys.getOrbitalTransfersBetween("YOU", "J") == 1
    assert orbSys.getOrbitalTransfersBetween("YOU", "K") == 0
    assert orbSys.getOrbitalTransfersBetween("YOU", "SAN") == 4
}