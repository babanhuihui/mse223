package shipSearch;
import java.text.DecimalFormat;
class Estimator { // computes point estimates and confidence intervals
    private double k; // # of values processed so far
    private double sum; // running sum of values
    private double v; // running value of (k-1)*variance
    private double z; // quantile for normal confidence intervals
    private String confStr; // string of form "xx%" for xx% confidence interval
    private DecimalFormat df; // for printing CI
	
   public Estimator(double z, String confStr, String dfStr) {
        k = 0;
        sum = 0;
        v = 0;
        this.z = z;
        this.confStr = confStr;
        df = new DecimalFormat(dfStr);
    }
   
   public void reset() {
       k = 0;
       sum = 0;
       v = 0;
   }
	
    public void processNextValue(double value) {
        k++;
        if (k>1) {
            double diff = sum - (k-1)*value;
            v += (diff/k) * (diff/(k-1));
        }
        sum += value;
    }

    public double variance() {
        double var = 0;
        if (k>1) {
            var = v/(k-1);
        }
        return var;
    }
	
    public void printConfidenceInterval(){
        double hw = z * Math.sqrt(variance()/k);
        double pointEstimate = mean();
        double cLower = pointEstimate - hw;
        double cUpper = pointEstimate + hw;
        System.out.print(confStr+" Confidence Interval ["+df.format(cLower)+", "+df.format(cUpper)+"]");
        System.out.println("Percentage: " + cLower/pointEstimate + " to " + cUpper/pointEstimate);
    }
	
    public double mean() {
        double mu=0;
        if (k >1) {
            mu = sum/k;
        }
        return mu;
    }

    public long numberOfTrialsNeeded(double epsilon, boolean relative) {
        double var = variance();
        double width = epsilon;
        if (relative) {
            width = mean() * epsilon;
        }
        return  (long)((var * z * z)/(width * width));
    }

    public double sum() {
        return sum;
    }
}

