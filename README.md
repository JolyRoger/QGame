# Q

"Q" game was initially released on SonyEricsson mobile phones. It represents logic game with balls and holes to where you should roll the balls up. This is "one more" version of the game written in Java.

## I just want to run it

On Linux machine you can run the game docker image with the command:
```
xhost + && \
docker run \
    -it --rm -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix \
    --name q-game \
    daniilmonakhov/q-game:latest && \
xhost -
```

You must install `xhost` first.

Remember, as far as the game utilizes UI interface, all the parameters and command `xhost` (must be installed), allowing and disallowing the application to use your host X settings, are important.
## Getting Started

Just download the game:
```
> git clone https://github.com/JolyRoger/QGame.git
```

### Prerequisites

You should have [gradle](https://gradle.org/) - the tool to build and run the game.
Also, [Java 21](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) has to be installed.

### Running

If you wish to run it directly, go to the game directory: 
```
> cd QGame
```
and run it with gradle:
```
> gradle run
```
or run it from already built jar file: 
```
> java -jar q-game.jar
```

### Control

Select a ball in white frame with "Space" key. The frame becomes red. Move ball in red frame using arrow keys. When you want to release the ball press "Space" again. The red frame turns back to white. Move the frame over the balls till the frame is on ball you want to move. When you roll up a ball into the proper hole the frame automatically is set on a next ball. The goal of Q game is rolling up all the balls to appropriate holes.

![alt text](https://ic.pics.livejournal.com/mjol1nir/16493210/6801/6801_900.png)