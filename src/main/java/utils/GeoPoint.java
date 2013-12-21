package utils;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class GeoPoint {
    private Double latitude;
    private Double longitude;

    public GeoPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return new StringBuffer("Location[{").append("Latitude:").append(latitude).append(",")
                                            .append("Longitude:").append(longitude).append("}]").toString();
    }
}
