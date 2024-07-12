package solvers;
import java.util.*;
import representation.*;

/**
 * Permet de regrouper les différents solvers
 * @author 21900563
 *
 */
public interface Solver{
	
	/**
	 * 
	 * @return une solution (de type Map<Variable, Object>), ou null s'il n'y en a pas 
	 */
	public Map<Variable, Object> solve();
}
