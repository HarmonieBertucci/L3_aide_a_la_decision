package planning;
import representation.*;
import java.util.*;

/**
 * 
 * @author 21900563
 *
 */
public class AStarPlanner implements Planner{
  protected Map<Variable, Object> etatInitial;
  protected Set<Action> actions;
  protected Goal goal;
  protected Heuristic heuristique;

  /**
   * 
   * @param etat état initial
   * @param actions liste d'actions possibles
   * @param goal but a atteindre
   * @param heuristique règles de choix de variables ou de valeur dans les dommaines
   */
  public AStarPlanner(Map<Variable, Object> etat ,Set<Action> actions, Goal goal,Heuristic heuristique){
    this.etatInitial = etat;
    this.actions= actions;
    this.goal=goal;
    this.heuristique=heuristique;
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
    
    Map<Map<Variable, Object>, Float > distance = new HashMap<>(); //etat=>distance pour y aller
    
    Map<Map<Variable, Object>, Float > value = new HashMap<>(); //etat=>valeur de l'état

    LinkedList<Map<Variable, Object>> ouvert = new LinkedList<>(); //liste des ouverts / états a voir
    

    ouvert.add(this.etatInitial); //on ajoute l'état initial a la liste des ouverts
    
    father.put(this.etatInitial,null); //on définit a null le pere de l'état initial
    
    distance.put(this.etatInitial,(float) 0); //on définit la distance pour aller a l'état initial a 0
    
    value.put(this.etatInitial,this.heuristique.estimate(this.etatInitial)); // on définit la valeur de l'état initial au resultat de l'éstimation selon l'heuristique
    

    Map<Variable, Object> instanciation; // état actuel
    Map<Variable, Object> next=null; //état futur

    if(this.goal.isSatisfiedBy(this.etatInitial)){ //si le goal est satisfait par l'état initial
      return Collections.emptyList(); //on renvoie une liste vide
    }
    while(!ouvert.isEmpty()){ //tant que la liste des états a voir n'est pas vide
    	
      instanciation= minimum(ouvert,value); //on met le premier état de la liste des ouverts comme état actuel 
      
      if(this.goal.isSatisfiedBy(instanciation)){ //si le but est satisfait par l'état
        return planAux(father,plan,instanciation); //on appelle planAux pour remettre en ordre le plan
      }
      else{ //si le but n'est pas satisfait par l'état
    	  
        ouvert.remove(instanciation); //on enlève l'état de la liste des états a voir
        
        for(Action action : this.actions){ //on parcours la liste des actions disponibles
        	
          if(action.isApplicable(instanciation)){ //si l'action est applicable a l'état
        	  
            next= action.successor(instanciation); // on met le successeur de l'état actuel dans next (état futur)
            
            if(!distance.keySet().contains(next) || distance.get(next) > distance.get(instanciation) + action.getCost()){ // //si l'état futur n'a jamais été visité ou si la distance déja enregistrée dans la liste des distances est plus grande que celle actuelle plus le cout de l'action
              
              distance.put(next,distance.get(instanciation) + action.getCost()); //on ajoute au dictionnaire des distances l'état next et (sa distance  + son cout)
              
              value.put(next,distance.get(next) + this.heuristique.estimate(next)); //on ajoute dans le dictionnaire des valeurs l'état next et (sa distance + l'estimation de sa valeur par l'heuristique)
              
              father.put(next,instanciation); //on définit l'état actuel comme étant le père de l'état next (état futur)
              
              plan.put(next,action); //on ajoute au plan l'état futur et l'action qui y a mené
              
              ouvert.add(next); //on ajoute l'état next a la liste des ouverts (états a voir)
            }
          }
        }
      }
    }
    return null; // si en sortant du while on n'a pas encore renvoyé un plan alors il n'existe pas de plans pour atteindre le goal et on renvoie null
  }

  /**
   * 
   * @param father dictionnaire (etat fils => etat pere)
   * @param plan plan a remettre a l'endroit
   * @param goal le but a atteindre
   * @return le plan a l'endroit
   */
  public List<Action> planAux(Map<Map<Variable, Object>,Map<Variable, Object>> father,Map<Map<Variable, Object>,Action> plan ,Map<Variable, Object> goal){
	    LinkedList<Action> bfsPlan = new LinkedList<>();
	    while(father.get(goal)!=null){ //tant que le pere de l'état que l'on regarde n'est pas null (tant que ce n'est pas l'état initial
	      
	      bfsPlan.addFirst(plan.get(goal)); //on ajoute l'état au début du plan
	      
	      goal = father.get(goal); //on regarde le pere du goal 
	    }
	    return bfsPlan; //une fois qu'on a le plan en entier on le renvoie
	  }

  
  /**
   * idem que dans Dijkstra
   * @param ouvert liste des états a comparer
   * @param distance liste des distances pour aller a ces états
   * @return l'état ayant la distance la plus petite
   */
  public Map<Variable, Object> minimum (LinkedList<Map<Variable, Object>> ouvert,   Map<Map<Variable, Object>, Float > valeurs){
    if(!ouvert.isEmpty()){
      Map<Variable, Object> minimum = ouvert.get(0);
      for(Map<Variable, Object> tmp : ouvert){
        if(valeurs.keySet().contains(minimum) && valeurs.get(minimum)> valeurs.get(tmp)){
          minimum=tmp;
        }
      }
      return minimum;
    }
    return null;
  }


}
