package com.calculator.utils;

import com.calculator.TokenType;
import static com.calculator.TokenType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MathOps {

    public static Map<TokenType, Function<Double, Double>> singleParamFunctions = new HashMap<>();
    public static Map<TokenType, Function<ArrayList<Double>, Double>> multiParamFunctions = new HashMap<>();
    public static final double PHI = 1.618033988749894;

    static {
        singleParamFunctions.put(SQRT, Math::sqrt);
        singleParamFunctions.put(LN, Math::log);
        singleParamFunctions.put(SIN, Math::sin);
        singleParamFunctions.put(COS, Math::cos);
        singleParamFunctions.put(TAN, Math::tan);
        singleParamFunctions.put(SINH, Math::sinh);
        singleParamFunctions.put(COSH, Math::cosh);
        singleParamFunctions.put(TANH, Math::tanh);
        singleParamFunctions.put(ARCSIN, Math::asin);
        singleParamFunctions.put(ARCCOS, Math::acos);
        singleParamFunctions.put(ARCTAN, Math::atan);
        singleParamFunctions.put(CSC, (arg) -> 1 / Math.sin(arg));
        singleParamFunctions.put(SEC, (arg) -> 1 / Math.cos(arg));
        singleParamFunctions.put(COT, (arg) -> 1 / Math.tan(arg));
        singleParamFunctions.put(CSCH, (arg) -> 1 / Math.sinh(arg));
        singleParamFunctions.put(SECH, (arg) -> 1 / Math.cosh(arg));
        singleParamFunctions.put(COTH, (arg) -> 1 / Math.tanh(arg));
        singleParamFunctions.put(ARCCSC, (arg) -> Math.signum(arg) * (-1.0 * ( Math.atan( Math.sqrt(Math.pow(arg, 2) - 1)) + (Math.PI / 2) )));
        singleParamFunctions.put(ARCSEC, (arg) -> Math.signum(arg) * ( Math.atan( Math.sqrt(Math.pow(arg, 2) - 1)) ));
        singleParamFunctions.put(ARCCOT, (arg) -> (-1.0 * Math.atan(arg)) + (Math.PI / 2));
        singleParamFunctions.put(VER, (arg) -> 1.0 - Math.cos(arg));
        singleParamFunctions.put(VCS, (arg) -> 1.0 + Math.cos(arg));
        singleParamFunctions.put(CVS, (arg) -> 1.0 - Math.sin(arg));
        singleParamFunctions.put(CVC, (arg) -> 1.0 + Math.sin(arg));
        singleParamFunctions.put(SEM, (arg) -> (1.0 - Math.cos(arg)) / 2.0);
        singleParamFunctions.put(HVC, (arg) -> (1.0 + Math.cos(arg)) / 2.0);
        singleParamFunctions.put(HCV, (arg) -> (1.0 - Math.sin(arg)) / 2.0);
        singleParamFunctions.put(HCC, (arg) -> (1.0 + Math.sin(arg)) / 2.0);
        singleParamFunctions.put(EXS, (arg) -> (1.0 / Math.cos(arg)) - 1.0);
        singleParamFunctions.put(EXC, (arg) -> (1.0 / Math.sin(arg)) - 1.0);
        singleParamFunctions.put(CRD, (arg) -> 2.0 * Math.sin(arg / 2.0));

        //These functions only take in the parameters it wants and if you give it more than it takes in it will ignore the extras
        multiParamFunctions.put(ROOT, (args) -> Math.pow(args.get(1), 1 / args.get(0)));
        multiParamFunctions.put(LOG, (args) -> Math.log10(args.get(1)) / Math.log10(args.get(0)));
        multiParamFunctions.put(BINOMIALPDF, (args) -> binomialpdf(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(BINOMIALCDF, (args) -> binomialcdf(args.get(0), args.get(1), args.get(2)));

//        multiParamFunctions.put(NORMALPDF, (args) -> normalpdf(args.get(0), args.get(1), args.get(2)));
    }

    /*
    If it gets a whole number then it will evaluate it normally and return the value, but if it gets a decimal number
    then it will evaluate the gamma function(sterling's approximation) and return the value
     */
    public static double factorial(String n) {
        if (n.endsWith(".0")) {
            int num = Integer.parseInt(n.substring(0, n.length() - 2));
            double sum = 1;
            for (int i = num; i > 0; i--) {
                sum *= i;
            }
            return sum;
        } else {
            double num = Double.parseDouble(n);
            return Math.sqrt(2 * Math.PI * num) * Math.pow(num / Math.E, num);
        }
    }

    private static double binomialpdf(double trials, double x_value, double prob_of_success) {
        double bin_coeff = factorial(String.valueOf(trials)) / (factorial(String.valueOf(x_value)) * factorial(String.valueOf(trials - x_value)));
        return bin_coeff * Math.pow(prob_of_success, x_value) * Math.pow((1 - prob_of_success), (trials - x_value));
    }

    private static double binomialcdf(double trials, double x_value, double prob_of_success) {
        double prob = 0;
        for(int i = (int) x_value; i >= 0; i--) {
            prob += binomialpdf(trials, i, prob_of_success);
        }
        return prob;
    }

//    private static double normalpdf(double value, double mean, double std) {
//        double z_score =  (value - mean) / std;
//        return
//    }

//    public static double root(double root, double value) {
//        return Math.pow(value, 1 / root);
//    }
//    public static double log(double base, double value) {
//
//    }
}
