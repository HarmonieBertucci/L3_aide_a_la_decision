package solvers;
import representation.*;
import java.util.*;

public interface VariableHeuristic {
	
  /**
   * @param variables un ensemble de variables
   * @param domaines un ensemble de domaines
   * @return la meilleure valiable au sens de l'heuristique
  */
  public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines);
  
}
