FROM eclipse-temurin:21-jre-jammy

RUN apt-get update -y && \
    apt-get install --no-install-recommends -y unzip xorg && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    mkdir -p /home/app

COPY build/libs/q-game-1.0-SNAPSHOT.jar /home/app
COPY openjfx-21.0.1_linux-x64_bin-sdk.zip /home

RUN unzip /home/openjfx-21.0.1_linux-x64_bin-sdk.zip -d /home && \
    rm -f /home/openjfx-21.0.1_linux-x64_bin-sdk.zip

ENV LD_LIBRARY_PATH /home/javafx-sdk-21.0.1/lib

WORKDIR /home/app

CMD java \
    --module-path /home/javafx-sdk-21.0.1/lib \
    --add-modules javafx.controls,javafx.fxml \
    --add-opens java.base/java.lang=ALL-UNNAMED \
    -jar /home/app/q-game-1.0-SNAPSHOT.jar
