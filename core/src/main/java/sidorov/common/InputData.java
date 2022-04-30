package sidorov.common;

public class InputData {

    private int l = 0;
    private int s = 0;

    private double alpha = 0;
    private double beta = 0;
    private double[] pValues = new double[]{0};
    private int[] ranks = new int[]{1};

    public InputData(int l, int s) {
        this.l = l;
        this.s = s;
    }

    public InputData(double alpha, double beta, double[] pValues, int[] ranks) {
        this.alpha = alpha;
        this.beta = beta;
        this.pValues = pValues;
        this.ranks = ranks;
    }

    public int l() {
        return l;
    }

    public int s() {
        return s;
    }

    public double alpha() {
        return alpha;
    }

    public double beta() {
        return beta;
    }

    public double[] pValues() {
        return pValues;
    }

    public int[] ranks() {
        return ranks;
    }
}
