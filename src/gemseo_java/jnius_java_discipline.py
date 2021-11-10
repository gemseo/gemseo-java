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
"""Python wrapper for Java Disciplines."""
from gemseo.core.discipline import MDODiscipline
from jnius import autoclass
from numpy import array
from numpy import fromiter
from numpy import ndarray

HashMap = autoclass("java.util.HashMap")
ArrayList = autoclass("java.util.ArrayList")
Double = autoclass("java.lang.Double")
String = autoclass("java.lang.String")
Integer = autoclass("java.lang.Integer")
Float = autoclass("java.lang.Float")
List = autoclass("java.util.List")
JavaArray = autoclass("java.lang.reflect.Array")


def to_java(value):
    """Convert a python native typed object to a Java jnius object.

    Args:
        value: The value to convert.

    Returns:
        The converted object.
    """
    if isinstance(value, str):
        return String(value)
    if isinstance(value, int):
        return Integer(value)
    if isinstance(value, float):
        return Float(value)
    if isinstance(value, (list, ndarray)):
        java_value = ArrayList()
        for v in value:
            java_value.add(Double(v))
        return java_value
    if isinstance(value, dict):
        java_map = HashMap()
        for k, v in value.items():
            java_map.put(String(k), to_java(v))
        return java_map
    raise TypeError(type(value))


def to_python(value):
    """Convert a jnius object to a Python native typed object.

    Args:
        value: The value to convert.

    Returns:
        The converted object.
    """
    if isinstance(value, String):
        return str(value)
    if isinstance(value, Integer):
        return int(value)
    if isinstance(value, Float):
        return float(value)
    if isinstance(value, ArrayList):
        return array([to_python(v) for v in value])
    if isinstance(value, HashMap):
        return {str(k): to_python(value.get(k)) for k in value.keySet()}
    if isinstance(value, (float, int, str, list)):
        return value
    if isinstance(value, List):
        return fromiter(value, dtype="d")
    raise TypeError(type(value))


class JavaDiscipline(MDODiscipline):
    """A Java discipline wrapper.

    Allow to call from Python a Java implementation that inherits from the Java
    abstract class: com.irt.saintexupery.discipline.MDODiscipline.

    To use it, call the constructor with the Java class path such as
    disc=JavaDiscipline('com.irt.saintexupery.problems.sellar.Sellar1')
    """

    def __init__(self, java_class_name, *klass_args):  # noqa: D205,D212,D415
        """
        Args:
            java_class_name: The java class path.
            *klass_args: The arguments to pass to the constructor use jnius types.
        """
        super().__init__(java_class_name)
        self.__klass = autoclass(java_class_name)
        self.__instance = self.__klass(*klass_args)
        input_names = [s for s in self.__instance.getInputDataNames()]
        self.input_grammar.initialize_from_data_names(input_names)
        output_names = [s for s in self.__instance.getOutputDataNames()]
        self.output_grammar.initialize_from_data_names(output_names)
        self.default_inputs = to_python(self.__instance.getDefaultInputs())

    def _run(self):
        """Call the run method of the Java implementation.

        The values values returned by java are converted to a dict of native Python
        types.
        """
        java_inpts = to_java(self.local_data)
        out = self.__instance.runWithLists(java_inpts)
        self.local_data = to_python(out)
