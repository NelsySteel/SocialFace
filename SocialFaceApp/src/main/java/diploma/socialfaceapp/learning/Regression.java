/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diploma.socialfaceapp.learning;

import diploma.socialfaceapp.Utils.dbUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.regression.MultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;
/**
 *
 * @author krist
 */
public class Regression {
    private final OLSMultipleLinearRegression regression;
        private RegressionResults results;
        public Regression(){
            this.regression = new OLSMultipleLinearRegression();
        }
        
        public List<Double> RegressionHandler(String trait){
            double[] trait_vals = null;
            double[][] likes = null;
                trait_vals = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
                likes = new double[6][];
                likes[0] = new double[]{0, 0, 0, 0, 0};
                likes[1] = new double[]{2.0, 0, 0, 0, 0};
                likes[2] = new double[]{0, 3.0, 0, 0, 0};
                likes[3] = new double[]{0, 0, 4.0, 0, 0};
                likes[4] = new double[]{0, 0, 0, 5.0, 0};
                likes[5] = new double[]{0, 0, 0, 0, 6.0};          
            
            regression.newSampleData(trait_vals, likes);
            double[] results = regression.estimateRegressionParameters();
            Double[] doubleArray = ArrayUtils.toObject(results);
            return new ArrayList(Arrays.asList(doubleArray));
        }
}
