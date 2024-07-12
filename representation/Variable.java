package representation;

import java.util.Set;

/**
 * classe permettant de representer une variable
 */
public class Variable {

	protected String nom;
	protected Set<Object> domaine;

	/**
	 * constructeur
	 * @param nom est le nom de la variable
	 * @param domaine est le domaine (l'ensemble des valeurs que peut prendrre la variable) de la variable
	 */
	public Variable(String nom,Set<Object> domaine) {
		this.nom=nom;
		this.domaine=domaine;
	}

	/**
	 * redéfinition d'equals() pour qu'il ne dépende que du nom de la variable
	 * @param v est l'object que l'on veut comparera la variable
	 * @return si le nom de v est egal a celui de la variable
	 */
	@Override
	public boolean equals(Object v) {
			Variable w = (Variable) v;
			return w.getName()==this.nom || v==this;

	}

	/**
	 * redéfinition de hashCode() pour qu'il ne depende soit egal au hashCode du nom de la variable
	 * pour que 2 variables ayant le meme nom aient le meme hashCode
	 * @return le hashCode de la variable
	 */
	@Override
	public int hashCode() {
		return ((this.nom == null) ? 0 : this.nom.hashCode());
	}

	/**
	 * permet de récupérer le nom de la variable (getter)
	 * @return l'attribut nom de la variable
	 */
	public String getName() {
		return this.nom;
	}

	/**
	 * permet de récupérer le domaine de la variable (getter)
	 * @return l'attribut domaine de la variable
	 */
	public Set<Object> getDomain(){
		return this.domaine;
	}
}
