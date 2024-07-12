package planning;
import representation.*;
import java.util.*;

/**
 * représente le but/ la solution
 * @author 21900563
 *
 */
public interface Goal{
	public Map<Variable, Object> getGoal();
	
	/**
	 * @param instantiation 
	 * @return si l'instanciation satisfait le goal/but
	 */
	public boolean isSatisfiedBy(Map<Variable, Object> instantiation);
}
