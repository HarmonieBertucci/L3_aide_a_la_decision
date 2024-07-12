package representation;

import java.util.*;

/**
 * permet de representer des contraintes de type v1 != v2, où v1 et v2 sont des variables
 */
public class DifferenceConstraint implements Constraint{
  protected Variable v1;
  protected Variable v2;

  /**
   * constructeur
   * @param v1 est la premiere variable concernee par la contrainte
   * @param v2 est la deuxieme variable concernee par la contrainte
   */
  public DifferenceConstraint(Variable v1, Variable v2){
    this.v1=v1;
    this.v2=v2;
  }

  @Override
  public Set<Variable> getScope(){
    Set<Variable> tmp = new HashSet<>();
    tmp.add(this.v1);
    tmp.add(this.v2);
    return tmp;
  }

  @Override
  public boolean isSatisfiedBy(Map<Variable, Object> dico){
      if(!(dico.keySet().contains(this.v1)) || !(dico.keySet().contains(this.v2))){
        throw new IllegalArgumentException("Il manque une valeur de variable");
        
      }
      else{
        return dico.get(this.v1) != dico.get(this.v2);
      }
    }
}
