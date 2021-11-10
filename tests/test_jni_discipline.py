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
# Contributors:
#    INITIAL AUTHORS - API and implementation and/or documentation
#       :author: Francois Gallard
#    OTHER AUTHORS   - MACROSCOPIC CHANGES
from pathlib import Path

import jnius_config
import pytest
from gemseo.api import create_design_space
from gemseo.api import create_discipline
from gemseo.api import create_scenario
from numpy import array
from numpy.linalg import norm

# java_disc.jar results in the creation of the jar of the src.java package
JAVA_CLASS_PATH = Path().parent.parent / "target/gemseo_java-0.0.1-SNAPSHOT.jar"
jnius_config.set_classpath(str(JAVA_CLASS_PATH))

DISC_NAMES = ("Sellar1", "Sellar2", "SellarSystem")
JAVA_CLASS_NAME = "com.irt.saintexupery.problems.sellar.{}"


@pytest.fixture
def sellar_disciplines():
    disciplines = [
        create_discipline(
            "JavaDiscipline",
            java_class_name=JAVA_CLASS_NAME.format(name),
        )
        for name in DISC_NAMES
    ]
    return disciplines


@pytest.mark.parametrize("disc_name", DISC_NAMES)
def test_exec(disc_name):
    j_disc = create_discipline(
        "JavaDiscipline",
        java_class_name=JAVA_CLASS_NAME.format(disc_name),
    )
    py_disc = create_discipline(disc_name)
    out_ref = py_disc.execute()
    j_out = j_disc.execute({k: v.real for k, v in py_disc.default_inputs.items()})

    assert sorted(py_disc.get_input_data_names()) == sorted(
        j_disc.get_input_data_names()
    )
    assert sorted(py_disc.get_output_data_names()) == sorted(
        j_disc.get_output_data_names()
    )

    for dname in py_disc.get_output_data_names():
        assert (j_out[dname] == out_ref[dname]).all()


def test_sellar_scenario(sellar_disciplines):
    design_space = create_design_space()

    design_space.add_variable("x_local", 1, l_b=0.0, u_b=10.0, value=0.5)
    design_space.add_variable(
        "x_shared", 2, l_b=(-10, 0.0), u_b=(10.0, 10.0), value=array([1.0, 0.5])
    )

    scenario = create_scenario(
        sellar_disciplines,
        formulation="MDF",
        objective_name="obj",
        design_space=design_space,
        main_mda_class="MDAGaussSeidel",
    )
    scenario.add_constraint("c_1", "ineq")
    scenario.add_constraint("c_2", "ineq")
    scenario.set_differentiation_method("finite_differences", 1e-6)
    scenario.execute({"algo": "SLSQP", "max_iter": 100})

    assert norm(scenario.get_optimum().x_opt - array([0.0, 1.953, 0.0])) < 1e-3
