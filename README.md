# TarmacSpeedway
A distributed client-server 2-player arcade, F1 racing game, optionally running in one client, or independant clients. 

## What's the `Part-{x}` thing about?

`Part-A` - A simple game splash screen, with a spinning car image.

`Part-B` - A local client 2-player version of the game. Both players play on the same machine, using two control key sets. This is based on part A (splash screen to load client and server).

`Part-C` - A distributed version of the client-server 2-player game. Each player has their own client, and the server is independant. This is based on part A to load the client; the server is loaded independantly. By default, the clients and server are configured to communicate via localhost, however, with minimal changes this can be changed to enable independant hosts. 

```
       B
      /
---- A
      \
       C
```

## Opening the project
### IntelliJ IDEA

To configure IntelliJ to run the client for you: 

- From the IntelliJ splash screen, _`open`_ the `Part-{x}` directory. 

- Right-click the module in the project window and click _'Open module settings'_.

- Ensure the Project SDK, language level, and output path are set as expected. You may need to create a new folder named `out` under the `Part-{x}` directory and select this for the compiler output. 

- Select _'Modules'_ from the left-hand _'Project settings'_ menu and select the 
_'Sources'_ tab on the right. 

- Select the `src` directory and mark as _'Sources'_, `src/test` as _'Tests'_, and `src/main/resources` as _'Resources'_. Click _'OK'_.

- Configure a runner: _'Run'_ > _'Run...'_ or _'Edit configurations...'_ > set the class path to `main.java.ApplicationLauncher` (for Part C, use `main.java.client.ApplicationLauncher`)

## Using Part C (Distributed server and client)
- Requires the server be started first. 
    - Compile all classes under the `server` package, then run `ServerLauncher.java` through a fresh terminal window. You may need to navigate to `../TarmacSpeedway/Part-C/out/production/Part-C` and run `java main.java.server.ServerLauncher`. Alternatively, build and run via an IDE by creating a build configuration and pointing it at said class.

- Open 2 instances of the client on the same machine. 
    - Compile all classes under the `client` package, then run `ApplicationLauncher.java` through another fresh terminal window. You may need to navigate to `../TarmacSpeedway/Part-C/out/production/Part-C` and run `java main.java.client.ApplicationLauncher`. Alternatively, build and run via an IDE by creating a build configuration and pointing it at said class.

- Edit the `remote` key within `Part-C/main/resources/remoteConfiguration.txt` to specify either the address of your local-network server (e.g. `192.168.1.1`) or specify it as `localhost`, for both clients. Save and exit the file.

- Each client must select a Car colour. 

- Click to start the race.

## Controls

- For Part B and C, use `W`, `A`, `S`, `D` to maneuver your car. Use `R`to reset cars to the start line. 

- For Part C, use `ESC` to close both client connections. 



