package solvers;

import java.util.*;
import representation.*;

public class HeuristicMACSolver extends AbstractSolver{
  protected ValueHeuristic valueHeuristic;
  protected VariableHeuristic variableHeuristic;

  /**
   * 
   * @param variables un ensemble de variables
   * @param contraintes un ensemble de contraintes
   * @param variableHeuristic une heuristique sur les variables
   * @param valueHeuristic une heuristique sur les valeurs
   */
  public HeuristicMACSolver (Set<Variable> variables, Set<Constraint> contraintes, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic){
    super(variables,contraintes);
    this.variableHeuristic = variableHeuristic;
    this.valueHeuristic = valueHeuristic;
  }


    @Override
    public Map<Variable, Object> solve(){

      Map<Variable, Object> instanciation = new HashMap<Variable, Object>(); //instanciation vide
      Map<Variable, Set<Object>> domaines= new HashMap<Variable, Set<Object>>(); 
      Set<Object> valeurs;
      for(Variable v: this.variables){ //permet de mettre les variable et leur domaine dans le map domaines
        valeurs= new HashSet<>(v.getDomain());
        domaines.put(v,valeurs);
      }
      Set<Variable> variable = new HashSet<>(this.variables);
      return heuristicMacRecursif(instanciation, variable,domaines);

    }

    public Map<Variable, Object> heuristicMacRecursif(Map<Variable, Object> instancePartielle, Set<Variable> variableNonInstanciees,Map<Variable, Set<Object>> domaines ){
    	
      //idem que MACSolver mais optimisée selon l'heuristique
    	
      if(variableNonInstanciees.isEmpty()){
        return instancePartielle;
      }
      else{
        ArcConsistency ac = new ArcConsistency(this.contraintes);
        if(!ac.ac1(domaines)){
          return null;
        }
        Variable x = this.variableHeuristic.best(variableNonInstanciees,domaines); //on récupère la meilleure variable selon l'heuristique
        variableNonInstanciees.remove(x); 
        Map<Variable, Object> newInstance;
        List<Object> domaineRandom = this.valueHeuristic.ordering(x,domaines.get(x)); //ordonne le domaine de la variable selon l'heuristique
        for(Object valeur : domaineRandom){
          newInstance = new HashMap<>(instancePartielle);
          newInstance.put(x,valeur);
          if(isConsistent(newInstance)){
            Map<Variable, Object> instanciation = heuristicMacRecursif(newInstance,variableNonInstanciees,domaines);
            if (instanciation!=null){
              return instanciation;
            }
          }
        }
        variableNonInstanciees.add(x);
        return null;
      }

    }



}
