
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

package com.irt.saintexupery.problems.sellar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SellarSystem extends SellarDiscipline {
	@Override
	public HashMap<String, Double[]> run(HashMap<String, Double[]> data) {
		HashMap<String, Double[]> result = new HashMap<>();
		Double[] x = data.get("x_local");
		Double[] y0 = data.get("y_1");
		Double[] y1 = data.get("y_2");
		Double[] x_shared = data.get("x_shared");

		Double obj[] = { Math.pow(x[0], 2) + x_shared[1] + y0[0] + Math.exp(y1[0] * -1) };
		result.put("obj", obj);
		Double c_1[] = { 3.16 - Math.pow(y0[0], 2) };
		result.put("c_1", c_1);
		Double c_2[] = { y1[0] - 24.0 };
		result.put("c_2", c_2);
		return result;
	}

	@Override
	public List<String> getInputDataNames() {
		return Arrays.asList("x_local", "y_1", "y_2", "x_shared");
	}

	@Override
	public List<String> getOutputDataNames() {
		return Arrays.asList("obj", "c_1", "c_2");
	}
}
