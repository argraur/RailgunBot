[![License: Apache-2.0](https://img.shields.io/badge/License-Apache%202.0-lightgrey.svg)](http://www.apache.org/licenses/LICENSE-2.0)
![Java CI with Gradle](https://github.com/argraur/RailgunBot/workflows/Java%20CI%20with%20Gradle/badge.svg)

# Misaka-chan Discord Bot

A simple utility Discord bot.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Java Development Kit 11 or above.

### Cloning

```
git clone https://github.com/argraur/RailgunBot -b master
```

### Building

Linux & macOS:
```
cd RailgunBot; ./gradlew build
```

Windows:
```cd RailgunBot\
.\gradlew.bat build
```

## Configuration

Configurations are located in `src/main/resources/config.properties` file.

### Available configurations:

`token`: Your Discord Bot token

`giphy`: Giphy Developer token

`command_prefix`: Prefix for all commands received by a bot (default is '>')

## Commands

### Available commands at the moment:

`>ping` - Pong!

`>long` - loooooooooooooooooong

`>kitsu genre <genre>` - Sends random anime of a given genre.

`>kitsu search <query>` - Searches anime by given query.

`>slap <mention>` - SLAP THAT LOSER!

`>hug <mention>` - Hugs the mentioned user!

`>gif <query> [mention]` - Search gif for a given query

`>ignore <mention>` - Ignores given user.

`>pardon <mention>` - Stops ignoring given user.

`>mock` -  Mocks GIven mEssAGe

`>calc` - Simple calculator. Example: >calc 2 * 2 * 2 (spaces are mandatory!)

`>help` - returns bot help

## Built with

* [Gradle](https://github.com/gradle/gradle) - Build automation tool
* [JDA](https://github.com/DV8FromTheWorld/JDA) - Java wrapper for Discord
* [Giphy4J](https://github.com/keshrath/Giphy4J) - Java wrapper for Giphy
* [OkHttp](https://github.com/square/okhttp) - HTTP client for Java

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.