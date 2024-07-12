package solvers;
import java.util.*;
import representation.*;

/**
 * ArcConsistence optimise la manière de trouver une solution en supprimant des domaines les valeurs non viables sur les contraintes binaires
 * 
 * @author 21900563
 *
 */
public class ArcConsistency{
  protected Set<Constraint> constraints;

  /**
   * 
   * @param constraints un ensemble de contraintes
   */
  public ArcConsistency(Set<Constraint> constraints){
    this.constraints=constraints;

    //lance une exception en cas de contraintes avec une portée de taille supérieure strictement à 2
    for(Constraint c : constraints){
      if(c.getScope().size()>2){
        throw new IllegalArgumentException("message quelconque");
      }
    }
  }

  /**
   * permet de supprimer les valeurs val des domaines pour lesquelles il existe une contrainte unaire 
   * non satisfaite par v, et retourner false si au moins un domaine a été vidé
   * @param domaines un ensemble de domaines 
   * @return false si au moins un domaine a été vidé, true sinon
   */
  public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaines){
	  /**
	   * remplace permet de conserver les valeurs a supprimer dans un domaine pour ne pas obtenir un 
	   * ConcurrentModificationException en le faisant dans le for
	   */
      Set<Object> remplace; 
      Map<Variable,Object> instance;

      for(Variable variable : domaines.keySet()){ //on parcours les variables de l'ensemble de domaines 
        remplace= new HashSet<>(); //on vide (ou définit une première fois) remplace
        for(Object objet : domaines.get(variable)){ //on parcours le Set<Object> associé a la variable 
          instance = new HashMap<>();
          instance.put(variable,objet);
          for(Constraint contrainte : this.constraints){ //on vérifie pour chaque contrainte que l'instance la satisfait
            if(contrainte.getScope().size() == 1 && contrainte.getScope().contains(variable) && !contrainte.isSatisfiedBy(instance)){
              remplace.add(objet);//si l'instance ne satisfait pas la contrainte on rajoute l'objet a supprimer a remplace pour le supprimer plus tard
            }
          }
        }
        domaines.get(variable).removeAll(remplace);//après avoir regardé tous les objets liés a la variable on supprime de son domaine ceux qui sont dans remplace (qui ne satisfont pas les contraintes)
      }

      for(Variable v : domaines.keySet()){ //on parcours les variables et si le domaine de l'une d'entre elles est vide on renvoie false
        if(domaines.get(v).size()==0){
          return false;
        }
      }
      return true; //si l'on a pas renvoyé false alors aucun domaine n'est pas vides et on renvoie true
  }

  /**
   * supprime les valeurs (v1) dans domain1 pour qui il n'existe aucune valeur dans le domain2 les supportant 
   * pour toutes les contraintes portant sur les deux variables (var1 et var2)
   * @param var1 une première variable
   * @param domain1 le domaine de la première variable
   * @param var2 une deuxième variable
   * @param domain2 le domaine de la deuxième variable
   * @return si au moins une valeur a été supprimé de domain1
   */
  public boolean revise(Variable var1, Set<Object> domain1,Variable var2, Set<Object> domain2){
    boolean del=false; // permet de savoir si on a supprimé au moins une valeur dans domain1
    boolean viable; //permet de savoir si une instance est viable (satisfait toutes les contraintes)
    boolean toutSastisfait=false; // permet de savoir si une instance satisfait une contrainte
    Map<Variable,Object> instance;
    Set<Object> remplace=new HashSet<>(); 
    //remplace permet de conserver les valeurs a supprimer dans un domaine pour ne pas obtenir un 
	//concurrentModificationException en le faisant dans le for

    for(Object valeur : domain1){ //on parcours les valeurs du domaine1
      instance = new HashMap<>(); //on vide le HashMap (ou l'instancie)
      
      instance.put(var1,valeur); //on met la variable1 et la valeur que l'on regarde dans l'instance
      
      viable = false; //on (re)définit viable a false pour la mettre a true seulement si l'instance satisfait toutes les contraintes
      
      for(Object valeur2 : domain2){//on parcours les valeurs du domaine2
    	instance.put(var2,valeur2); //on met la variable2 et la valeur2 dans l'instance
    	
        toutSastisfait= true; //on (re)définit toutSatisfait a true pour n'avoir qu'a la mettre a false si une contrainte n'est pas satisfaite par l'instanciation
        
        for(Constraint contrainte : this.constraints){//on parcours les contraintes pour savoir si instance les satisfait 
          
        	if(contrainte.getScope().size() == 2 && contrainte.getScope().contains(var1) && contrainte.getScope().contains(var2) ){
            
        	  if(!contrainte.isSatisfiedBy(instance)){
        		  toutSastisfait =false; // si instance ne satisfait pas la contrainte on met toutSatisfait a false
        		  break; //si instance ne satisfait pas la contrainte elle n'est pas viable donc on fait un break
        	  }
        	}
        }
        if(toutSastisfait){
          viable = true;//comme l'instance satisfait toutes les contraintes on passe viable a true
          break;
        }
      }
      if(!viable){ //si l'instance n'est pas viable pour au moins une valeur du domaine2 on l'ajoute a remplace pour pouvoir la supprimer plus tard
        remplace.add(valeur);
        del = true; // on passe del a true pour dire qu'on va supprimer au moins une valeur du domaine 1
      }
    }
    for(Object o : remplace){ //on supprime toutes les valeurs du domaines 1 contenues dans remplace
      domain1.remove(o);
    }
    return del; //on renvoie del qui définit si on a supprimé au moins une valeur
  }

  /**
   * filtre tous les domaines en place en utilisant l'ArcConsistance, jusqu'à stabilité
   * @param domaines un ensemble de domaines
   * @return false si au moins un domaine a été vidé (true sinon)
   */
  public boolean ac1(Map<Variable, Set<Object>> domaines){
    if(!enforceNodeConsistency(domaines)){ //en appelant enforceNodeConsistency on vérifie si au moins un domaine a été vidé si c'est le cas on renvoie false
      return false;
    }
    boolean change = true; //on définit change a true
    while(change){ //tant que change est a true
      change=false;//on définit change a false
      for (Variable vi : domaines.keySet()) { //on parcours les variables vi du domaines
        for (Variable vj : domaines.keySet()) { //on parcours les variables vj du domaines
          if(!vi.equals(vj)){ //si les deux variables sont différentes
            if(revise(vi,domaines.get(vi),vj,domaines.get(vj))){ // et si en appelant revise une valeur a été retiré
              change=true; // on continue de parcourir les variables du domaine
            }
          }
        }
      }
    }

    for(Variable v : domaines.keySet()){ // pour chaque variable de l'ensemble des domaines
      if(domaines.get(v).isEmpty()){ // si son domaine est vide
        return false; // on renvoie false
      }
    }

    return true; // si on a pas renvoyé false avant ça c'est qu'aucun domaine n'a été vidé donc on renvoie true
  }
}
