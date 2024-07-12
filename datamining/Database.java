package datamining;
import java.util.*;
import representation.*;

public class Database {
	protected Set<Variable> variables;
	protected List<Map<Variable, Object>> instances;

	public Database(Set<Variable> liste) {
		this.variables= liste;	//liste des variables concerné par la database
		this.instances = new ArrayList<>(); //construction de la base vide
	}

	public List<Map<Variable , Object>> getInstances(){ //retourne la liste des instances rentré dans la database
		return this.instances;
	}

	public Set<Variable> getVariables(){//retourne la liste des variables
		return this.variables;
	}

	/**
	 * ajoute une instance à la base
	 * @param instance instance a ajouter a la base
	 */
	public void add(Map<Variable, Object> instance) {
		this.instances.add(instance);
	}

	/**
	 *crée une table d'item qui associe une booleanVariable a chaque couple variable valeur (avec les valeurs dans le domaines de la variable)
	 * @return
	 */
	public Map<Variable, Map<Object, BooleanVariable>> itemTable(){
		Map<Variable, Map<Object, BooleanVariable>> tableItem= new HashMap<>(); //la table a renvoyer
		Map<Object, BooleanVariable> item;

		for(Variable variable : this.variables){ //on parcours l'ensemble des variables

			item = new HashMap<>(); //on (ré)initialise item

			if(variable.getClass().getSimpleName().equals("BooleanVariable") ){ //si la variable est une BooleanVariable

				for(Object bool : variable.getDomain() ){ //on parcours son domaine

					if((boolean) bool == true){ //si l'objet du domaine est le booleen true
						item.put(true,new BooleanVariable(variable.getName())); //on ajoute au dictionnaire une booleanVariable  true => une nouvelle variable ayant le même nom
					}
				}
			}
			else{ //si la variable n'est pas une BooleanVariable

				for(Object valeur : variable.getDomain()){//on parcours son domaine

					item.put(valeur, new BooleanVariable(variable.getName() + valeur)); //on ajoute a item une entré ayant comme clé la valeur en cours de la variable regardé et en valeur une  booleanVariable qui sera associé au couple {variable,valeur};
				}
			}
			tableItem.put(variable,item); // on ajoute a la table a renvoyer variable => item
		}
		return tableItem;
	}

	/**
	 *
	 * @return une instance de BooleanDatabase codant la base sur l'ensemble des items créés par la méthode itemTable en se servant des instances
	 */
	public BooleanDatabase propositionalize(){
		Map<Variable, Map<Object, BooleanVariable>> tableItem = itemTable(); //resultat de la méthode itemTable

		Set<BooleanVariable> items= new HashSet<>(); //ensemble de variables

		for(Variable variable : this.variables){ // on parcours l'ensemble des variables

			for(Object valeur : variable.getDomain() ){ //on parcours leur domaine

				if(tableItem.keySet().contains(variable)){ //si la variable est dans tableItem

					if(tableItem.get(variable).keySet().contains(valeur)){ //si la valeur est dans la table

						items.add(tableItem.get(variable).get(valeur)); //on ajoute la booleanVariable associé au couple {variable,valeur} a items
					}
				}
			}
		}
		BooleanDatabase db = new BooleanDatabase(items); // creation de la booleanDatabase a renvoyer
		for(Map<Variable, Object> instance : this.instances){ // on parcourt les instances de la database
			Set<BooleanVariable> transaction = new HashSet<>(); // on initialise une transaction
			for(Variable variable : instance.keySet()){ // on parcourt la liste des variables concerné par l'instance
				transaction.add(tableItem.get(variable).get(instance.get(variable))); // et on ajoute a la transaction les booleanVariables qui sont associées au couple {variable de l'instance, valeur dans l'instance}
			}
			db.add(transaction); // on ajoute la transaction a la booleanDatabase
		}
		return db;
	}
}
