/**
 * Calculate the angle defined by 3 points
 * (and deliver it's cosine on the fly)
 */
package shortestdistance.util;

public final class CheapAngleMeter {
  private double cosangle;

  public double getCosAngle() {
    return cosangle;
  }

  public double calcAngle(int lon0, int lat0, int lon1, int lat1, int lon2, int lat2) {
    double[] lonlat2m = CheapRuler.getLonLatToMeterScales(lat1);
    double lon2m = lonlat2m[0];
    double lat2m = lonlat2m[1];
    double dx10 = (lon1 - lon0) * lon2m;
    double dy10 = (lat1 - lat0) * lat2m;
    double dx21 = (lon2 - lon1) * lon2m;
    double dy21 = (lat2 - lat1) * lat2m;

    double dd = Math.sqrt((dx10 * dx10 + dy10 * dy10) * (dx21 * dx21 + dy21 * dy21));
    if (dd == 0.) {
      cosangle = 1.;
      return 0.;
    }
    double sinp = (dy10 * dx21 - dx10 * dy21) / dd;
    double cosp = (dy10 * dy21 + dx10 * dx21) / dd;
    cosangle = cosp;

    double offset = 0.;
    double s2 = sinp * sinp;
    if (s2 > 0.5) {
      if (sinp > 0.) {
        offset = 90.;
        sinp = -cosp;
      } else {
        offset = -90.;
        sinp = cosp;
      }
      s2 = cosp * cosp;
    } else if (cosp < 0.) {
      sinp = -sinp;
      offset = sinp > 0. ? -180. : 180.;
    }
    return offset + sinp * (57.4539 + s2 * (9.57565 + s2 * (4.30904 + s2 * 2.56491)));
  }

  public static double getAngle(int lon1, int lat1, int lon2, int lat2) {
    double res = 0;
    double xdiff = lat2 - lat1;
    double ydiff = lon2 - lon1;
    res = Math.toDegrees(Math.atan2(ydiff, xdiff));
    return res;
  }

  public static double getDirection(int lon1, int lat1, int lon2, int lat2) {
    double res = getAngle(lon1, lat1, lon2, lat2);
    return normalize(res);
  }

  public static double normalize(double a) {
    return a >= 360 ? a - (360 * (int) (a / 360))
      : a < 0 ? a - (360 * ((int) (a / 360) - 1)) : a;
  }

  public static double getDifferenceFromDirection(double b1, double b2) {
    double r = (b2 - b1) % 360.0;
    if (r < -180.0) r += 360.0;
    if (r >= 180.0) r -= 360.0;
    return Math.abs(r);
  }

}
