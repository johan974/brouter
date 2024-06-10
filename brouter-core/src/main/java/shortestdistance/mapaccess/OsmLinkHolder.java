/**
 * Container for routig configs
 *
 * @author ab
 */
package shortestdistance.mapaccess;

public interface OsmLinkHolder {
  void setNextForLink(OsmLinkHolder holder);

  OsmLinkHolder getNextForLink();
}
