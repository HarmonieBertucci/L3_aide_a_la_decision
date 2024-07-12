package solvers;
import java.util.*;
import representation.*;

/**
 * Classe abstraite implementant solver, permettant de ne pas réécrire la méthode isConsistent dans toutes les classes 
 * implémentant Solver (ces classes étendent donc AbstractSolver)
 * @author 21900563
 *
 */
public class AbstractSolver implements Solver{
  protected Set<Variable> variables;
  protected Set<Constraint> contraintes;

  /**
   * constructeur d'AbstractSolver
   * @param varia un ensemble de variables (Set<Variable>)
   * @param contr un ensemble de contraintes (Set<Constraint>)
   */
  public AbstractSolver(Set<Variable> varia,Set<Constraint> contr){
    this.variables=varia;
    this.contraintes=contr;
  }
  
  @Override
  public Map<Variable, Object> solve(){
	  return null;
  }

  /**
   * @param instanciation une affectation partielle des variables
   * @return un booléen indiquant si instanciation satisfait toutes les contraintes qui portent sur des variables instanciées dans l'affectation (consistance locale)
   */
  public boolean isConsistent(Map<Variable,Object> instanciation){
    for(Constraint c : this.contraintes){
      if(instanciation.keySet().containsAll(c.getScope())){
        if(!c.isSatisfiedBy(instanciation)){
          return false ;
        }
      }
    }
    return true;
  }

}
