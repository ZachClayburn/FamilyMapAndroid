package clayburn.familymap.services;


import clayburn.familymap.ObjectTransmitter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Generates Person and Event data for the Family Map Server
 */
public class DataGenerator {

    static {
        final String resourceLocation = "Server/src/main/resources/";
        try {
            String fileName;
            fileName = resourceLocation + "locations.json";
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
            InputStream stream = new FileInputStream(fileName);
            locations = ObjectTransmitter.receive(stream,LocationHolder.class);
            stream.close();

            fileName = resourceLocation + "fnames.json";
            stream = new FileInputStream(fileName);
            fNames = ObjectTransmitter.receive(stream,NameHolder.class);
            stream.close();

            fileName = resourceLocation + "mnames.json";
            stream = new FileInputStream(fileName);
            mNames = ObjectTransmitter.receive(stream,NameHolder.class);
            stream.close();

            fileName = resourceLocation + "snames.json";
            stream = new FileInputStream(fileName);
            sNames = ObjectTransmitter.receive(stream,NameHolder.class);
            stream.close();

        } catch (ObjectTransmitter.TransmissionException | IOException e) {
            e.printStackTrace();
        }
    }

    private static LocationHolder locations;

    private static NameHolder fNames;

    private static NameHolder mNames;

    private static NameHolder sNames;

    /**
     * Get a female name selected randomly from a pre-loaded list of names
     * @return The randomly selected name
     */
    static String getRandomFemaleName(){
        int index = (int) (Math.random() * (fNames.data.length -1));
        return fNames.data[index];
    }

    /**
     * Get a male name selected randomly from a pre-loaded list of names
     * @return The randomly selected name
     */
    static String getRandomMaleName(){
        int index = (int) (Math.random() * (mNames.data.length -1));
        return mNames.data[index];
    }

    /**
     * Get a surname selected randomly from a pre-loaded list of names
     * @return The randomly selected name
     */
    static String getRandomSurname(){
        int index = (int) (Math.random() * (sNames.data.length -1));
        return sNames.data[index];
    }

    /**
     * Get a random sequence of years in chronological order
     * @param generation The generation of the Person the Event belongs to. Used to determine the starting year
     * @param numEvents The needed number of events to be generated
     * @return An array of Strings numEvents long, containing years to be used as years of events in a Person's life.
     */
    public static String[] getRandomYearSequence(int generation, int numEvents){
        final int gen0StartYear = 1980;
        final int generationYearGap = 40;
        final int eventYearGap = 25;

        String[] years = new String[numEvents];

        int yearToAdd = gen0StartYear - (int)(generation * (generationYearGap + 0.2) * Math.random());

        for (int i = 0; i < numEvents; i++) {
            years[i] = Integer.toString(yearToAdd);
            yearToAdd += (eventYearGap * Math.random());
        }
        return years;
    }

    /**
     * Get a location selected randomly from a pre-loaded list of locations
     * @return The randomly selected location
     */
    public static Location getRandomLocation(){
        int index = (int) (Math.random() * (locations.data.length -1));
        return locations.data[index];
    }


    /**
     * Provides data for generating random location data
     */
    public static class Location{
        private String country;
        private String city;
        private double latitude;
        private double longitude;

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    private static class NameHolder {
        private String[] data;
    }

    private static class LocationHolder{
        Location[] data;
    }
}
