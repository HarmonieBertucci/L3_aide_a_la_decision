package planning;
import representation.*;
import java.util.*;

public interface Action{
  /**
   * 
   * @param etat état d'origine
   * @return si l'action est applicable a l'état
   */
  public boolean isApplicable(Map<Variable, Object> etat);
  
  /**
   * 
   * @param etat état d'origine
   * @return l'état après avoir fait l'action
   */
  public Map<Variable, Object> successor(Map<Variable, Object> etat);
  
  /**
   * 
   * @return le cout de l'action
   */
  public int getCost();
}
