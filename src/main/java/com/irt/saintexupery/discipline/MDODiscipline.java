
// Copyright 2021 IRT Saint Exupéry, https://www.irt-saintexupery.com
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License version 3 as published by the Free Software Foundation.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package com.irt.saintexupery.discipline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * An abstract implementation of the DisciplineInterface with a runWithLists method
 * to ease the pyjnius interface.
 *
 */
public abstract class MDODiscipline implements DisciplineInterface {
	public HashMap<String, Double[]> defaultInputs = new HashMap<>();

    public MDODiscipline( ) {
		super();
	}

    @Override
    public void setDefaultInput(String dataName, Double[] value) {
    	this.defaultInputs.put(dataName, value);
    }

    @Override
    public HashMap<String, Double[]> getDefaultInputs() {
    	return this.defaultInputs;
    }

    /**
     * Runs the discipline using a HashMap<String, ArrayList[]> while the original interface
     * only provides HashMap<String, Double[]>. Useful for pyjnius interface.
     *
     * @param arrayData the input data.
     * @return the output data.
     */
    public Map<String, Double[]> runWithLists(Map<String, ArrayList<Double>> arrayData){
    	HashMap<String, Double[]> data=new HashMap<>();
    	for (Map.Entry<String,  ArrayList<Double>> entry : arrayData.entrySet()) {
    	    String key = entry.getKey();
    	    Double[] value =  entry.getValue().toArray(new Double[0]);
    	    data.put(key,value);
    	}
    	return this.run(data);
    }
}
