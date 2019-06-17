package com.chiruhas.android.zerodha.HelperClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles state wise stamp duty costs
 *
 * it contains methods which take in the the type of order as parameter and returns the value
 */
public class StateTaxHelper {
   static Map<String,Model> d = new HashMap<>();
    public static void initData() {
        d.put("Andhra Pradesh", new Model(0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50));
        d.put("Arunachal Pradesh", new Model(0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0));
        d.put("Assam", new Model(0.018, 49.5, 0.018, 49.5, 0.018, 49.5, 0.018, 49.5, 0.018, 49.5, 0.018, 49.5));
        d.put("Delhi", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.002, 0, 0.001, 0));
        d.put("Goa", new Model(0.005, 0, 0.005, 0, 0.005, 0, 0.005, 0, 0.005, 0, 0.005, 0));
        d.put("Gujarat", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.002, 0, 0.001, 0));
        d.put("Haryana", new Model(0.002, 20, 0.01, 500, 0.002, 200, 0.002, 200, 0.002, 200, 0.001, 500));
        d.put("Himachal Pradesh", new Model(0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50));
        d.put("Jammu & Kashmir", new Model(0.01, 0, 0.01, 0, 0.01, 0, 0.01, 0, 0.01, 0, 0.01, 0));
        d.put("Karnataka", new Model(0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0));
        d.put("Kerala", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.002, 0, 0.001, 0));
        d.put("Madhya Pradesh", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.002, 0, 0.002, 50));
        d.put("Maharashtra", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.002, 0, 0.001, 0));
        d.put("Meghalaya", new Model(0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0));
        d.put("Odisha", new Model(0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50, 0.005, 50));
        d.put("Rajasthan", new Model(0.003, 0, 0.012, 0, 0.0012, 0, 0.0024, 0, 0.0012, 0, 0, 0));
        d.put("Tamil Nadu", new Model(0.006, 0, 0.006, 0, 0.006, 0, 0.006, 0, 0.006, 0, 0, 0));
        d.put("Telangana", new Model(0.01, 100, 0.01, 100, 0.01, 100, 0.01, 100, 0.01, 100, 0.01, 100));
        d.put("Uttar Pradesh", new Model(0.002, 1000, 0.002, 1000, 0.002, 1000, 0.002, 1000, 0.002, 1000, 0.002, 1000));
        d.put("West Bengal", new Model(0.002, 0, 0.01, 0, 0.002, 0, 0.002, 0, 0.0002, 0, 0.002, 0));
        d.put("Others", new Model(0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0, 0.003, 0));
    }

    /**
     * @param type to specify the type for which brokerage is need to be calculated
     *      *             E0 indicates Equity intraday
     *      *             E1 indicates Equity Delivery
     *      *             E2 indicates Equity Futures
     *      *             E3 indicate Equity Options
     *      *             CU0 indicates Currency Futures
     *      *             CU1 indicates Currency Options
     *      *             C0 indicates Commodity Futures
     *      *             C1 indicates Commodity Options
     * @return
     */
    public static double stampDuty(String type,double turnover,String state){
        //initialising data
       initData();
       Model m = d.get(state);
        double per=0;
        double tax=0;

        if (type.startsWith("E")) {
            if (type.equals("E0")) {

                per = (turnover*m.getIntra1())/100;
                if(per>m.getIntra2()){
                    if(m.getIntra2()==0)
                        tax=per;
                    else tax=m.getIntra2();

                    per=tax;
                }

            } else if (type.equals("E1")) {
                per = (turnover*m.getDel1())/100;
                if(per>m.getDel2()){
                    if(m.getDel2()==0)
                        tax=per;
                    else tax=m.getDel2();

                    per=tax;
                }

            } else if (type.equals("E2")) {
                per = (turnover*m.getFut1())/100;
                if(per>m.getFut2()){
                    if(m.getFut2()==0)
                        tax=per;
                    else tax=m.getFut2();

                    per=tax;
                }
            } else if (type.equals("E3")) {
                per = (turnover*m.getOpt1())/100;
                if(per>m.getOpt2()){
                    if(m.getDel2()==0)
                        tax=per;
                    else tax=m.getOpt2();

                    per=tax;
                }
            }
        } else if (type.startsWith("C")) {
            per = (turnover*m.getCur1())/100;
            if(per>m.getCur2()){
                if(m.getCur2()==0)
                    tax=per;
                else tax=m.getCur2();

                per=tax;
            }
        }


        else if (type.startsWith("c")) {
            per = (turnover*m.getCom1())/100;
            if(per>m.getCom2()){
                if(m.getCom2()==0)
                    tax=per;
                else tax=m.getCom2();

                per=tax;
            }
        }

        return  per;
    }

}

 class Model{
    double intra1;
    double intra2;
    double del1;
    double del2;
    double fut1;
    double fut2;
    double opt1;
    double opt2;
    double cur1;
    double cur2;
    double com1;
    double com2;

     public Model(double intra1, double intra2, double del1, double del2, double fut1, double fut2, double opt1, double opt2, double cur1, double cur2, double com1, double com2) {
         this.intra1 = intra1;
         this.intra2 = intra2;
         this.del1 = del1;
         this.del2 = del2;
         this.fut1 = fut1;
         this.fut2 = fut2;
         this.opt1 = opt1;
         this.opt2 = opt2;
         this.cur1 = cur1;
         this.cur2 = cur2;
         this.com1 = com1;
         this.com2 = com2;
     }

     public double getCur1() {
         return cur1;
     }

     public double getCur2() {
         return cur2;
     }

     public double getCom1() {
         return com1;
     }

     public double getCom2() {
         return com2;
     }

     public double getIntra1() {
         return intra1;
     }

     public double getIntra2() {
         return intra2;
     }

     public double getDel1() {
         return del1;
     }

     public double getDel2() {
         return del2;
     }

     public double getFut1() {
         return fut1;
     }

     public double getFut2() {
         return fut2;
     }

     public double getOpt1() {
         return opt1;
     }

     public double getOpt2() {
         return opt2;
     }
 }
