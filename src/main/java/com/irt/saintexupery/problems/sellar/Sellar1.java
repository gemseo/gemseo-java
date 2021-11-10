
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

public class Sellar1 extends SellarDiscipline {

	@Override
	public HashMap<String, Double[]> run(HashMap<String, Double[]> data) {
		HashMap<String, Double[]> result = new HashMap<>();
		Double content[] = {
				Math.pow(Math.pow(data.get("x_shared")[0], 2) + data.get("x_shared")[1] + data.get("x_local")[0] - 0.2 * data.get("y_2")[0],0.5) };
		result.put("y_1", content);
		return result;
	}

	@Override
	public List<String> getInputDataNames() {
		return Arrays.asList("x_local", "y_2", "x_shared");
	}

	@Override
	public List<String> getOutputDataNames() {
		return Arrays.asList("y_1");
	}
}
