# Copyright 2021 IRT Saint Exupéry, https://www.irt-saintexupery.com
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License version 3 as published by the Free Software Foundation.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
import jnius_config
from gemseo.api import configure_logger
from gemseo.api import create_design_space
from gemseo.api import create_discipline
from gemseo.api import create_scenario
from numpy import array

# Configure jnius to detect the jac and update the classpath
# to be able to load the Java implementation of the sellar problem
# java_disc.jar results in the creation of the jar of the src.java package
jnius_config.set_classpath("java_disc.jar")

configure_logger()

design_space = create_design_space()

design_space.add_variable("x_local", 1, l_b=0.0, u_b=10.0, value=0.5)
design_space.add_variable(
    "x_shared", 2, l_b=(-10, 0.0), u_b=(10.0, 10.0), value=array([1.0, 0.5])
)

disciplines = [
    create_discipline(
        "JavaDiscipline", java_class_name="com.irt.saintexupery.problems.sellar." + name
    )
    for name in ("Sellar1", "Sellar2", "SellarSystem")
]

scenario = create_scenario(
    disciplines,
    formulation="MDF",
    objective_name="obj",
    design_space=design_space,
    main_mda_class="MDAGaussSeidel",
)
scenario.add_constraint("c_1", "ineq")
scenario.add_constraint("c_2", "ineq")
scenario.set_differentiation_method("finite_differences", 1e-7)
scenario.execute({"algo": "SLSQP", "max_iter": 100})

scenario.post_process("OptHistoryView", save=True, show=False)

optimum = scenario.get_optimum().get_data_dict_repr()
