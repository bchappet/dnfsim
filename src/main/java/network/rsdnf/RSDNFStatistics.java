/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.network.rsdnf;

import java.math.BigDecimal;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.network.generic.NetworkStatistics;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFStatistics extends NetworkStatistics{

    public RSDNFStatistics(String name, Var<BigDecimal> dt, Parameter... parameter) {
        super(name, dt, parameter);
    }

//    public RSDNFStatistics(String name, String defaultDisplayedParameter, Var<BigDecimal> dt, Parameter... parameter) {
//        super(name, defaultDisplayedParameter, dt, parameter);
//    }
    
}
