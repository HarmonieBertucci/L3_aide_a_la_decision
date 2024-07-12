package datamining;
import representation.*;
import java.util.*;

/**
 * représente des bases de données transactionnelles
 * @author 21900563
 *
 */
public class BooleanDatabase{
  protected Set<BooleanVariable> items;
  protected List<Set<BooleanVariable>> transactions;

  /**
   * 
   * @param items ensemble d'items de type BooleanVariable
   */
  public BooleanDatabase(Set<BooleanVariable> items){
    this.items=items;
    this.transactions=new LinkedList<>(); //base vide
  }
  
  public Set<BooleanVariable> getItems(){
    return this.items;
  }

  public List<Set<BooleanVariable>> getTransactions(){
    return this.transactions;
  }

  /**
   * 
   * @param item liste de BooleanVariable / transaction a ajouter a la base
   */
  public void add(Set<BooleanVariable> item){
    this.transactions.add(item);
  }

}
