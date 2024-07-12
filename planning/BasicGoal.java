package planning;

import representation.*;
import java.util.*;

/**
 * représente des buts spécifiés par une instanciation partielle des variables
 * @author 21900563
 *
 */
public class BasicGoal implements Goal{
	protected Map<Variable, Object> instantiationPartielle;

  	/**
  	 * 
  	 * @param instantiation une instanciation partielle des variables
  	 */
  	public BasicGoal(Map<Variable, Object> instantiation){
    this.instantiationPartielle = instantiation;
  	}
 
  	public Map<Variable, Object> getGoal(){
	  return this.instantiationPartielle;
  	}
  
  	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instantiation){ //est satisfait si l'instanciation affecte toutes les variables de l'instanciation partielle à la bonne valeur
    
	  if(instantiation.keySet().containsAll(this.instantiationPartielle.keySet())){ //on verifie que l'instanciation contient toutes les variables de l'instanciation partielle
    	
		  for(Variable variable : this.instantiationPartielle.keySet()){ //pour chaque variable dans l'instanciation partielle
        
			  if(!instantiation.get(variable).equals(this.instantiationPartielle.get(variable))){ // si la valeur de la variable dans l'instanciation est differente de celle de l'instanciation partielle
				  return false; //on renvoie false
			  }
		  }
		  return true; // si toutes les valeurs des variables dans l'instanciation partielle sont identiques dans l'instanciation on renvoie true
	  }
	  return false; //si toutes les variables de l'instanciation partielle ne sont pas contenues dans l'instanciation proposée on renvoie false
  	}
}
