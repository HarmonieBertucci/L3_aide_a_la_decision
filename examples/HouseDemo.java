package examples;
import java.util.Set;
import java.util.HashSet;

public class HouseDemo{
  public static void main(String[]  args){
    Set<String> s1 = new HashSet<String>();
    s1.add("sdb");
    s1.add("cuisine"); 
    Set<String> s2 = new HashSet<String>();
    s2.add("chambre1");
    s2.add("chambre2");

    HouseExample maison = new HouseExample(2,2,s1,s2);
    
    System.out.println(maison.solve("mac"));
    System.out.println(maison.planification());
    System.out.println("ok");
    
    
  }
}
