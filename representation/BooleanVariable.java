package representation;

import java.util.Set;
import java.util.HashSet;

/**
 * classe heritant de la classe variable et ayant leur domaine definit a {true,false}
 */
public class BooleanVariable extends Variable {


  public BooleanVariable(String nom, Set<Object> domaine){
    super(nom , domaine);
  }

  public BooleanVariable(String nom){
    this(nom,new HashSet<>());
    this.domaine.add(true);
    this.domaine.add(false);
  }
}
