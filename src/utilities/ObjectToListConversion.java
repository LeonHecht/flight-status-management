package utilities;

import java.util.ArrayList;
import java.util.List;

public class ObjectToListConversion {

    public static ArrayList<Double> convertListToDoubleArrayList(List<Object> list) {
        ArrayList<Double> doubleArrayList = new ArrayList<>();

        for (Object obj : list) {
            if (obj instanceof Number) {
                doubleArrayList.add(((Number) obj).doubleValue());
            } else if (obj instanceof String) {
                try {
                    double value = Double.parseDouble((String) obj);
                    doubleArrayList.add(value);
                } catch (NumberFormatException e) {
                    // Handle if the String cannot be parsed to a Double
                    // You might want to log an error or handle differently based on your use case
                }
            }
            // Add more conditions as needed based on the types you expect in the Object list
        }

        return doubleArrayList;
    }

    public static void main(String[] args) {
        // Example List<Object> to convert to ArrayList<Double>
        List<Object> objectList = new ArrayList<>();
        objectList.add(10.5);  // Double
        objectList.add(20);    // Integer (will be converted to Double)
        objectList.add("30.7"); // String representing a number (will be converted to Double)
        objectList.add("Not a number"); // String that cannot be converted to Double

        // Convert the List<Object> to ArrayList<Double>
        ArrayList<Double> doubleArrayList = convertListToDoubleArrayList(objectList);

        // Print the converted ArrayList<Double>
        System.out.println("ArrayList<Double>:");
        for (Double d : doubleArrayList) {
            System.out.println(d);
        }
    }
}