
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.irt.saintexupery.discipline.JepMDODisciplineAdapter;
import com.irt.saintexupery.discipline.MDODiscipline;
import com.irt.saintexupery.problems.sellar.Sellar1;
import com.irt.saintexupery.problems.sellar.Sellar2;
import com.irt.saintexupery.problems.sellar.SellarSystem;

import jep.JepException;
import jep.NDArray;
import jep.SharedInterpreter;

@TestInstance(Lifecycle.PER_CLASS)
class SellarJEPTests{
	private SharedInterpreter interp ;

	@BeforeEach
	void setUp() throws JepException {
		this.interp = new SharedInterpreter();
		// These lines do not work with jep >= 4.
		// this.interp.exec("from jep import redirect_streams");
		// this.interp.exec("redirect_streams.setup()");
		this.interp.exec("from gemseo.api import configure_logger, create_discipline");
		this.interp.exec("from gemseo_java.jep_java_discipline import JEPJavaDiscipline");
		this.interp.exec("configure_logger()");
	}

	@AfterEach
	void tearDown() throws JepException {
		this.interp.close();
	}

	@Test
	void testRunSellar1() throws JepException {
		Sellar1 sellar1Java=new Sellar1();
		MDODiscipline sellar1 = new JepMDODisciplineAdapter(sellar1Java);
		this.interp.set("sellar1_java", sellar1);
		this.interp.exec("sellar1=JEPJavaDiscipline('Sellar1', sellar1_java)");
		this.interp.exec("out1=sellar1.execute()");
		this.interp.exec("y_1=out1['y_1']");
		double[] y_1 = (double[])((NDArray)interp.getValue("y_1")).getData();
		HashMap<String, Double[]> out1 = sellar1Java.run(sellar1Java.defaultInputs);
		Assertions.assertEquals(y_1[0],out1.get("y_1")[0]);
	}

	@Test
	void testRunSellar2() throws JepException {
		Sellar2 sellar2Java=new Sellar2();
		MDODiscipline sellar2 = new JepMDODisciplineAdapter(sellar2Java);
		this.interp.set("sellar2_java", sellar2);
		this.interp.exec("sellar2=JEPJavaDiscipline('Sellar2', sellar2_java)");
		this.interp.exec("out2=sellar2.execute()");
		this.interp.exec("y_2=out2['y_2']");
		double[] y_2 = (double[])((NDArray)interp.getValue("y_2")).getData();
		HashMap<String, Double[]> out2 = sellar2Java.run(sellar2Java.defaultInputs);
		Assertions.assertEquals(y_2[0],out2.get("y_2")[0]);
	}

	@Test
	void testRunSellarSystem() throws JepException {
		SellarSystem sellarSystemJava=new SellarSystem();
		MDODiscipline sellar_system = new JepMDODisciplineAdapter(sellarSystemJava);
		this.interp.set("sellar_system_java", sellar_system);
		this.interp.exec("sellar_system=JEPJavaDiscipline('SellarSystem', sellar_system_java)");
		this.interp.exec("out_sys=sellar_system.execute()");
		this.interp.exec("c_1=out_sys['c_1']");
		double[] c_1 = (double[])((NDArray)interp.getValue("c_1")).getData();
		HashMap<String, Double[]> out_sys = sellarSystemJava.run(sellarSystemJava.defaultInputs);
		Assertions.assertEquals(c_1[0],out_sys.get("c_1")[0]);
	}
}
