package Statistics;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
 
import org.jfree.data.general.DefaultPieDataset;
 
import Data.CustomIncident;
import Data.Process;
 
public class JamanStat {
              private DefaultPieDataset pieDataset;
              private HashMap<String, Integer> finalShapes;
 
              public JamanStat() {
 
                           pieDataset = new DefaultPieDataset();
 
                           ArrayList<CustomIncident> allIncids = Process.getAllIncidents();
                           HashSet<String> shapes = new HashSet<String>();
                           finalShapes = new HashMap<String, Integer>();
                           ArrayList<String> allShapes = new ArrayList<String>();
 
                           for (int i = 0; i < allIncids.size(); i++) {
                                         shapes.add(allIncids.get(i).getShape());
                                         allShapes.add(allIncids.get(i).getShape());
                           }
 
                           for (String shape : shapes) {
                                         finalShapes.put(shape, Collections.frequency(allShapes, shape));
                           }
 
                           for (String shape : finalShapes.keySet()) {
                                         pieDataset.setValue(shape, finalShapes.get(shape));
                           }
              }
}