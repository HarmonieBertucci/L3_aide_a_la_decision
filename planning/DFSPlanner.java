package planning;
import representation.*;
import java.util.*;

/**
 * recherche en profondeur
 * @author 21900563
 *
 */
public class DFSPlanner implements Planner{
  protected Map<Variable, Object> etatInitial;
  protected Set<Action> actions;
  protected Goal goal;

  /**
   * 
   * @param etat un état initial,
   * @param actions un ensemble d'actions possibles
   * @param goal un but
   */
  public DFSPlanner(Map<Variable, Object> etat ,Set<Action> actions, Goal goal ){
    this.etatInitial = etat;
    this.actions= actions;
    this.goal=goal;
  }

  public Map<Variable, Object> getInitialState(){
    return this.etatInitial;
  }

  public Set<Action> getActions(){
    return this.actions;
  }

  public  Goal getGoal(){
    return this.goal;
  }

  
  public List<Action> plan(){
    List<Action> plan = new LinkedList<>();
    List<Map<Variable, Object>> ferme = new LinkedList<>();
    return planRecursif(this.etatInitial, plan, ferme );
  }

  public List<Action> planRecursif(Map<Variable, Object> etat,List<Action> plan ,List<Map<Variable, Object>> ferme){
    if(this.goal.isSatisfiedBy(etat)){ //si le goal est satisfait par l'etat
      return plan; //on renvoie le plan
    }
    else{ //si le goal n'est pas satisfait par l'etat
      Map<Variable, Object> next; //prochain état
      List<Action> sousPlan; //sous plan dans lequel on enregistrera le resultat de l'appel récursif
      
      for(Action action : this.actions){ // pour chaque action dans la liste d'actions possibles
    	  
        if(action.isApplicable(etat)){ //si on peut faire l'action avec l'etat actuel
        	
          next= action.successor(etat); //on met le successeur de l'etat dans next
          
          if(!ferme.contains(next)){//si la liste des fermés ne contient pas déja next
        	  
            plan.add(action); //on ajoute l'action dans le plan
            
            ferme.add(next); //on ajoute next dans la liste des fermés
            
            sousPlan=planRecursif(next,plan,ferme); //on appelle plan recursif avec le nouvel etat, le nouveau plan et la nouvelle liste des fermés
            
            if(sousPlan!=null){ //si le resultat de la recursivité n'est pas null
              return sousPlan; //on renvoie le resultat
            }
            else{ //si le resultat de la recursivité est null
              plan.remove(plan.get(plan.size()-1)); //on enlève l'action du plan
            }
          }
        }
      }
      return null; //si pour toutes les action de la liste des actions possibles on n'a pas trouvé de plan, cela signifie qu'aucun plan existe pour atteindre le goal avec cet état initial et la liste des actions possibles 
    }

  }

}
