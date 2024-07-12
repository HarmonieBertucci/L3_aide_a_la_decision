package datamining;
import java.util.*;

/**
 * 
 * @author 21900563
 *
 */
public interface ItemsetMiner{
  public BooleanDatabase getDatabase();
  
  /**
   * permet d'extraire les ItemSet fréquents de la base
   * @param frequenceMinimale
   * @return l'ensemble des itemsets (non vides) dans la base ayant une fréquence plus grande ou égale a frequenceMinimale
   */
  public Set<Itemset> extract(float frequenceMinimale);
}
