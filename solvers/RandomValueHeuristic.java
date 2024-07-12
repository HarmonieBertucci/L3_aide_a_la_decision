package solvers;
import representation.*;
import java.util.*;

/**
 * permet d'avoir une heuristique aléatoire
 * @author 21900563
 *
 */
public class RandomValueHeuristic implements ValueHeuristic{
  protected Random random;

  /**
   * 
   * @param random un générateur aléatoire
   */
  public RandomValueHeuristic(Random random){
    this.random=random;
  }

  @Override
  public List<Object> ordering(Variable variable, Set<Object> domaine){
    int item;
    
    List<Object> listeOrdering = new LinkedList<Object>(); //liste que l'on va renvoyer
    
    Object[] tmp=domaine.toArray();//on transforme la liste des domaines en array
    
    while(listeOrdering.size()<domaine.size()){ //tant que la liste que l'on va renvoyer n'a pas la même taille que la liste des domaines
       item=this.random.nextInt(domaine.size()); //on tire un chiffre aléatoire entre 0 et la taille de la liste des domaines
       if(!listeOrdering.contains(tmp[item])){ //si la liste ne contient pas déja l'élément d'indice item
         listeOrdering.add(tmp[item]); // on le rajoute dans la liste
       }
    }
    return listeOrdering ;
  }
}
