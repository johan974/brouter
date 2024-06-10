/**
 * Container for link between two Osm nodes
 *
 * @author ab
 */
package shortestdistance.core;

import shortestdistance.expressions.BExpressionContext;
import shortestdistance.expressions.BExpressionContextNode;
import shortestdistance.expressions.BExpressionContextWay;

import java.util.Map;


final class StdModel extends OsmPathModel {
  public OsmPrePath createPrePath() {
    return null;
  }

  public OsmPath createPath() {
    return new StdPath();
  }

  private BExpressionContextWay ctxWay;
  private BExpressionContextNode ctxNode;


  @Override
  public void init(BExpressionContextWay expctxWay, BExpressionContextNode expctxNode, Map<String, String> keyValues) {
    ctxWay = expctxWay;
    ctxNode = expctxNode;

    BExpressionContext expctxGlobal = expctxWay; // just one of them...

  }
}
