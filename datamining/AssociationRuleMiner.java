package datamining;
import java.util.*;

public interface AssociationRuleMiner{
	
  public BooleanDatabase getDatabase();
  
  /**
   * permet d'extraire les règles d'associations ayant une fréquence et une confiance respectivement supérieures a frequenceMinimale et confianceMinimale 
   * @param frequenceMinimale
   * @param confianceMinimale
   * @return les règes d'associations extraites de la database
   */
  public Set<AssociationRule> extract(float frequenceMinimale,float confianceMinimale);
}
