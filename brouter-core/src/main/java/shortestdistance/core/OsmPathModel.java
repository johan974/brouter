/**
 * Container for link between two Osm nodes
 *
 * @author ab
 */
package shortestdistance.core;

import shortestdistance.expressions.BExpressionContextNode;
import shortestdistance.expressions.BExpressionContextWay;

import java.util.Map;


abstract class OsmPathModel {
  public abstract OsmPrePath createPrePath();

  public abstract OsmPath createPath();

  public abstract void init(BExpressionContextWay expctxWay, BExpressionContextNode expctxNode, Map<String, String> keyValues);
}
