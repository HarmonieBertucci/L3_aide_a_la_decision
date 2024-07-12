package datamining;

import java.util.*;

import representation.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner  {
	protected Apriori apriori ;
	
	public BruteForceAssociationRuleMiner(BooleanDatabase db) {
		super(db);
		this.apriori= new Apriori(db);
	}

	/**
	 * 
	 * @param ensembleItems un ensemble d'items
	 * @return l'ensemble de tous ses sous-ensembles, à l'exception de l'ensemble vide et de l'ensemble lui-même
	 */
	public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> ensembleItems){
		Set<Set<BooleanVariable>> resultat = new HashSet<>(); // tous les sous ensembles de ensembleItems
		
		Set<Set<BooleanVariable>> etape= new HashSet<>(); //
		
		Set<Set<BooleanVariable>> ensembleTmp= new HashSet<>(); //ensemble que l'on va rajouter a etape
		
		Set<BooleanVariable> ensembleDeVariables  = new HashSet<>(); //ensemble temporaire
		
		Set<BooleanVariable> singletons= new HashSet<>(); //ensemble des singletons
		
		for(BooleanVariable item : ensembleItems) { //pour tous les items de l'ensemble
			
			ensembleDeVariables = new HashSet<>(); //on (ré)initialise l'ensemble de variables a un ensemble vide
			
			ensembleDeVariables.add(item); // on y ajoute l'item
			
			if(!ensembleDeVariables.equals(ensembleItems) && ensembleDeVariables.size()>0){ //si l'ensemble n'est pas égal a ensembleItem et n'est pas vide
				resultat.add(ensembleDeVariables); //on ajoute l'ensemble de variables au résultat
				etape.add(ensembleDeVariables); //et a etape
				singletons.add(item); //et a singletons
			}
		}
		
		for(int i=2; i<ensembleItems.size();i++) {
			
			ensembleTmp= new HashSet<>(); // on (ré)initialise etapeTmp a une liste vide
			
			for(Set<BooleanVariable> variables : etape) { //pour tous les éléments dans etape
				
				for(BooleanVariable singleton : singletons) { //puis pour tous les singletons
					
					ensembleDeVariables = new HashSet<>(variables); //on redéfinit l'ensemble de variables a un ensemble vide a qui on ajoute variables
					
					ensembleDeVariables.add(singleton); //on y ajoute le singleton
					
					ensembleTmp.add(ensembleDeVariables); //on ajoute l'ensemble de variables a l'ensemble temporaire
					
					resultat.add(ensembleDeVariables); // et au résultat
				}
			}
			etape = new HashSet<>(ensembleTmp); //après avoir visité tous les éléments dans etape on le redéfinit en ensembleTmp
		}
		return resultat;
	}

	@Override
	public Set<AssociationRule> extract(float frequenceMinimale,float confianceMinimale){
		Set<AssociationRule> listeAssociation=  new HashSet<>(); //liste des règles d'asssociation extraites
		
		Set<Itemset> itemsets = this.apriori.extract(frequenceMinimale); //itemsets (non vides) dans la base ayant une fréquence plus grande ou égale a frequenceMinimale (extraits par apriori)
		
		Set<BooleanVariable> listeVariable=new HashSet<>(); // nous permet de conserver la liste des variables d'un itemset
		
		float frequence; //nous permet de conserver la fréquence d'un item
		
		float confiance; //nous permet de conserver la fréquence d'un item
		
		Set<BooleanVariable> conclusion; 
		
		AssociationRule regleAssociation;
		
		for(Itemset itemSet : itemsets){ //on parcours l'ensemble des itemsets extraits par apriori
			
			listeVariable = itemSet.getItems(); //on met ses items dans listeVariable
			
			frequence = itemSet.getFrequency(); //on définit sa fréquence
			
			if(frequence>=frequenceMinimale){ //si sa fréquence est supérieure a la fréquence minimale
				
				for(Set<BooleanVariable> premise : BruteForceAssociationRuleMiner.allCandidatePremises(listeVariable)){ //on parcours la liste des premisses candidates
					
					conclusion= new HashSet<>(listeVariable); //on met dans conclusion l'ensemble des items de l'itemset
					
					conclusion.removeAll(premise); //et on y enlève la premisse
					
					confiance = confidence(premise,conclusion,itemsets); //on définit la confiance
					
					if( confiance >= confianceMinimale){ //si sa confiance est plus grande que la confiance minimale
						
						listeAssociation.add(new AssociationRule(premise,conclusion,frequence,confiance)); //on ajoute la règle d'association a la liste a renvoyer
					}
				}
			}
		}
		return listeAssociation;
	}
}
