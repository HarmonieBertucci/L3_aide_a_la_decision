package planning;
import representation.*;
import java.util.*;

/**
 * permet de faire des plans
 * @author 21900563
 *
 */
public interface Planner {
  /**
   * 
   * @return la liste d'action (le plan)
   */
  public List<Action> plan();

  public Map<Variable, Object> getInitialState();

  public Set<Action> getActions();

  public  Goal getGoal();
}
