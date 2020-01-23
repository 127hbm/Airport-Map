//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
//
import java.util.Objects;
public class Entry {
    private String code;
    private String information;
    
    public Entry (String code, String information) {
        this.code = code;
        this.information = information;
    }
    public Entry(String code) {
        this(code, null);
    }
    public String getCode() {
        return code;
    }
    
    public String getInformation() {
        return information;
    }
    
    @Override
    public boolean equals (Object other) {
        boolean result;
        if (other == null || getClass() != other.getClass())
            result = false;
        else {
            Entry otherEntry = (Entry) other;
            result = code.equals(otherEntry.getCode());
        }
        return result;
    } // end equals
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
