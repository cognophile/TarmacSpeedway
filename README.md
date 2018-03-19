# TarmacSpeedway
A distributed client-server 2-player arcade, F1 racing game, optionally running in one client, or independant clients. 

## What's the `Part-{x}` thing about?

`Part-A` - A simple game splash screen, with a spinning car image.

`Part-B` - A localhost client-server 2-player version of the game. Both players play on the same machine, using two control key sets. This is based on part A (splash screen to load client and server).

`Part-C` - A distributed version of the client-server 2-player game. Each player has their own client, and the server is independant. This is based on part A to load the client; the server is loaded independantly. 

```
       B
      /
---- A
      \
       C
```

## Opening the project
### IntelliJ IDEA
- From the IntelliJ splash screen, _`open`_ the `Part-{x}` directory. 

- Right-click the module in the project window and click _'Open module settings'_.

- Ensure the Project SDK, language level, and output path are set as expected. You may need to create a new folder named `out` under the `Part-{x}` directory and select this for the compiler output. 

- Select _'Modules'_ from the left-hand _'Project settings'_ menu and select the 
_'Sources'_ tab on the right. 

- Select the `src` directory and mark as _'Sources'_, `src/test` as _'Tests'_, and `src/main/resources` as _'Resources'_. Click _'OK'_.

- Configure a runner: _'Run'_ > _'Run...'_ or _'Edit configurations...'_ > set the class path to `main.java.ApplicationLauncher`.

## Using Part C
- Compile and run `TCPServerDaemon.java` through a fresh terminal window.

- Compile and run `ApplicationLauncher.java` and all other classes contained in the `client` subdirectory through another fresh terminal (or build and run via an IDE).