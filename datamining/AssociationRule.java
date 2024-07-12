package datamining;
import representation.*;
import java.util.*;

/**
 * permet de representer les règles d'association (premise implique conclusion
 * @author 21900563
 *
 */
public class AssociationRule{
  protected Set<BooleanVariable> premise;
  protected Set<BooleanVariable> conclusion;
  protected float frequence;
  protected float confiance;

  public AssociationRule(Set<BooleanVariable> premise,Set<BooleanVariable> conclusion,float frequence,float confiance){
    this.premise=premise;
    this.conclusion=conclusion;
    this.frequence=frequence;
    this.confiance=confiance;
  }

  public Set<BooleanVariable> getPremise(){
    return this.premise;
  }

  public Set<BooleanVariable> getConclusion(){
    return this.conclusion;
  }

  public float getFrequency(){
    return this.frequence;
  }

  public float getConfidence(){
    return this.confiance;
  }

}
