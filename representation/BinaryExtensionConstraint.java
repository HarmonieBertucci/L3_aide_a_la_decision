package representation;
import java.util.*;

/**
 *
 */
public class BinaryExtensionConstraint implements Constraint{
  protected Variable v1;
  protected Variable v2;
  protected Set<BinaryTuple> autorizedValues;

  /**
   *
   * @param v1 est la premiere variable concernee par la contrainte
   * @param v2 est la deuxieme variable concernee par la contrainte
   */
  public BinaryExtensionConstraint(Variable v1, Variable v2){
    this.v1=v1;
    this.v2=v2;
    this.autorizedValues = new HashSet<>();

  }

  /**
   * permet d'ajouter un couple (de type BinaryTuple) de valeurs autorisees au Set autorizedValue
   * @param o1
   * @param o2
   */
  public void addTuple(Object o1, Object o2){
    this.autorizedValues.add(new BinaryTuple(o1,o2));
  }

  @Override
  public Set<Variable> getScope(){
    Set<Variable> tmp = new HashSet<>();
    tmp.add(v1);
    tmp.add(v2);
    return tmp;
  }

  @Override
  public boolean isSatisfiedBy(Map<Variable, Object> dico){
    if(!(dico.keySet().contains(this.v1)) || !(dico.keySet().contains(this.v2))){
      throw new IllegalArgumentException("Il manque une valeur de variable");
    }else{
      BinaryTuple b = new BinaryTuple(dico.get(this.v1),dico.get(this.v2));
      return this.autorizedValues.contains(b);
    }
  }
}
