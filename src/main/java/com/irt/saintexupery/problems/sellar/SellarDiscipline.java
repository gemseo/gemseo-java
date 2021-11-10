
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

import com.irt.saintexupery.discipline.MDODiscipline;

public abstract class SellarDiscipline extends MDODiscipline {
	public SellarDiscipline() {
		super();
		if(this.getInputDataNames().contains("x_local")) {
			this.defaultInputs.put("x_local", new Double[] { 0.0d });
		}
		if(this.getInputDataNames().contains("x_shared")) {
			this.defaultInputs.put("x_shared", new Double[] { 1.0d, 0.0d });
		}
		if(this.getInputDataNames().contains("y_1")) {
			this.defaultInputs.put("y_1", new Double[] { 1.0d });
		}
		if(this.getInputDataNames().contains("y_2")) {
			this.defaultInputs.put("y_2", new Double[] { 1.0d });
		}
	}

}
