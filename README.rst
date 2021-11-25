..
    Copyright 2021 IRT Saint Exupéry, https://www.irt-saintexupery.com

    This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
    International License. To view a copy of this license, visit
    http://creativecommons.org/licenses/by-sa/4.0/ or send a letter to Creative
    Commons, PO Box 1866, Mountain View, CA 94042, USA.

GEMSEO java interface
*********************

Contains two Java-GEMSEO interfaces:

- One is based on JNIUS (https://pyjnius.readthedocs.io/en/stable)
  to call a Java implementation from a standard Python GEMSEO scenario.
- The other is based on JEP (https://github.com/ninia/je)
  to make a Java code create and use a GEMSEO scenario,
  eventually containing GEMSEO disciplines implemented in Java.

Installation
------------
Set ``JAVA_HOME``: path to the root installation directory of the Java JDK.

Install with:

.. code:: console

   pip install .

At runtime, you shall configure:

- On linux, ``LD_LIBRARY_PATH``: add the path to the ``JEP`` python package,
- On MacOS, ``DYLD_LIBRARY_PATH``: add the path to the ``JEP`` python package,
- On Windows, ``PATH``: add the path to the ``JEP`` python package,
- ``CLASSPATH``: add the jep package provided when installing jep in the Python
  distribution to the java classpassth,

See the JEP (https://github.com/ninia/jep/wiki/Getting-Started) documentations for further information.

Java MDODiscipline
------------------

The Java abstract MDODiscipline is defined in the package ``com.irt.saintexupery.discipline``.

Examples for the Sellar problem: ``com.irt.saintexupery.problems.sellar``

The analytical derivatives (``gemseo.discipline.MDODiscipline._compute_jacobian``) are not supported yet.

JEP specific issues
-------------------

For the JEP interface,
you shall wrap the ``MDODiscipline`` wrapper using the ``JepMDODisciplineAdapter``:

.. code:: java

  import com.irt.saintexupery.discipline.JepMDODisciplineAdapter;
  import com.irt.saintexupery.problems.sellar.Sellar1;
  MDODiscipline sellar1 = new JepMDODisciplineAdapter(new Sellar1());

Examples
--------

Please look at examples/java_examples and examples/python_examples.

Frequent issues
---------------

"Exception in thread "main" java.lang.UnsatisfiedLinkError: no jep in java.library.path:"
Add Jep to the classpath.

If Jep is still undetected, please check that the compiled "jep.dll" is well included as a native library.

Some ideas on Java-Python bridge technologies
---------------------------------------------

Many libraries provide Java-Python interprocess communications and serialization.
However many of them have limitations such as Jython that does not support all the compiled
extensions of Python because it is a re-implementation of the Python interpreter in Java.
Others use sockets such as py4j and this can deal with performance and security issues.

Both JNIUS and JEP are based on the C APIs of CPython and Java JNI (Java Native Interface).
This avoids memory copies,
so precision and performance losses, which is key for numerical computing.

JNIUS allows to call Java code from python,
JEP allows to call Python from Java,
and re-enter in the Java code.
However both technologies cannot be mixed,
JEP cannot call JNIUS code.

This is why the two solutions are proposed here.

Authors
-------

- François Gallard
- Pascal Le Métayer
- Antoine Dechaume

License
-------

LGPL v3.0
