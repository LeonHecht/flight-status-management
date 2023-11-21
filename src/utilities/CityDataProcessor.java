package utilities;

import java.util.ArrayList;
import java.util.List;

public class CityDataProcessor {

    public static List<Object[]> removeCommaAndSubstring(List<Object[]> data, int originCityIndex, int destCityIndex) {
        List<Object[]> modifiedData = new ArrayList<>();

        for (Object[] row : data) {
            Object[] modifiedRow = new Object[row.length];

            if (row.length > originCityIndex && row.length > destCityIndex) {
                // Process OriginCityName
                if (row[originCityIndex] instanceof String) {
                    String originCity = (String) row[originCityIndex];
                    int commaIndex = originCity.indexOf(",");
                    if (commaIndex != -1) {
                        modifiedRow[originCityIndex] = originCity.substring(0, commaIndex);
                    } else {
                        modifiedRow[originCityIndex] = originCity; // No comma found, keep the original value
                    }
                }

                // Process DestCityName
                if (row[destCityIndex] instanceof String) {
                    String destCity = (String) row[destCityIndex];
                    int commaIndex = destCity.indexOf(",");
                    if (commaIndex != -1) {
                        modifiedRow[destCityIndex] = destCity.substring(0, commaIndex);
                    } else {
                        modifiedRow[destCityIndex] = destCity; // No comma found, keep the original value
                    }
                }
            }

            modifiedData.add(modifiedRow);
        }

        return modifiedData;
    }

    public static void main(String[] args) {
        // Assuming data is a List<Object[]> where the columns are in specific indices
        List<Object[]> data = new ArrayList<>();

        // Add example data (replace this with your actual data)
        data.add(new Object[]{"Atlanta, GA", "New York, NY"});
        data.add(new Object[]{"Los Angeles, CA", "Chicago, IL"});

        // Assuming the indices of the "OriginCityName" and "DestCityName" columns in the data
        int originCityIndex = 0; // Replace with the actual index of OriginCityName column
        int destCityIndex = 1;   // Replace with the actual index of DestCityName column

        // Remove everything after the comma in the specified columns and get the modified data
        List<Object[]> modifiedData = removeCommaAndSubstring(data, originCityIndex, destCityIndex);

        // Display modified data
        for (Object[] row : modifiedData) {
            for (Object cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
