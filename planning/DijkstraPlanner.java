package planning;
import representation.*;
import java.util.*;

/**
 * 
 * @author 21900563
 *
 */
public class DijkstraPlanner implements Planner{
    protected Map<Variable, Object> etatInitial;
    protected Set<Action> actions;
    protected Goal goal;

    /**
     * 
     * @param etat un état initial,
     * @param actions un ensemble d'actions possibles
     * @param goal un but
     */
  public DijkstraPlanner(Map<Variable, Object> etat ,Set<Action> actions, Goal goal){
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
    Map<Map<Variable, Object>, Double > distance = new HashMap<>(); //etat=>distance pour y aller
    LinkedList<Map<Variable, Object>> ouvert = new LinkedList<>(); //liste des ouverts / états a voir
    LinkedList<Map<Variable, Object>> goals = new LinkedList<>(); //liste des fois ou on a atteint de goal

    father.put(this.etatInitial,null); //on definit le pere de l'etat initial a null
    distance.put(this.etatInitial,0.0); //on definit la distance pour aller a l'etat initial a 0
    ouvert.add(this.etatInitial); //on ajoute l'état initial a la liste des ouverts

    Map<Variable, Object> instanciation; // etat Actuel
    Map<Variable, Object> next; // etat Futur
    
    if(this.goal.isSatisfiedBy(this.etatInitial)){ //si l'état initial satisfait le but
      return Collections.emptyList(); //on venvoie une liste vide
    }

    while(!ouvert.isEmpty()){ //tant que la liste des états a voir n'est pas vide
      
      instanciation = minimum(ouvert,distance); //on prend de la liste des ouverts l'état ayant la plus petite distance
      ouvert.remove(instanciation); //on l'enlève de la liste des ouverts
      
      if(this.goal.isSatisfiedBy(instanciation)){ //si l'état actuel satisfait le but
        goals.add(instanciation); //on ajoute l'état actuel a la liste
      }
      for(Action action : this.actions){ //on parcours la liste des actions
    	  
        if(action.isApplicable(instanciation)){ //si l'action est applicable a l'état actuel
        	
          next= action.successor(instanciation); // on met le successeur de l'état actuel dans next (état futur)
          
          if(!distance.keySet().contains(next) || distance.get(next)> distance.get(instanciation)+ action.getCost() ){ //si l'état futur n'a jamais été visité ou si la distance déja enregistrée dans la liste des distances est plus grande que celle actuelle
        	  
            distance.put(next,distance.get(instanciation)+ action.getCost()); //on ajoute/modifie la distance de l'état futur dans le dictionnaire des distances
            
            father.put(next,instanciation); //on définit l'état actuel comme pere de l'état futur
            
            plan.put(next,action); //on ajoute l'état futur et l'action y menant au plan
            
            ouvert.add(next); // on ajoute l'état suivant a la liste des ouverts
          }
        }

      }

    }
    if(goals.isEmpty()){ //si en sortant du while la liste des buts est vide ça veut dire qu'il n'y a pas de plan possible pour atteindre le goal
      return null; // du coup on renvoie null
    }
    else{ // si la liste des buts (plan a l'envers) n'est pas vide
      return get_dijkstra_plan(father ,plan,goals ,distance); //on renvoie le résultat de get_dijkstra_plan qui va remettre le plan a l'endroit
    }
  }

  /**
   * 
   * @param father dictionnaire (etat fils => etat pere)
   * @param plan plan a remettre a l'endroit
   * @param goals liste des fois ou on a atteint le goal
   * @param distance dictionnaire des distances pour aller aux états
   * @return le plan a l'endroit
   */
  public List<Action> get_dijkstra_plan (Map<Map<Variable, Object>,Map<Variable, Object>> father, Map<Map<Variable, Object>,Action> plan , LinkedList<Map<Variable, Object>> goals , Map<Map<Variable, Object>, Double > distance){
    LinkedList<Action> bfsPlan = new LinkedList<>();
    
    if(!plan.isEmpty()){ //si le plan n'est pas vide
    	
      Map<Variable, Object> goal=minimum(goals, distance); //on récupere la fois ou on a eu la plus petite distance pour aller au goal
      
      while(father.get(goal)!=null){ // tant que le pere de l'état que l'on regarde n'est pas null (tant que ce n'est pas l'état initial
    	  
        bfsPlan.addFirst(plan.get(goal)); //on ajoute l'état au début du plan
        
        goal = father.get(goal); //on regarde le pere du goal 
      }
      return bfsPlan; //une fois qu'on a le plan en entier on le renvoie
    }
    else{ //si le plan est vide
      return null; //on renvoie null
    }
  }


  /**
   * 
   * @param ouvert liste des états a comparer
   * @param distance liste des distances pour aller a ces états
   * @return l'état ayant la distance la plus petite
   */
  public Map<Variable, Object> minimum (LinkedList<Map<Variable, Object>> ouvert,   Map<Map<Variable, Object>, Double > distance){
    if(!ouvert.isEmpty()){
      Map<Variable, Object> minimum = ouvert.get(0);
      for(Map<Variable, Object> tmp : ouvert){
        if(distance.keySet().contains(minimum) && distance.get(minimum)> distance.get(tmp)){
          minimum=tmp;
        }
      }
      return minimum;
    }
    return null;
  }
}
