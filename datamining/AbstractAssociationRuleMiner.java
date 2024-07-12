package datamining;
import representation.*;
import java.util.*;

/**
 * permet de définir des méthodes utilisables dans toutes les classes l'étendant sans avoir a les réécrires dans chacunes
 * @author 21900563
 *
 */
public class AbstractAssociationRuleMiner implements  AssociationRuleMiner{
	
  protected BooleanDatabase database; // la base de données 
  
  public AbstractAssociationRuleMiner(BooleanDatabase base){
    this.database=base;
  }

  public BooleanDatabase getDatabase(){
    return this.database;
  }

  /**
   * 
   * @param items l'ensemble d'items dont on veut connaitre la fréquence
   * @param itemsets l'ensemble des itemsets ou on va chercher la fréquence
   * @return la fréquence de l'ensemble d'items telle qu'elle est stockée dans l'ensemble d'itemsets
   */
  public static float frequency(Set<BooleanVariable> items,Set<Itemset> itemsets){
    for(Itemset itemset : itemsets){ //pour chaque itemset dans l'ensemble
    	
      if(itemset.getItems().equals(items)){ // si l'item est égal a celui que l'on cherche
    	  
        return itemset.getFrequency(); //on renvoie sa fréquence
      }
    }
    return 0; //si on a pas trouvé sa fréquence on renvoie 0
  }

  /**
   * 
   * @param premise premisse candidate
   * @param conclusion conclusion candidate
   * @param itemsets un ensemble d'itemsets 
   * @return la confiance de la règle d'association de prémisse et conclusion données, les fréquences d'itemsets nécessaires aux calculs étant données dans l'ensemble d'itemsets
   */
  public static float confidence(Set<BooleanVariable> premise,Set<BooleanVariable> conclusion,Set<Itemset> itemsets){
    Set<BooleanVariable> tmp = new HashSet<>(conclusion); // on ajoute dans un ensemble la conclusion
    
    tmp.addAll(premise); // on ajoute dans le même ensemble la premise
    
	return (float) frequency(tmp,itemsets)/frequency(premise,itemsets); //on renvoie la fréquence de l'ensemble divisé par la fréquence de la premise
  }

  public Set<AssociationRule> extract(float frequenceMinimale,float confianceMinimale){
    return null; //va être redéfinie dans chaque classe étendant AbstractAssociationRuleMiner
  };


}
