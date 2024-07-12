package solvers;
import java.util.*;
import representation.*;

/**
 * Classe étendant AbstractSolver
 * 
 * Backtrack retourne en arrière quand la valeur choisie pour la variable en cours d’instanciation 
 * n’est pas validée avec l’instanciation partielle sur l’ensemble des contraintes possibles
 * 
 * L'espace de recherche est réduit uniquement sur des instanciations partielles satisfaisant 
 * les contraintes avec les variables déjà instanciées
 * 
 * @author 21900563
 *
 */
public class BacktrackSolver extends AbstractSolver{
	
 /**
  * constructeur de BacktrackSolver
  * @param varia un ensemble de variables
  * @param contr un ensemble de contraintes
  */
 public BacktrackSolver (Set<Variable> varia,Set<Constraint> contr){
    super(varia,contr);
  }

  @Override
  public Map<Variable, Object> solve(){
    LinkedList<Variable> variable=new LinkedList<Variable>();
    for(Variable v : this.variables){
      variable.add(v);
    }
    Map<Variable, Object> instanciation = new HashMap<Variable, Object>();
    return solveRecursive(instanciation, variable);
  }

  /**
   * méthode se rappelant récursivement pour construire une instanciation complète respectant les contraintes
   * @param instanciation une solution partielle
   * @param variables une liste de variables non instanciées
   * @return une solution arbitraire étendant la solution partielle, ou null s'il n'y en a pas
   */
  public Map<Variable, Object> solveRecursive(Map<Variable, Object> instanciation, LinkedList<Variable> variables){
    if(variables.size() == 0){
      return instanciation;
    }
    Map<Variable, Object> n;
    Variable v=variables.get(0);
    variables.remove(v);
    for(Object val : v.getDomain()){
      n = new HashMap<>(instanciation);
      n.put(v,val);

      if(isConsistent(n)){
        Map<Variable, Object> r = solveRecursive(n,variables);
        if(r != null){
          return r;
        }
      }
    }

    variables.addFirst(v);
    return null;
  }
}
