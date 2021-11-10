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
#    INITIAL AUTHORS - initial API and implementation and/or initial
#                           documentation
#        :author: Francois Gallard
#    OTHER AUTHORS   - MACROSCOPIC CHANGES
"""XXX."""
from gemseo.core.discipline import MDODiscipline
from numpy import fromiter


def hashmap2dict(hash_map):
    """XXX."""
    return {k: fromiter(hash_map[k], "d") for k in hash_map}


class JEPJavaDiscipline(MDODiscipline):
    """XXX."""

    def __init__(self, name, java_discipline, *klass_args):
        """XXX."""
        super().__init__(name)
        self.__instance = java_discipline
        input_names = self.__instance.getInputDataNames()
        self.input_grammar.initialize_from_data_names(input_names)
        output_names = self.__instance.getOutputDataNames()
        self.output_grammar.initialize_from_data_names(output_names)
        self.default_inputs = hashmap2dict(java_discipline.getDefaultInputs())

    def _run(self):
        """XXX."""
        # data_java = HashMap()
        # for k, v in self.local_data.items():
        # v_list = ArrayList()
        # v_list += [Double(i) for i in v]
        # print(type(v_list[0]))
        # data_java[String(k)] = v_list
        # data_java = {k: arr.tolist() for k, arr in self.local_data.items()}
        # print(data_java)
        out = self.__instance.runWithNDArray(self.local_data)
        out = hashmap2dict(out)
        self.local_data = out
