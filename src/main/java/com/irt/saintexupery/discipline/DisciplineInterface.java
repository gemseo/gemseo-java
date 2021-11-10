
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

import java.util.HashMap;
import java.util.List;


/**
 * The discipline interface declares the required methods
 * to configure the input and output grammars,
 * define the default_inputs and to execute
 * the discipline.
 */
public interface DisciplineInterface {
	/**
	 * Run method like gemseo.MDODiscipline._run
	 *
	 * @param arrayData the data to execute the discipline.
	 * @return the execution results.
	 */
    HashMap<String, Double[]> run(HashMap<String, Double[]> data);
    /**
     * Get the list of input names to define the input grammar.
     *
     * @return the list of names.
     */
    List<String> getInputDataNames();
    /**
     * Get the list of output names to define the input grammar.
     *
     * @return the list of names.
     */
    List<String> getOutputDataNames();
    /**
     * Set one default input of the discipline.
     *
     * @param dataName name of the data.
     * @param value value of the data.
     */
    void setDefaultInput(String dataName, Double[] value);
    HashMap<String, Double[]> getDefaultInputs();
}
