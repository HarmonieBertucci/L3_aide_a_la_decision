package planning;
import representation.*;
import java.util.*;

public interface Heuristic{
	
  /**
   * 
   * @param etat
   * @return l'estimation de la valeur de l'état
   */
  public float estimate(Map<Variable, Object> etat);
}
