
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

package sellar;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.irt.saintexupery.problems.sellar.Sellar1;
import com.irt.saintexupery.problems.sellar.Sellar2;
import com.irt.saintexupery.problems.sellar.SellarSystem;

class SellarTests {

	@Test
	void testRunSellarDisciplines() {
		Sellar1 sellar1 = new Sellar1();
		HashMap<String, Double[]> out1 = sellar1.run(sellar1.defaultInputs);
		Assertions.assertArrayEquals(new Double[] { Math.pow(0.8d, 0.5) }, out1.get("y_1"));

		Sellar2 sellar2 = new Sellar2();
		HashMap<String, Double[]> out2 = sellar2.run(sellar2.defaultInputs);
		Assertions.assertArrayEquals(new Double[] { 2.0d }, out2.get("y_2"));

		SellarSystem sellarSystem = new SellarSystem();
		HashMap<String, Double[]> outSystem = sellarSystem.run(sellarSystem.defaultInputs);
		Assertions.assertArrayEquals(new Double[] { 2.16d }, outSystem.get("c_1"));
	}

}
