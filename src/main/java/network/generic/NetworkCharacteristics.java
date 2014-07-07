/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.generic;

import main.java.maps.Trajectory;
import main.java.statistics.Characteristics;

/**
 *
 * @author CARRARA Nicolas
 */
public class NetworkCharacteristics extends Characteristics {
    
    public static final String LOAD_REMAINING = "current_packet";
    public static final String BUFF_MAX_LOAD = "buff_max_load";
    public static final String TRANSMISSION_TIME_BEFORE_CONGESTION = "transmission_time";

    public NetworkCharacteristics(Trajectory<Double>... params) {
        super(params);
    }
}
