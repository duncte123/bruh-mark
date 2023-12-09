[openjdk]: https://www.azul.com/downloads/?version=java-21-lts&architecture=x86-64-bit&package=jre#zulu
[download]: https://github.com/duncte123/bruh-mark/releases/latest

# Bruh Mark
My solution for day 5 part 2 of Advent of Code 2023 was so hard on the CPU that I decided to turn it into a weird benchmark.

My own score: DNF

## Running the application
To run this application you will need to install java 21 or newer, you can download [openjdk here][openjdk]. I built this application with Azul's jdk so that one is validated to work.
Once you have downloaded and installed java, you can validate the installation by running `java -version` in your favourite terminal, it should list version 21.

Next up you can download the latest version of Bruh Mark from the [releases page][download].
After that, you should open a terminal window in the directory that you downloaded `BruhMark.jar` into and run `java -jar BruhMark.jar`

## Compiling from source
To compile from source, you will need to have the JDK installed instead of the JRE. After that, you need to run the following command in the folder that contains the `src` directory of this project

```bash
./gradlew build
```
(On windows you will need to run `gradlew.bat build`)

The resulting file will be in `build/libs/BruhMark.jar`
