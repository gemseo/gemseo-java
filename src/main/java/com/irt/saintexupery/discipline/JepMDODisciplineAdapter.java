
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
import java.util.Map;

import com.irt.saintexupery.discipline.MDODiscipline;
import jep.NDArray;

public class JepMDODisciplineAdapter extends MDODiscipline {
	public MDODiscipline baseDiscipline;

	public JepMDODisciplineAdapter(MDODiscipline baseDiscipline) {
		super();
		this.baseDiscipline = baseDiscipline;

	}

	@Override
	public void setDefaultInput(String dataName, Double[] value) {
		this.baseDiscipline.setDefaultInput(dataName, value);
	}

	@Override
	public HashMap<String, Double[]> getDefaultInputs() {
		return this.baseDiscipline.getDefaultInputs();
	}

	@Override
	public List<String> getInputDataNames() {
		return this.baseDiscipline.getInputDataNames();
	}

	@Override
	public List<String> getOutputDataNames() {
		return this.baseDiscipline.getOutputDataNames();
	}

	public HashMap<String, Double[]> run(HashMap<String, Double[]> data) {
		return this.baseDiscipline.run(data);
	}

	public Map<String, Double[]> runWithNDArray(Map<String, NDArray<double[]>> arrayData) {
		HashMap<String, Double[]> data = new HashMap<>();
		for (Map.Entry<String, NDArray<double[]>> entry : arrayData.entrySet()) {
			String key = entry.getKey();
			double[] value = entry.getValue().getData();
			Double[] valueDouble = new Double[value.length];
			for (int i = 0; i < value.length; i++) {
				valueDouble[i] = (Double) value[i];
			}
			data.put(key, valueDouble);
		}
		return this.run(data);
	}

}
