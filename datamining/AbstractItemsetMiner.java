package datamining;
import representation.*;
import java.util.*;

/**
 * 
 * @author 21900563
 *
 */
public class AbstractItemsetMiner implements ItemsetMiner{
  public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName()); //permet de comparer les items (pour les instances de SortedSet<BooleanVariable>)
  protected BooleanDatabase base;
  
  /**
   * 
   * @param base une base 
   */
  public AbstractItemsetMiner(BooleanDatabase base){
    this.base=base;
  }

  public BooleanDatabase getDatabase(){
    return this.base;
  }

  /**
   * 
   * @param items un ensemble d'items
   * @return la fréquence d'items dans la base
   */
  public float frequency(Set<BooleanVariable> items){
	  
    int compteur = 0; // on définit le compteur a 0 
    
    for(Set<BooleanVariable> transaction : this.base.getTransactions()){ // pour chaque transaction dans la liste de la base
      if( transaction.containsAll(items)){ // si la transaction contient tous les elements de l'ensemble d'items
        compteur++; //on incrémente le compteur
      }
    }
    float res = (float) compteur / this.base.getTransactions().size(); // res = fréquence les items dans la base
    
    if(items.isEmpty()){ //si la liste des items est vide 
      return 1; //on renvoie 1
    }
    
    return res; //on renvoie la fréquence
  }

  
  public Set<Itemset> extract(float frequenceMinimale){
    Set<Itemset> res=new HashSet<>();
    Set<BooleanVariable> items;
    for(BooleanVariable item : this.base.getItems()){ //pour chaque item de la base
      items = new HashSet<>(); 
      items.add(item); //on ajoute l'item a une liste
      if(frequency(items)>=frequenceMinimale){ // si la fréquence de cette liste est plus grande que la fréquence minimale
        res.add(new Itemset(items,frequency(items))); // on ajoute au resultat la liste et sa fréquence (itemset)
      }
    }
    return res; // on renvoie la liste d'itemset ayant une fréquence superieure a frequenceMinimale
  }
}
