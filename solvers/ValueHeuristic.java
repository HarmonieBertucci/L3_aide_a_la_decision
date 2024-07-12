package solvers;
import representation.*;
import java.util.*;

public interface ValueHeuristic {
	
  /**
   * 
   * @param variable une variable
   * @param domaine son domaine 
   * @return une liste contenant les valeurs du domaine, ordonnées selon l'heuristique (la meilleure en premier)
   */
  public List<Object> ordering(Variable variable, Set<Object> domaine);

}
