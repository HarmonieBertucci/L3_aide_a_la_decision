package representation;


import java.util.*;

/**
 * represente un couple de valeurs (de type Object)
 * on s'en sert dans BinaryExtensionConstraint pour avoir un couple de valeur autorisees
 */
public class BinaryTuple{
  protected Object o1;
  protected Object o2;

  public BinaryTuple(Object o1, Object o2){
    this.o1=o1;
    this.o2=o2;
  }

  /**
   * redéfinition d'equals() pour qu'il ne verifie si les deux objets des couples sont égaux
   * @param v est l'object que l'on veut comparer a la variable
   * @return si this contient les memes objets que v
   */
  @Override
  public boolean equals(Object b2){
    BinaryTuple b = (BinaryTuple) b2;
    return ((b.getO1() == this.o1)&&(b.getO2() == o2));
  }

  /**
   * redéfinition de hashCode() pour qu'il soit egal a la somme des hashCode des deux objects
   * pour que 2 BinaryTuples ayant les memes objets aient le meme hashCode
   * @return le hashCode du BinaryTuples
   */
  @Override
  public int hashCode(){
    return this.o1.hashCode()+this.o2.hashCode();
  }

  public Object getO1(){
    return this.o1;
  }

  public Object getO2(){
    return this.o2;
  }
}
