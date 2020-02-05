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

On Windows you need to delete the following duplicate java files to compile
after cloning this repo.

```
libclient/Move.java
libclient/javadoc/Move.java
libclient/javadoc/GthClient.java
```

Build with `javac Grossthello.java`. Run with for example

        java Grossthello white localhost 0 3

where `white` is the side to play, `localhost` is the
machine on which the game server
[gthd](http://pdx-cs-ai.github.com/gothello-gthd) is
running, 0 is the "server number" on that server, and
3 is the negamax search depth. You can add `log` at the end
of the command to have the player log scores to `stderr`.
