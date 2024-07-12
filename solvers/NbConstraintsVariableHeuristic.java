package solvers;
import java.util.*;
import representation.*;

/**
 * permet de récupérer la variable apparaissant dans le plus de contraintes
 * @author 21900563
 *
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic{
  protected Set<Constraint> contraintes;
  protected boolean booleen;

  /**
   * 
   * @param contraintes un ensemble de contraintes
   * @param booleen un booléen indiquant si l'on préfère les variables apparaissant 
   * dans le plus de contraintes (true) ou dans le moins de contraintes (false)
   */
  public NbConstraintsVariableHeuristic(Set<Constraint> contraintes, boolean booleen){
    this.contraintes=contraintes;
    this.booleen = booleen;
  }

  public Set<Constraint> getContraintes(){
    return this.contraintes;
  }

  public boolean getBooleen(){
    return this.booleen;
  }

  @Override
  public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines){
    Variable best = null; //on définit la meilleure valeur a null
    
    int compteur =  this.booleen ?  0 : this.contraintes.size()+1 ; // booléen permettant pour chaque variable de savoir dans combien de contraintes elle est
    																// si le booleen est true on préfère les variables apparaissant dans le plus de contraintes alors on met le compteur a 0
    																//sinon on le met a (nombre total de contraintes)+1 
    
    for (Variable variable : variables){ //on parcours la liste des variables
    	
      if(this.booleen){ //si on veut la variable qui apparait dans le plus de contraintes
    	  
        if(nbConstraint(variable)> compteur ){ //on appelle la méthode définie en dessous pour savoir si elle a plus de contraintes que l'ancienne best
        	
          best = variable; //on définit la variable comme étant la best
          
          compteur=nbConstraint(variable); //on définit le compteur au nombre de contraintes portant sur la variable pour comparer les prochaines variables 
        }
      }
      else{ //sinon si on veut la variable qui apparait dans le moins de contraintes // idem que le if
    	  
        if(nbConstraint(variable)<= compteur ){ //on appelle la méthode définie en dessous pour savoir si elle a moins de contraintes que l'ancienne best
          best = variable;
          compteur=nbConstraint(variable);
        }
      }
    }
    return best; 
  }

  /**
   * permet de connaitre le nombre de contraintes qui portent sur la variable donnée en parametre
   * @param variable variable dont on veut connaitre le nombre de contraintes qui portent sur elle
   * @return le nombre de contraintes qui portent sur la variable donnée en parametre
   */
  public int nbConstraint(Variable variable){
    int compteur=0;
    for(Constraint contrainte : this.contraintes){ //on parcours la liste des contraintes 
      if(contrainte.getScope().contains(variable)){ //si la variable est dans le scope de la contrainte
        compteur++; //on augmente le compteur
      }
    }
    return compteur;
  }

}
