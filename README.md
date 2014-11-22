windowlib
=========

[java.awt.Robot](https://docs.oracle.com/javase/8/docs/api/java/awt/Robot.html)
allows Java programs to simulate mouse clicks and keypresses, but doesn't help
in finding where windows are or if they're minimized etc., unless you're willing
to do image recognition on screen captures.

windowlib aims to provide these missing features.

Dependencies
------------

[BridJ](https://code.google.com/p/bridj/)

Building
--------

`ant fetch; ant jar`

License
-------

GPLv3+.
