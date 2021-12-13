import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import modals.DirectedWeightedGraphAlgorithms_Class;
import modals.DirectedWeightedGraph_Class;
import modals.MyFrame;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {

    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {

        DirectedWeightedGraph ans = new DirectedWeightedGraph_Class();
        DirectedWeightedGraphAlgorithms_Class algo = new DirectedWeightedGraphAlgorithms_Class();
        algo.init(ans);
        algo.load(json_file);
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph ans = new DirectedWeightedGraph_Class();
        DirectedWeightedGraphAlgorithms_Class algo = new DirectedWeightedGraphAlgorithms_Class();
        algo.init(ans);
        algo.load(json_file);
        return algo;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        MyFrame frame2 = new MyFrame(json_file);
    }
    public static void main(String[] args) {
        runGUI(args[0]);
    }
}