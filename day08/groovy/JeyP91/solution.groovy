#!/usr/bin/env groovy

test(true)

// Read input
ArrayList<String> pixelsAsString = Arrays.asList(new File('input.txt').text.toList()) as ArrayList<String>

// Convert string array to text
List<Integer> pixels = new ArrayList<Integer>()
for(String s : pixelsAsString) pixels.add(Integer.valueOf(s))

int rows = 25
int columns = 6
Image image = new Image()
for (int i = 0; i < pixels.size(); i += rows * columns) {
    Layer layer = new Layer(rows, columns, pixels.subList(i, i + rows * columns) as ArrayList<Integer>)
    image.addLayer(layer)
}

/** PART 1 **/
println "Solution Part 1: " + getProductOfOnesAndTwos(image)

/** PART 2 **/
List<Integer> realImage = getRealImage(image)

// Build result string
println "Solution Part 2: "
for (int i = 0; i < columns; i++) {
    for (int j = 0; j < rows; j++) {
        StringBuilder sb = new StringBuilder()
        int num = realImage.get(i * rows + j)
        sb.append(num)
        // sb.append("\t")
        print sb.toString()
    }
    println ""
}

static int getProductOfOnesAndTwos(Image image) {
    Layer layer = image.getLayerWithLowestCountOfNumber(0)
    int ones = layer.getCountOfNumber(1)
    int twos = layer.getCountOfNumber(2)
    return ones * twos
}

static List<Integer> getRealImage(Image image) {
    List<Integer> realImage = new ArrayList<>()
    for (int j = 0; j < 150; j++) {
        for (int i = 0; i < image.getLayers().size(); i++) {
            if(image.getLayers()[i].getPixels()[j] != 2) {
                realImage.add(image.getLayers()[i].getPixels()[j])
                break
            }
        }
    }
    return realImage
}

class Image {
    List<Layer> layers
    Image() {
        this.layers = new ArrayList<Layer>()
    }
    void addLayer(Layer layer) {
        layers.add(layer)
    }
    Layer getLayerWithLowestCountOfNumber(int number) {
        int count = 150
        Layer highestLayer = null
        this.layers.each { layer ->
            if(layer.getCountOfNumber(number) < count) {
                highestLayer = layer
                count = layer.getCountOfNumber(number)
            }
        }
        return highestLayer
    }
    List<Layer> getLayers() {
        return layers
    }
}

class Layer {
    int rows
    int columns
    ArrayList<Integer> pixels

    Layer(int rows, int columns, ArrayList<Integer> pixels) {
        if(pixels.size() != rows * columns) {
            throw new Exception("Wrong data")
        }
        this.rows = rows
        this.columns = columns
        this.pixels = pixels
    }

    int getCountOfNumber(int number) {
        int count = 0
        this.pixels.each{ pixel ->
            if(pixel == number){
                count++
            }
        }
        return count
    }

    ArrayList<Integer> getPixels() {
        return pixels
    }
}

static void test(boolean debug) {
    int rows = 2
    int columns = 2
    List<Integer> pixels = new ArrayList<>(Arrays.asList(0,2,2,2,1,1,2,2,2,2,1,2,0,0,0,0))
    Image image = new Image()
    for (int i = 0; i < pixels.size(); i += rows * columns) {
        Layer layer = new Layer(rows, columns, pixels.subList(i, i + rows * columns) as ArrayList<Integer>)
        image.addLayer(layer)
    }

    List<Integer> realImage = getRealImage(image)
    assert realImage[0] == 0
    assert realImage[1] == 1
    assert realImage[2] == 1
    assert realImage[3] == 0
    if(debug) {
        println "Test 1 successful"
    }
}