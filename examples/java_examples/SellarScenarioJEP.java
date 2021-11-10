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

package java_examples;

import java.util.ArrayList;
import java.util.HashMap;

import com.irt.saintexupery.discipline.JepMDODisciplineAdapter;
import com.irt.saintexupery.discipline.MDODiscipline;
import com.irt.saintexupery.problems.sellar.Sellar1;
import com.irt.saintexupery.problems.sellar.Sellar2;
import com.irt.saintexupery.problems.sellar.SellarSystem;

import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;

public class SellarScenarioJEP {

	public Interpreter create_interpreter() throws JepException {
		SharedInterpreter interp = new SharedInterpreter();
		interp.exec("from jep import redirect_streams");
		interp.exec("redirect_streams.setup()");
		return interp;
	}

	public static void main(String[] args) {
		/*
		 *
		 * To run, add :
		 * - PYTHONPATH = path to src.python
		 * - PYTHONHOME = path to Python folder
		 * - The path to the JDK in PATH ($user\AppData\Local\jdk-xxx)
		 *
		 *
		 */
		SellarScenarioJEP runner = new SellarScenarioJEP();
		try (Interpreter interp = runner.create_interpreter()) {
			interp.exec("from gemseo.api import configure_logger, create_discipline");
			interp.exec("from  gemseo_java.jep_java_discipline import JEPJavaDiscipline");
			interp.exec("configure_logger()");

			MDODiscipline sellar1 = new JepMDODisciplineAdapter(new Sellar1());
			interp.set("sellar1_java", sellar1);
			interp.exec("sellar1=JEPJavaDiscipline('Sellar1', sellar1_java)");
			interp.exec("sellar1.execute()");

			MDODiscipline sellar2 = new JepMDODisciplineAdapter(new Sellar2());
			interp.set("sellar2_java", sellar2);
			interp.exec("sellar2=JEPJavaDiscipline('Sellar2', sellar2_java)");

			MDODiscipline sellar_system = new JepMDODisciplineAdapter(new SellarSystem());
			interp.set("sellar_system_java", sellar_system);
			interp.exec("sellar_system=JEPJavaDiscipline('SellarSystem', sellar_system_java)");

			interp.exec("from gemseo.api import create_design_space, create_scenario");

			interp.exec("design_space=create_design_space()");
			interp.exec("from numpy import array");
			interp.exec("design_space.add_variable('x_local', 1, l_b=0.0, u_b=10.0, value=0.5)");
			interp.exec("design_space.add_variable('x_shared', 2, l_b=(-10, 0.0), u_b=(10.0, 10.0), value=array([1.0, 0.5]))");
			interp.exec("disciplines=[sellar1,sellar2,sellar_system]");
			interp.exec(
					"scenario=create_scenario(disciplines, formulation='MDF', objective_name='obj',design_space=design_space,"
							+ "main_mda_class='MDAGaussSeidel')");
			interp.exec("scenario.add_constraint('c_1','ineq')");
			interp.exec("scenario.add_constraint('c_2','ineq')");
			interp.exec("scenario.set_differentiation_method('finite_differences',1e-7)");
			interp.exec("scenario.execute({'algo':'SLSQP', 'max_iter':100})");
			interp.exec("scenario.post_process('OptHistoryView', save=True, show=False)");

			interp.exec("optimum=scenario.get_optimum().get_data_dict_repr()");
			interp.exec("x_opt=optimum['x_opt'].tolist()");
			HashMap<String, Object> optimum_dict = (HashMap<String, Object>) interp.getValue("optimum");
			System.out.println(optimum_dict);
			ArrayList<Double> xopt = (ArrayList<Double>) interp.getValue("x_opt");
			System.out.println(xopt);
			double f_opt = (double) optimum_dict.get("f_opt");
			System.out.println(f_opt);
			interp.close();
		} catch (JepException e) {
			e.printStackTrace();
		}

	}
}
