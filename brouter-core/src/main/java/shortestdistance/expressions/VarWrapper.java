package shortestdistance.expressions;

import shortestdistance.util.LruMapNode;

import java.util.Arrays;

public final class VarWrapper extends LruMapNode {
  float[] vars;

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(Object o) {
    VarWrapper n = (VarWrapper) o;
    if (hash != n.hash) {
      return false;
    }
    return Arrays.equals(vars, n.vars);
  }
}
