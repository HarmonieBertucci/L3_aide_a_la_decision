package planning;
import representation.*;
import java.util.*;

/**
 * recherche en largeur
 * @author 21900563
 *
 */
public class BFSPlanner implements Planner{
  protected Map<Variable, Object> etatInitial;

  protected Set<Action> actions;

  protected Goal goal;

  /**
   * 
   * @param etat un état initial,
   * @param actions un ensemble d'actions possibles
   * @param goal un but
   */
  public BFSPlanner(Map<Variable, Object> etat ,Set<Action> actions, Goal goal ){
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
    Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>(); // etatActuel=>etatPere
    Map<Map<Variable, Object>,Action> plan = new HashMap<>(); //etat =>action
    
    LinkedList<Map<Variable, Object>> ouvert = new LinkedList<>(); //liste des ouverts / états a voir
    
    LinkedList<Map<Variable, Object>> ferme = new LinkedList<>(); //liste des fermés / états déjà vus
    
    father.put(this.etatInitial,null); //on definit le pere de l'etat initial a null
    
    ouvert.add(this.etatInitial); // on ajoute l'etat initial a la liste des ouverts
    
    ferme.add(this.etatInitial);// on ajoute l'etat initial a la liste des fermés

    Map<Variable, Object> instanciation; // etat Actuel
    Map<Variable, Object> next; // etat Futur

    if(this.goal.isSatisfiedBy(this.etatInitial)){ //si le goal est satisfait par l'état initial
      return new LinkedList<>(); //on renvoie une liste vide
    }
    while(!ouvert.isEmpty()){ //tant que la liste des ouverts n'est pas vide
    	
      instanciation = ouvert.getFirst(); //on définit l'etat actuel comme étant le premier etat dans la liste des ouverts
      
      ouvert.remove(instanciation); // on l'enleve de la liste des ouverts
      
      ferme.add(instanciation); // on l'ajoute a la liste des fermés
      
      for(Action action : this.actions){ //pour chaque action dans la liste des ouverts
    	  
        if(action.isApplicable(instanciation)){ //si l'action est applicable pour l'instanciation
        	
          next= action.successor(instanciation); // on donne a l'état futur le successeur de l'instanciation après avoir effectué l'action
          
          if(!ferme.contains(next) && !ouvert.contains(next)){ //si next n'est contenu ni dans la liste des états a voir ni dans la liste desétats déjà vus
            
        	father.put(next,instanciation); // on définit que l'instanciation est le pere de next dans father
        	
            plan.put(next,action); //on ajoute l'état futur et l'action qui y a mené au plan
            
            if(this.goal.isSatisfiedBy(next)){ //si le goal est satisfait par l'état futur 
              return planAux(father, plan, next); //on appelle planAux qui va remettre le plan dans le bon sens
            }
            else{ //si le goal n'est pas satisfait par l'état futur 
              ouvert.add(next); //on ajoute l'état futur a la liste des états a voir
            }
          }
        }
      }
    }
    return null; //si aucun plan n'a été trouvé pour répondre au goal on renvoie false
  }

  /**
   * remet le plan dans le bon sens
   * @param father map permettant d'acceder au pere d'un état
   * @param plan le plan que l'on veut remettre dans le bon sens
   * @param goal le goal qui va nous permettre de remonter en prenant son père dans le plan
   * @return le plan a l'endroit
   */
  public List<Action> planAux(Map<Map<Variable, Object>,Map<Variable, Object>> father,Map<Map<Variable, Object>,Action> plan ,Map<Variable, Object> goal){
    LinkedList<Action> bfsPlan = new LinkedList<>(); //on crée une liste d'actions
    
    while(father.get(goal)!=null){//tant que le père de goal n'est pas null
      
      bfsPlan.addFirst(plan.get(goal)); //on ajoute le goal au début de la liste 
      
      goal = father.get(goal); // et le nouveau goal devient le pere du précédant goal
    }
    return bfsPlan;
  }

}
