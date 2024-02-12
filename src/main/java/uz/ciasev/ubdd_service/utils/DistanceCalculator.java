package uz.ciasev.ubdd_service.utils;

import org.springframework.data.util.Pair;
import java.util.List;

public class DistanceCalculator {

    private final static Double bigR = 6371D;

    public static List<Pair<Double, Double>> minMaxCoordinates(double lat, double lon, double radius) {

        double angularR = radius / bigR;

        lat = Math.toRadians(lat);
        lon = Math.toRadians(lon);

        //

        double latMin = lat - angularR;
        double latMax = lat + angularR;

        //

        double lonDelta = Math.sin(angularR) / Math.cos(lat);
        lonDelta = Math.asin(lonDelta);

        double lonMin = lon - lonDelta;
        double lonMax = lon + lonDelta;

        //

        latMin = Math.toDegrees(latMin);
        latMax = Math.toDegrees(latMax);
        lonMin = Math.toDegrees(lonMin);
        lonMax = Math.toDegrees(lonMax);

        //

        return List.of(Pair.of(latMin, lonMin),
                Pair.of(latMax, lonMax));
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

//    public static void main (String[] args) {
//
//        double dist = 5D;
//        double lat = 41.305067;
//        double lon = 69.265685;
//
//        List<Pair<Double, Double>> rsl = minMaxCoordinates(dist, lat, lon);
//
//        System.out.println(distance(rsl.get(0).getFirst(), rsl.get(0).getSecond(), rsl.get(1).getFirst(), rsl.get(1).getSecond(), "K"));
//        System.out.println(distance(lat, lon, rsl.get(0).getFirst(), rsl.get(0).getSecond(), "K"));
//    }
}