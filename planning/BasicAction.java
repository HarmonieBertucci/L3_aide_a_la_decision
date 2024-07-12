package planning;
import representation.*;
import java.util.*;

/**
 * permet de représenter des actions dont la précondition et l'effet sont des instanciations partielles des variables
 * @author 21900563
 *
 */
public class BasicAction implements Action{
  protected Map<Variable, Object> precondition;
  protected Map<Variable, Object> effet;
  protected int cout;
  
  /**
   * 
   * @param precondition précondition pour effectuer l'action
   * @param effet effet de l'action
   * @param cout cout de l'action
   */
  public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effet, int cout){
    this.precondition=precondition;
    this.effet=effet;
    this.cout=cout;
  }

  @Override
  public boolean isApplicable(Map<Variable, Object> etat){ //est applicable si l'état affecte toutes les variables de la précondition à la bonne valeur
    if(etat.keySet().containsAll(this.precondition.keySet())){ // si l'état contient toutes les variables de précondition
      for(Variable variable : this.precondition.keySet()){ //pour chaque variables des préconditions
        if(!etat.get(variable).equals(this.precondition.get(variable))){ //si la valeur de la variable dans l'etat est différente de celle dans la précondition 
          return false; // on renvoie false
        }
      }
      return true; //si toutes les valeurs des variables sont correctes on renvoie true
    }
    return false; //si l'état ne contient pas toutes les variables de précondition
  }

  @Override
  public Map<Variable, Object> successor(Map<Variable, Object> etat){
    Map<Variable, Object> etatSuccessor=new HashMap<>(etat); //l'etat successeur est identique a l'état courrant
    for(Variable variable : this.effet.keySet()){ //mais pour chaque variables dans l'effet
        etatSuccessor.put(variable,this.effet.get(variable)); //on lui attribut la valeur dans l'effet
    }
    return etatSuccessor;
  }

  @Override
  public int getCost(){
    return this.cout;
  }
}
