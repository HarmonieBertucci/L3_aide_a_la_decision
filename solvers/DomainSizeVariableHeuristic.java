package solvers;
import java.util.*;
import representation.*;

/**
 * permet de récupérer la variable ayant le plus grand/le plus petit domaine
 * @author 21900563
 *
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic{
  protected boolean booleen;

  /**
   * 
   * @param booleenindique si l'on préfère les variables avec le plus grand 
   * domaine (true) ou avec le plus petit domaine (false)
   */
  public DomainSizeVariableHeuristic(boolean booleen){
    this.booleen = booleen;
  }


  public boolean getBooleen(){
    return this.booleen;
  }

  @Override
  public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines){
      Variable best =(Variable) variables.toArray()[0]; //on définit la premiere variable comme étant la meilleure

      for(Variable var:  variables){//on parcours les variables
    	  
        if(this.booleen){ //si on veut la variable avec le plus grand domaine
        	
          if(domaines.get(best).size() < domaines.get(var).size()){ // si var a un plus grand domaine que best
            best = var; //best devient var
          }
        }
        else{ //si on veut la variable avec le plus petit domaine
          if(domaines.get(best).size() > domaines.get(var).size() ){ // si var a un plus petit domaine que best
            best = var; //best devient var
        
          }
        }
      }
      return best;
  }
}
