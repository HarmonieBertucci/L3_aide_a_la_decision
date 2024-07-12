package representation;

import java.util.*;

/**
 * interface permettant de representer une contrainte
 */
public interface Constraint {

  /**
   * le scope est l'ensemble des variables touchees par la contrainte
   * @return le scope de la containte
   */
  public Set<Variable> getScope();

  /**
   *
   * @param dico l'instanciation que l'on verifie
   * @return si la contrainte est satisfaite par l'instanciation dico
   */
  public boolean isSatisfiedBy(Map<Variable, Object> dico);



}
