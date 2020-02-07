# Grossthello: Gothello AI
Bart Massey

This is a simple Java AI for playing
[Gothello](http://pdx-cs-ai.github.io/gothello-project). It
uses depth-limited negamax search.

This repo uses Git submodules to borrow pieces from the
other repos. Say `git submodule update --init` before you
try to compile.

To compile and run this, you will have to set your Java
`CLASSPATH`. On Linux

        export CLASSPATH=`pwd`:`pwd`/libclient:`pwd`/gthd

Running this on Windows is a fairly involved process

1) Make sure this server and the AI player are both built into `class` files in the main directory. 
2) Update your Java JRE
3) Set your classpath on windows to point to the latest one first (Unless you really wanna run with 8)
4) Set your classpath on windows to point to the directories the server and player are built in
5) If you prefer Python to Powershell then use `os.system()` to write a function call that will call java endlessly with the args you want. This will block since it is a server so we need to manage this.
6) Have one IDE shell running a short script that will call the server endlessly.
7) Have another IDE shell running a short script that will call the enemy AI player endlessly.
8) Run your player in a third IDE shell.

Help setting classpath on Windows
[How to access the menu](https://javarevisited.blogspot.com/2013/02/windows-8-set-path-and-classpath-java-windows-7.html)
[How to set Java classpath](https://javatutorial.net/set-java-home-windows-10)

Troubleshooting
* If you get [this error](https://stackoverflow.com/questions/10382929/how-to-fix-java-lang-unsupportedclassversionerror-unsupported-major-minor-versi?page=1&tab=votes#tab-top)
make sure your classpath finds the latest JRE first in your PATH variable and not "javapath" which may lead to version 8.
* Make sure to check the hierarchy in the PATH variable, if %JAVA_HOME% is below "javapath" then you will call version 8.
* Run `java -version` to check which version Windows will call. This should mention version 13.

Build with `javac Grossthello.java`. Run with for example

        java Grossthello white localhost 0 3

where `white` is the side to play, `localhost` is the
machine on which the game server
[gthd](http://pdx-cs-ai.github.com/gothello-gthd) is
running, 0 is the "server number" on that server, and
3 is the negamax search depth. You can add `log` at the end
of the command to have the player log scores to `stderr`.
