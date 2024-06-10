package btools.router;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JohanRoutingEngineTest {
  private File workingDir;

  @Before
  public void before() {
    URL resulturl = this.getClass().getResource("/testtrack0.gpx");
    Assert.assertNotNull("reference result not found: ", resulturl);
    File resultfile = new File(resulturl.getFile());
    workingDir = resultfile.getParentFile();
  }

  @Test
  public void routeCrossingSegmentBorder() {
    String msg = calcRoute(8.720897, 50.002515, 8.723658, 49.997510, "testtrack", new RoutingContext());
    // error message from router?
    Assert.assertNull("routing failed: " + msg, msg);

    // if the track didn't change, we expect the first alternative also
    File a1 = new File(workingDir, "testtrack1.gpx");
    a1.deleteOnExit();
    Assert.assertTrue("result content mismatch", a1.exists());
  }

  @Test
  public void routeDestinationPointFarOff() {
    String msg = calcRoute(8.720897, 50.002515, 16.723658, 49.997510, "notrack", new RoutingContext());
    Assert.assertTrue(msg, msg != null && msg.contains("not found"));
  }

  @Test
  public void johanRouteOne() {
    String msg = calcRoute(6.1068, 52.2199, 6.1219, 52.2254, "paramTrack", new RoutingContext());
    Assert.assertNull("routing failed: " + msg, msg);

    File trackFile = new File(workingDir, "paramTrack1.gpx");
    trackFile.deleteOnExit();
    Assert.assertTrue("result content mismatch", trackFile.exists());
  }

  @Test
  public void johanPointOnly() {
    int shortestDistance = calcPoint(6.1068, 52.2199);
    System.out.println( "Shortest distance: " + shortestDistance );
    Assert.assertEquals( 71, shortestDistance);
  }

  private String calcRoute(double flon, double flat, double tlon, double tlat, String trackname, RoutingContext rctx) {
    String wd = workingDir.getAbsolutePath();

    List<OsmNodeNamed> wplist = new ArrayList<>();
    OsmNodeNamed n;
    n = new OsmNodeNamed();
    n.name = "from";
    n.ilon = 180000000 + (int) (flon * 1000000 + 0.5);
    n.ilat = 90000000 + (int) (flat * 1000000 + 0.5);
    wplist.add(n);

    n = new OsmNodeNamed();
    n.name = "to";
    n.ilon = 180000000 + (int) (tlon * 1000000 + 0.5);
    n.ilat = 90000000 + (int) (tlat * 1000000 + 0.5);
    wplist.add(n);

    rctx.localFunction = wd + "/../../../../misc/profiles2/trekking.brf";

    RoutingEngine re = new RoutingEngine(
      wd + "/" + trackname,
      wd + "/" + trackname,
      new File(wd, "/../../../../data"),
      wplist,
      rctx);

    re.doRun(0);

    return re.getErrorMessage();
  }

  private int calcPoint(double flon, double flat) {
    String wd = workingDir.getAbsolutePath();

    List<OsmNodeNamed> wplist = new ArrayList<>();
    OsmNodeNamed n;
    n = new OsmNodeNamed();
    n.name = "from";
    n.ilon = 180000000 + (int) (flon * 1000000 + 0.5);
    n.ilat = 90000000 + (int) (flat * 1000000 + 0.5);
    wplist.add(n);

    n = new OsmNodeNamed();
    n.name = "to";
    n.ilon = 180000000 + (int) (flon * 1000000 + 0.5);
    n.ilat = 90000000 + (int) (flat * 1000000 + 0.5);
    wplist.add(n);

    RoutingContext rctx = new RoutingContext();
    rctx.localFunction = wd + "/../../../../misc/profiles2/trekking.brf";

    RoutingEngine re = new RoutingEngine(
      wd + "/dummy",
      wd + "/dummy",
      new File(wd, "/../../../../data"),
      wplist,
      rctx);

    return re.findNearestTrackPoint(0);
  }

}
