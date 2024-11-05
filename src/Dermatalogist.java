import java.util.*;

public class Dermatalogist {
    String Name;
    private final Map<String, List<String>> schedule;


    public Dermatalogist(String Name, Map<String, List<String>> schedule){
        this.Name = Name;
        this.schedule = schedule;
    }



    public String getName(){
        return Name;
    }
    public Map<String, List<String>> getSchedule(){
        return schedule;
    }




}
