package server.dbService;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 08.12.13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class LocationRange {
    public Double leftLatitude;
    public Double leftLongitude;
    public Double rightlLatitude;
    public Double rightlLongitude;

    public LocationRange(Double leftLatitude, Double leftLongitude, Double rightlLatitude, Double rightlLongitude) {
        this.leftLatitude = leftLatitude;
        this.leftLongitude = leftLongitude;
        this.rightlLatitude = rightlLatitude;
        this.rightlLongitude = rightlLongitude;
    }
}
