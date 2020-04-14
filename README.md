[![License: Apache-2.0](https://img.shields.io/badge/License-Apache%202.0-yellow.svg)](http://www.apache.org/licenses/LICENSE-2.0)
![Java CI with Gradle](https://github.com/argraur/RailgunBot/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![Known Vulnerabilities](https://snyk.io/test/github/argraur/RailgunBot/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/argraur/RailgunBot?targetFile=build.gradle)

# Misaka-chan Discord Bot

A simple utility Discord bot.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java Development Kit 8 - 11.

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
```
cd RailgunBot\
.\gradlew.bat build
```

## Configuration

Configurations are located in `src/main/resources/config.properties` file.

### Available configurations:

`token`: Your Discord Bot token

`giphy`: Giphy Developer token

`command_prefix`: Prefix for all commands received by a bot (default is '>')

`activity`: Set bot activity (e.g. `anime games`)

## Commands

### Available commands at the moment:

`>ban <mention>` - Bans mentioned user (Ban permission required, for a bot and command issuer)

`>calc` - Simple calculator. Example: >calc 2 * 2 * 2 (spaces are mandatory!)

`>color [attach image or url]` - Gets dominating color out of given image.

`>del [message link]` - Delete given message. (Message manage permission required, for a bot and command issuer)

`>gif <query> [mention]` - Search gif for a given query

`>hug <mention>` - Hugs the mentioned user!

`>ignore <mention>` - Ignores given user.

`>kick <mention>` - Kicks mentioned user. (Kick permission required, for a bot and command issuer)

`>kitsu genre <genre>` - Sends random anime of a given genre.

`>kitsu search <query>` - Searches anime by given query.

`>mock` - mockS GiveN mESsAge

`>long` - loooooooooooooooooong

`>pardon <mention>` - Stops ignoring given user.

`>ping` - Pong!

`>sauce [attach image or pass url]` - Searches for art sauce

`>slap <mention>` - SLAP THAT LOSER!

`>ud <word or expression>` - Search for given word or expression on UrbanDictionary

`>wait [attached image]` - WAIT: What Anime Is This? Searches for anime using given screenshot from anime!

`>help` - returns bot help

## Built with

* [Gradle](https://github.com/gradle/gradle) - Build automation tool
* [JDA](https://github.com/DV8FromTheWorld/JDA) - Java wrapper for Discord
* [Giphy4J](https://github.com/keshrath/Giphy4J) - Java wrapper for Giphy
* [OkHttp](https://github.com/square/okhttp) - HTTP client for Java
* [ColorThief](https://github.com/SvenWoltmann/color-thief-java) - Dominant color extractor for Java
* [Kitsu](https://kitsu.io) - Anime exploring site
* [SauceNAO](https://saucenao.com) - Anime art reverse image search engine
* [trace.moe](https://trace.moe) - Search engine that helps tracing back the original anime by screenshot.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Arseniy Graur** - *Project creator* - [argraur](https://github.com/argraur)

See also the list of [contributors](https://github.com/argraur/RailgunBot/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
