# WASMBots

This is an effort to create a game engine in which independent WebAssembly modules are loaded, each of which "drives" a robot in a combat arena against other robots. In other words, if you ever played RobotWar (in the 80s) or Robocode (in Java), this is an effort to recreate that, but allowing for any WebAssembly-targeting language rather than just Java (or the pidgin BASIC that RobotWar used).

Part of this is to scratch an itch I've had for a few years (I keep imagining this same mechanic in a variety of different forms--at one point I even tried to imagine what it'd be like using something like CORBA!), and part of this is to spend some time exploring WASM interfaces and modules and engines.

## Quirks
There's a few self-imposed "quirks" I'm putting in place for this project:

***No Rust.*** Nothing against Rust, it's just that every example on the Internet around WASM (including those from the WebAssembly folks themselves!) are Rust-ian. If WASM is really a viable target for multiple languages, then let's test that by keeping Rust entirely out of the picture.

***Separate engine from display.*** The bots should run inside the engine, and the engine should make the state of each of the bots accessible in such a way that the display could take several forms. (This helps mimic approaches that are less game-centric, as well as makes it interesting for remote sorts of scenarios.)

## Glossary
`arena`:

    1. The environment in which the battle takes place. To begin, it will be an empty space some 1000x1000 "units" in size; in a future revision, arenas will be of varying size and contain obstacles or cover (that is, each arena will have an associated "map").
    2. The definition of the software environment that a bot interacts with.

`bot`: 

    1. A combatant in the arena
    2. The software code that runs the combatant in the arena

`host`: The software that loads the bots into memory, then runs the time cycles that each bot experiences. The host process may or may not be a WASM process itself.

## Contents

### engine
This directory contains the core engine, which knows how to load botcode, call into it, and runs the loop that defines the time moments that make up the simulation.

### interfaces
This directory contains the WASM interfaces that a bot must implement in order to be loaded and run within the system.

The `bot.world` file describes the collective bot/arena experience; 

### js-bot
The JS implementation of a bot.

### py-bot
The Python implementation of a bot.

# Bibliography
This project is highly dependent on several concepts, and a collection of the resources I find useful to understand these concepts appears below.

https://component-model.bytecodealliance.org/ - The WebAssembly Component Model

