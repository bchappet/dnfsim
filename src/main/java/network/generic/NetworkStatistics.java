/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.network.generic;

import java.math.BigDecimal;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.statistics.Statistics;

/**
 *
 * @author CARRARA Nicolas
 */
public class NetworkStatistics extends Statistics
{

    public NetworkStatistics(String name, String defaultDisplayedParameter, Var<BigDecimal> dt, Parameter... parameter) {
        super(name, defaultDisplayedParameter, dt, parameter);
    }
    
}
