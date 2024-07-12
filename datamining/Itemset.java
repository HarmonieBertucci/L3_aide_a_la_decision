package datamining;
import representation.*;
import java.util.*;

/**
 * permet de représenter un ensemble de BooleanVariable et leur fréquence
 * @author 21900563
 *
 */
public class Itemset{
  protected Set<BooleanVariable> items;
  protected float frequence;

  /**
   * 
   * @param items un ensemble d'items 
   * @param frequence une fréquence (entre 0.0 et 1.0)
   */
  public Itemset(Set<BooleanVariable> items,float frequence){
    this.items=items;
    this.frequence=frequence;
  }

  public Set<BooleanVariable> getItems(){
    return this.items;
  }

  public float getFrequency(){
    return this.frequence;
  }


}
