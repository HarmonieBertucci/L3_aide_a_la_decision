package examples;
import representation.*;
import solvers.*;
import planning.*;

import java.util.*;

/**
 * implémentation du fil rouge
 * @author 21900563 && 21715195
 *
 */
public class HouseExample{
  protected int largeur; //largeur du terrain
  protected int longueur; //longueur du terrain
  protected Set<String> piecesdEau; //ensemble des pièces d'eau
  protected Set<String> autresPieces; //ensemble des autres pièces
  protected Map<Integer,Variable> emplacementPiece; //dictionnaire des emplacements des pièces
  protected Map<String,BooleanVariable> variablesConstruction; //dictionnaire des variables de construction
  protected Set<Constraint> contraintes; //ensemble des contraintes
 
  public HouseExample(int largeur,int longueur,Set<String> piecesdEau, Set<String> autresPieces){
    this.emplacementPiece= new HashMap<>(); 
    this.variablesConstruction= new HashMap<>();
    this.contraintes=new HashSet<>();
    this.piecesdEau= new HashSet<>();
    this.autresPieces= new HashSet<>();
	this.largeur=largeur;
    this.longueur=longueur;

    for(String nomPiecedEau : piecesdEau ) { //on ajoute les pièces d'eau dans piecesdEau
    	this.piecesdEau.add(nomPiecedEau);
    }
    
    for(String nomPiece : autresPieces ) { //on ajoute les autres pièces dans autresPieces
    	this.autresPieces.add(nomPiece);
    }
    
    Set<Object> toutesLesPieces = new HashSet<>(this.piecesdEau);
    toutesLesPieces.addAll(this.autresPieces);
    
    for(int i=0 ; i<largeur * longueur;i++ ) { //pour chaque emplacement
    	this.emplacementPiece.put(i,new Variable(""+i,toutesLesPieces)); //on ajoute au dictionnaire emplacementPiece[i]="variable i ayant un domaine toutesLesPieces"
    }
 
    this.createBooleanVariables(); //on appelle la méthode createBooleanVariables pour ajouter les variables de construction dans le dictionnaire correspondant
    this.createContraint(); //on appelle la méthode createContraint pour créer les contraintes

    
    
  }

  public Set<Variable>  getVariable(){
    Set<Variable> var = new HashSet<Variable>();
    return var;
  }

  public Set<Constraint>  getConstraints(){
    Set<Constraint> var = new HashSet<Constraint>();
    return var;
  }
  
  /**
   * création des variables de construction
   */
  public void createBooleanVariables() {
	  
	  this.variablesConstruction.put("dalle_coulee",new BooleanVariable("dalle_coulee"));
	  this.variablesConstruction.put("sol_humide",new BooleanVariable("sol_humide"));
	  this.variablesConstruction.put("mur_eleve",new BooleanVariable("mur_eleve"));
	  this.variablesConstruction.put("toiture_termine",new BooleanVariable("toiture_termine"));
	  
  }
  
  /**
   * créer contraintes de construction
   */
  public void createContraint() {
	  this.contraintes.add(new Implication(this.variablesConstruction.get("sol_humide"),false, this.variablesConstruction.get("dalle_coulee"),true));
	  this.contraintes.add(new Implication(this.variablesConstruction.get("mur_eleve"),true, this.variablesConstruction.get("sol_humide"),false));
	  this.contraintes.add(new Implication(this.variablesConstruction.get("toiture_termine"),true, this.variablesConstruction.get("mur_eleve"),true));
	  
	  for(int i=0;i<this.longueur*this.largeur;i++) { //pour chaque emplacement de pièce sur le terrain
		  for(int j=0;j<this.longueur*this.largeur;j++) { //pour chaque emplacement de pièce sur le terrain
			 
			  if(i!=j) { //si les emplacements ne sont pas les mêmes
				  this.contraintes.add(new DifferenceConstraint(this.emplacementPiece.get(i),this.emplacementPiece.get(j))); //on ajoute une contrainte comme quoi les emplacements doivent être différents
			  }
		  }  
	  }
	  
	 BinaryExtensionConstraint construction = new BinaryExtensionConstraint(this.variablesConstruction.get("dalle_coulee"),this.variablesConstruction.get("toiture_termine"));
	 construction.addTuple(true,true);
	 this.contraintes.add(construction);
	 this.createContrainteEau(); //on appelle createContrainteEau pour créer les contraintes pour les pièces d'eau
	 
  }
  
  /**
   * créer les contraintes pour les pièces d'eau
   */
  public void createContrainteEau() {
	  Set <BinaryExtensionConstraint> tmpContrainte= new HashSet<>();
	  for(Variable v1 : this.emplacementPiece.values()) {
		  for(Variable v2 : this.emplacementPiece.values()) {
			  if(v1!=v2 && this.pasAdjacent(v1,v2)) {
				  tmpContrainte.add(new BinaryExtensionConstraint(v1,v2));
			  }
		  }
	  }
	 for(BinaryExtensionConstraint contrainteBinaire : tmpContrainte) {
		 for(String piecedEau : this.piecesdEau) {
			 for(String piece : this.autresPieces) {
				 contrainteBinaire.addTuple(piece, piecedEau);
			 }
		 }
		 for(String piece1 : this.autresPieces) {
			 for( String piece2 : this.autresPieces) {
				 contrainteBinaire.addTuple(piece1, piece2);
			 } 
		 }
		 
	 }
	 this.contraintes.addAll(tmpContrainte);
  } 
  
  /**
   * 
   * @param v1
   * @param v2
   * @return si les pièces ne sont pas adjacentes
   */
  public boolean pasAdjacent(Variable v1, Variable v2) {
	  int numeroPiece1 = Integer.parseInt(v1.getName());
	  int numeroPiece2 = Integer.parseInt(v1.getName());
	  if(numeroPiece1==numeroPiece2) {
		  return false; //si les deux pièces sont les mêmes on renvoie false
	  }
	  else if (Math.abs( numeroPiece1/this.longueur - numeroPiece2/this.longueur) <2 && Math.abs( numeroPiece1%this.largeur - numeroPiece2%this.largeur) <2) { //si les deux pièces sont a coté
        	  return false;
      } 
	  return true ; 
  }
  
  /**
   * partie solveur
   * @param choix
   * @return la solution
   */
  public Map<Variable,Object> solve(String choix){
	  Set<Variable> tmpVariable = new HashSet<>();
	  
	  for(Variable variable : this.emplacementPiece.values()) { 
		  tmpVariable.add(variable); //on ajoute les variable d'emplacement de Piece dans tmpVariable
	  }
	  
	  for(Variable variable : this.variablesConstruction.values()) {
		  tmpVariable.add(variable); //on ajoute les variable de construction dans tmpVariable
	  }	  
	  
	  Set<Constraint> tmpContrainte = new HashSet<>(this.contraintes); //on ajoute les contraintes dans tmpContrainte
	
	  Solver solveur = new BacktrackSolver(tmpVariable, tmpContrainte); //on utilise de base Backtrack
	  
	  if(choix =="mac") { //mais si on met mac en argument de la méthode
		  solveur= new MACSolver(tmpVariable,tmpContrainte); //on utilise MacSolver (qui utilise ac1)
	  }
	  
	  return solveur.solve(); //on renvoie la solution trouvée par le solver
  }
  
  
  /**
   * @return l'état initial
   */
  public Map<Variable,Object> createEtatInitial(){
	  Map<Variable, Object> etatInitial = new HashMap<>(); //on créé l'état initial
	  
	  for(int i=0; i<this.longueur * this.largeur;i++) {
		  etatInitial.put(this.emplacementPiece.get(i), ""); //on met un nom de pièce vide pour chaque emplacement
	  }
	  
	  // on initialise les variables d'emplacement
	  etatInitial.put(this.variablesConstruction.get("dalle_coulee"),false);
	  etatInitial.put(this.variablesConstruction.get("sol_humide"),true);
	  etatInitial.put(this.variablesConstruction.get("mur_eleve"),false);
	  etatInitial.put(this.variablesConstruction.get("toiture_termine"),false);
	  
	  return etatInitial;  
  }
  
  /**
   * 
   * @return le goal = la solution trouvée par le solver
   */
  public Goal createGoal(){
	 return new BasicGoal(this.solve("mac"));
  }
  
  /**
   * on crée les actions possibles
   * @return l'ensemble des actions possibles
   */
  public Set<Action> createAction(){
	  Set<Action> actions = new HashSet<>();
	  Map<Variable,Object> precondition;
	  Map<Variable,Object> consequence;
	 
		  precondition = new HashMap<>();
		  consequence  = new HashMap<>();
		  consequence.put(this.variablesConstruction.get("dalle_coulee"),true);
		  actions.add(new BasicAction(precondition,consequence,1));
		  
		  precondition = new HashMap<>();
		  consequence  = new HashMap<>();
		  precondition.put(this.variablesConstruction.get("dalle_coulee"),true);
		  precondition.put(this.variablesConstruction.get("sol_humide"),true);
		  consequence.put(this.variablesConstruction.get("sol_humide"), false);
		  actions.add(new BasicAction(precondition,consequence,1));
		  
		  precondition = new HashMap<>();
		  consequence  = new HashMap<>();
		  precondition.put(this.variablesConstruction.get("sol_humide"),false);
		  precondition.put(this.variablesConstruction.get("mur_eleve"),false);
		  consequence.put(this.variablesConstruction.get("mur_eleve"),true);
		  actions.add(new BasicAction(precondition,consequence,1));
		  
		  precondition = new HashMap<>();
		  consequence  = new HashMap<>();
		  precondition.put(this.variablesConstruction.get("mur_eleve"),true);
		  precondition.put(this.variablesConstruction.get("toiture_termine"),false);
		  consequence.put(this.variablesConstruction.get("toiture_termine"),true);
		  actions.add(new BasicAction(precondition,consequence,1));
		  
		  

		  
			for(Variable variable : this.emplacementPiece.values()) {
			  for(String piece : this.piecesdEau) {
				  precondition = new HashMap<>();
				  precondition.put(this.variablesConstruction.get("toiture_termine"), true);
				  precondition.put(variable, "");
				  consequence  = new HashMap<>();
				  consequence.put(variable, piece);
				  actions.add(new BasicAction(precondition,consequence,1));
			  }
		  }
		  
		  
			for(Variable variable : this.emplacementPiece.values()) {
			  for(String piece : this.autresPieces) {
				  precondition = new HashMap<>();
				  precondition.put(this.variablesConstruction.get("toiture_termine"), true);
				  precondition.put(variable, "");
				  consequence  = new HashMap<>();
				  consequence.put(variable, piece);
				  actions.add(new BasicAction(precondition,consequence,1));
			  }
		  }
		  
	  return actions;
  }
  
  /**
   * 
   * @return le plan pour atteindre le goal avec la liste d'action
   * 
   * on utilise DFSPlanner
   */
  public List<Action> planification(){
	  System.out.println(this.createEtatInitial() + "\n " + this.createAction() + "\n "+ this.createGoal().getGoal());
	  Planner planing = new DFSPlanner(this.createEtatInitial(),this.createAction(),this.createGoal());
	  return planing.plan();
  }
 
}
