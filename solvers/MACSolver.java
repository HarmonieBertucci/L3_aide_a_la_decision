package solvers;

import java.util.*;
import representation.*;

/**
 * 
 * @author 21900563
 *
 */
public class MACSolver extends AbstractSolver{

	/**
	 * 
	 * @param variables un ensemble de variables
	 * @param contraintes un ensemble de contraintes 
	 */
  public MACSolver (Set<Variable> variables, Set<Constraint> contraintes ){
    super(variables,contraintes);
  }

  @Override
  public Map<Variable, Object> solve(){
    LinkedList<Variable> variable=new LinkedList<Variable>(); //on crée une linkedList pour les variables pour pouvoir ajouter et supprimer facilement les variables a l'interieur
    Map<Variable, Object> instanciation = new HashMap<Variable, Object>(); //instanciation a vérifier
    Map<Variable, Set<Object>> domaines= new HashMap<Variable, Set<Object>>(); //liste des (Variable=>Domaine)
    Set<Object> valeurs; 
    
    for(Variable v : this.variables){ //on parcours le set des variables pour les ajouter dans la liste
      variable.add(v);
      valeurs= new HashSet<>(v.getDomain());
      domaines.put(v,valeurs);
    }
    
    return macRecursif(instanciation, variable,domaines); 

  }

  /**
   * 
   * @param instancePartielle instanciation vide (au premier appel) a completer
   * @param variableNonInstanciees liste des variables a instancier
   * @param domaines map permettant d'avoir le domaine de chaque variable
   * @return une instanciation complête ou null si aucune solution n'est possible
   */
  public Map<Variable, Object> macRecursif(Map<Variable, Object> instancePartielle, LinkedList<Variable> variableNonInstanciees,Map<Variable, Set<Object>> domaines ){
    if(variableNonInstanciees.isEmpty()){ //si la liste des variables non instanciées est vide on renvoie l'instanciation
      return instancePartielle;
    }
    else{
      ArcConsistency ac = new ArcConsistency(this.contraintes); //on crée un solver Ac1 avec la liste des contraintes
      
      if(!ac.ac1(domaines)){ //si au moins un des domaine est vidé en utilisant ac1 on renvoie false
        return null;
      }
      Variable x = variableNonInstanciees.get(0); //on prend la premiere variable des variable non instanciées
      
      variableNonInstanciees.remove(x); //on l'enlève de la liste
      
      Map<Variable, Object> newInstance; // on crée une nouvelle instanciation
      
      for(Object valeur : domaines.get(x)){ //on parcours les valeurs contenues dans le domaine de la variable
    	  
        newInstance = new HashMap<>(instancePartielle); //on ajoute a la nouvelle instanciation le contenue de l'instanciation partielle
        
        newInstance.put(x,valeur); //on ajoute en plus la variable et sa valeur
        
        if(isConsistent(newInstance)){//si toutes les contraintes qui portent sur les variables de la nouvelle instanciation sont satisfaites (consistance locale)
        	
          Map<Variable, Object> instanciation = macRecursif(newInstance,variableNonInstanciees,domaines); //on rappelle récursivement la méthode avec la nouvelle instanciation
          
          if (instanciation!=null){ //si l'instanciation résultant de la récursivité n'est pas nulle
            return instanciation; //on retourne l'instanciation
          }
        }
      }
      variableNonInstanciees.add(x); //on remet la variable dans les variables non instanciées
      return null;
    }
  }




}
