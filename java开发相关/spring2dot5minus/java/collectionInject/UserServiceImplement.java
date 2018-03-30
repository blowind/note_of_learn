package collectionInject;  
  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.HashSet;  
import java.util.Map;  
import java.util.Properties;  
import java.util.Set;  
import java.util.List;  
  
public class UserServiceImplement implements IUserService {  
  
    public Set<String> getS() {  
        return s;  
    }  
  
    public void setS(Set<String> s) {  
        this.s = s;  
    }  
  
    public Map<String, String> getM() {  
        return m;  
    }  
  
    public void setM(Map<String, String> m) {  
        this.m = m;  
    }  
  
    public Properties getP() {  
        return p;  
    }  
  
    public void setP(Properties p) {  
        this.p = p;  
    }  
  
    public List<String> getL() {  
        return l;  
    }  
  
    public void setL(List<String> l) {  
        this.l = l;  
    }  
  
    private Set<String> s = new HashSet<String>();  
    private Map<String, String> m = new HashMap<String, String>();  
    private Properties p = new Properties();  
    private List<String> l = new ArrayList<String>();  
  
    public void saveUser() {  
        System.out.println("Set集合注入");  
        for (String str : s) {  
            System.out.println(str);  
        }  
  
        System.out.println("------------------------------");  
        System.out.println("Map集合注入");  
        for (String str : m.values()) {  
            System.out.println(str);  
        }  
  
        System.out.println("------------------------------");  
        System.out.println("Properties属性集合注入");  
        for (Object str : p.values()) {  
            System.out.println(str);  
        }  
  
        System.out.println("------------------------------");  
        System.out.println("List集合注入");  
        for (String str : l) {  
            System.out.println(str);  
        }  
    }  
}