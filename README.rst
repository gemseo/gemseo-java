..
    Copyright 2021 IRT Saint Exupéry, https://www.irt-saintexupery.com

    This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
    International License. To view a copy of this license, visit
    http://creativecommons.org/licenses/by-sa/4.0/ or send a letter to Creative
    Commons, PO Box 1866, Mountain View, CA 94042, USA.

GEMSEO java interface
*********************

Contains two Java-GEMSEO interfaces:

- One is based on JNIUS https://pyjnius.readthedocs.io/en/stable/
  to call Java implementation from a standard Python GEMSEO scenario
- The other is based on JEP https://github.com/ninia/jep to make a Java code create and use a GEMSEO scenario,
  eventually containing GEMSEO disciplines implemented in Java

Starting
--------
First you need a working GEMSEO installation

To use JNIUS, type pip install pyjnius.

To use JEP, you may follow this:
https://github.com/ninia/jep/wiki/Getting-Started

pip install jep also works, and then you must configure the runtime environment to make the execution work

Java MDODiscipline
------------------

The Java abstract MDODiscipline is defined in the package com.irt.saintexupery.discipline.

You have examples here for the Sellar problem : com.irt.saintexupery.problems.sellar

Analytical derivatives (gemseo.discipline.MDODiscipline.\_compute\_jacobian) is not supported yet.

JEP specific issues
-------------------
For the JEP interface, you must wrap the MDODiscipline wrapper using the JepMDODisciplineAdapter:
import com.irt.saintexupery.discipline.JepMDODisciplineAdapter;
import com.irt.saintexupery.problems.sellar.Sellar1;
MDODiscipline sellar1 = new JepMDODisciplineAdapter(new Sellar1());

For the runtime, you must configure:
To run, add:

- PYTHONPATH = path to this package's src.python
- PYTHONHOME = path to the Python interpreter folder
- PATH = The path to the JDK in ($user\AppData\Local\jdk-xxx)
- CLASSPATH : add the jep package provided when installing jep in the Python
  distribution to the java classpassth (typically PYTHONHOME\Lib\site-packages\jep)

Examples
--------
Please look at examples/java\_examples and examples/python\_examples folders.

Frequent issues
---------------

"Exception in thread "main" java.lang.UnsatisfiedLinkError: no jep in java.library.path:"
Add Jep to the classpath

If Jep is still undetected, please check that the compiled "jep.dll" is well included as a native library.

Some ideas on Java-Python bridge technologies
---------------------------------------------
Many libraries provide Java-Python interprocess communications and serialization.
However many of them have limitations such as Jython that does not support all compiled
extensions of Python because it is a re implementation of Python interpreter in Java.
Others use sockets such as py4j, and this can deal performance and security issues.

Both JNIUS and JEP are based on the C APIs of CPython and Java JNI (Java Native Interface).
This avoids memory copies, so precision and performance losses, which is key for numerical computing.

JNIUS allows to call Java code from python, JEP allows to call Python from Java, and re enter in the Java code.
However both technologies cannot be mixed, JEP cannot call JNIUS code.

This is why the two solutions are proposed here.

Authors
-------
François Gallard
Pascal Le Métayer
Antoine Dechaume

License
-------
LGPL v3.0
