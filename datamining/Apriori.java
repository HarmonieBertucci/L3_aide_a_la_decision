package datamining;
import representation.*;
import java.util.*;

/**
 * cette classe représentera des extracteurs fonctionnant sur le principe de l'algorithme apriori
 * @author 21900563
 *
 */
public class Apriori extends AbstractItemsetMiner{
  public Apriori(BooleanDatabase base){
    super(base);
  }

  /**
   * 
   * @param frequence une fréquence
   * @return l'ensemble de tous les itemset singletons de la base ayant une fréquence superieure ou égale a frequence
   */
  public Set<Itemset> frequentSingletons(float frequence){
    Set<Itemset> ensemble = new HashSet<>();
    for(Itemset item : super.extract(frequence)){ // on parcours les items dans la liste renvoyé par extract
    	
      if(item.getItems().size()==1){ // si l'item est un singleton
    	  
        ensemble.add(item); //on l'ajoute a la liste que l'on va renvoyer
      }
    }
    return ensemble;
  }

  /**
   * conditions /!\ :
   * 	les deux ensembles doivent avoir la même taille k
   *    les deux ensembles doivent avoir les mêmes k-1 premiers items
   *    les deux ensembles doivent avoir des ke items différents
   *    
   * @param ens1 ensemble trié 1
   * @param ens2 ensemble trié 2
   * @return un ensemble trié les combinant selon les conditions /!\
   */
  public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> ens1, SortedSet<BooleanVariable> ens2){
    int taille=ens1.size(); //taille de l'ensemble 1
    int taille2=ens2.size(); //taille de l'ensemble 2
    
    if(taille!=0 && taille2!=0 && taille==taille2){ //si les tailles sont égales (condition 1) et si les ensembles ne sont pas vide
      
      if(ens1.headSet(ens1.last()).equals(ens2.headSet(ens2.last()))){ //si les deux ensembles ont les mêmes taille-1 premiers items
    	  
        if(!ens1.last().equals(ens2.last())){ // si les derniers éléments des deux ensembles sont différents
        	
          ens1.addAll(ens2);//on ajoute tous les éléments de l'ensemble 2 dans l'ensemble 1 
          
          return ens1; //et on renvoie l'ensemble 1
        }
      }
    }
    return null;
  }

  /**
   * 
   * @param ensembleItems un ensemble d'items 
   * @param collectionEnsembleItems une collection d'ensemble d'items 
   * @return si tous les sous-ensembles obtenus en supprimant exactement un élément de l'ensemble d'items sont contenus dans la collection
   */
  public static boolean allSubsetsFrequent(Set<BooleanVariable> ensembleItems, Collection<SortedSet<BooleanVariable>> collectionEnsembleItems){
    Set<BooleanVariable> tmp=new HashSet<>(ensembleItems) ; // liste temporaire
    
    for(BooleanVariable variable : ensembleItems){ // on parcours l'ensemble d'items pour regarder les variables
    	
        tmp= new HashSet<>(ensembleItems); //on ajoute l'ensemble dans la liste temporaire
        
        tmp.remove(variable); //puis on lui enlève la variable
        
        if(!collectionEnsembleItems.contains(tmp)){ //si la collection ne contient pas la liste privée de variable
          return false; //on renvoie false 
        }
    }

    return true; //si on n'a pas renvoyé false alors la collection contient tous les sous-ensembles obtenus en supprimant exactement un élément de l'ensemble d'items
  }

  @Override
  public Set<Itemset> extract(float frequenceMinimale){

	List<SortedSet<BooleanVariable>> dejaVu= new ArrayList<>(); //liste nous permettant de savoir si on a déjà vu un ensemble d'items

    List<SortedSet<BooleanVariable>> itemsFrequents = new ArrayList<>();// liste d'ensembles triés de variable 
    
	SortedSet<BooleanVariable> listeItems; //nous permet d'avoir une liste triée (grace au comparateur) pour l'ajouter à etapeTmp et à itemsFrequents

    Set<BooleanVariable> unique= new HashSet<>(); //liste des variables singletons 
    
    
    Set<Itemset> singletons= frequentSingletons(frequenceMinimale); // on récupère l'ensemble des singletons fréquents
    
    Set<Itemset> resultat=new HashSet<>(singletons); // Set<Itemset> que l'on va renvoyer, à qui on ajoute les singletons
    

    for(Itemset singleton : singletons){ //pour chaque singleton dans la liste des singletons
    	
      unique.add((BooleanVariable) singleton.getItems().toArray()[0]); //on conserve le singleton dans unique
    	
      listeItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR); //on (re)définit listeItems comme une liste triée vide
      
      listeItems.add((BooleanVariable) singleton.getItems().toArray()[0]); // on ajoute a listeItems la variable du singleton (le premier (et seul) élément de sa liste d'item)
      
      itemsFrequents.add(listeItems); //on ajoute dans itemsFréquents l'ensemble trié contenant seulement le singleton
    }
    

    for(int i=2; i<this.base.getItems().size()+1;i++) { 
    	
      dejaVu= new ArrayList<>(); //on réinitialise la liste des ensembles triés déja vus
      
      for(Set<BooleanVariable> variables : itemsFrequents) { //pour chaque ensemble de variable dans la liste des items fréquents
    	  
        for(BooleanVariable singleton : unique) { // et pour chaque singleton dans unique
        	
          if(!variables.contains(singleton)){ //si la liste de variable ne contient pas le singleton
        	  
            listeItems = new TreeSet<>(AbstractItemsetMiner.COMPARATOR); //on redéfinit listeItems comme une liste triée vide
            
            listeItems.addAll(variables); //on ajoute toutes les variables dans listeItems
            
            listeItems.add(singleton); //on ajoute le singleton dans listeItems
            
            if(!dejaVu.contains(listeItems) && frequency(listeItems)>= frequenceMinimale){ //et si la liste des ensembles triés déja vus ne contient pas déja listeItems et si la fréquence de listeItems est supérieure a la fréquence minimale
            	
              dejaVu.add(listeItems); // on ajoute la liste des variables dans liste temporaire
              
              resultat.add(new Itemset(listeItems,frequency(listeItems))); //on ajoute au resultat un nouvel itemset ayant comme ensemble d'items la liste items
            }
          }
        }
        itemsFrequents = new ArrayList<>(dejaVu); // après avoir regardé tous les singletons on ajoute les nouvelles listes de variables a la liste d'items fréquents
      }
      //itemsFrequents = new ArrayList<>(etapeTmp);
    }
    return resultat;
  }







}
