package shortestdistance.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ShortestDistance {
  String workingDir;

  public ShortestDistance() throws IOException {
    workingDir = Files.createTempDirectory("brouter").toFile().getAbsolutePath();
  }

  public static void main(String[] args) throws IOException {
    ShortestDistance test = new ShortestDistance();
    System.out.println( "Shortest distance/2: " + test.johanPointOnly());
  }

  public int johanPointOnly() {
    return calcPoint(6.1068, 52.2199);
  }

  private int calcPoint(double flon, double flat) {
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
    rctx.localFunction = "./misc/profiles2/trekking.brf";

    RoutingEngine re = new RoutingEngine(
      workingDir, workingDir, new File("./data"), wplist, rctx);

    return re.findNearestTrackPoint(0);
  }

}
