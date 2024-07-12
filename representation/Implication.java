package representation;
import java.util.*;

/**
 * permet de representer les contraintes de type l1 -> l2, ou l1 est soit une variable booleenne, soit la negation d'une variable booleenne, et de meme pour l2
 */
public class Implication implements Constraint{
  protected BooleanVariable l1;
  protected BooleanVariable l2;
  protected boolean b1;
  protected boolean b2;

  /**
   *
   * @param l1 est la premiere variable concernée par la contrainte
   * @param b1 permet de savoir si on prend l1 ou !l1
   * @param l2 est la deuxieme variable concernée par la contrainte
   * @param b2 permet de savoir si on prend l2 ou !l2
   */
  public Implication(BooleanVariable l1, boolean b1, BooleanVariable l2, boolean b2){
    this.l1=l1;
    this.l2=l2;
    this.b1=b1;
    this.b2=b2;
  }// l1 implique l2;

  @Override
  public Set<Variable> getScope(){
    Set<Variable> tmp = new HashSet<>();
    tmp.add(this.l1);
    tmp.add(this.l2);
    return tmp;
  }

  @Override
  public boolean isSatisfiedBy(Map<Variable, Object> dico){
    if(!(dico.keySet().contains(this.l1)) || !(dico.keySet().contains(this.l2))){
      throw new IllegalArgumentException("Il manque une valeur de variable");
    }
    else{
      if(this.b1 && this.b2){
        return (dico.get(this.l1).equals(false) || dico.get(this.l2).equals(true));
      }
      else if (!this.b1 && this.b2){
        return (dico.get(this.l1).equals(true) || dico.get(this.l2).equals(true));
      }
      else if (this.b1 && !this.b2){
        return (dico.get(this.l1).equals(false) || dico.get(this.l2).equals(false));
      }

      return (dico.get(this.l1).equals(true) || dico.get(this.l2).equals(false));
    }
  }
}
