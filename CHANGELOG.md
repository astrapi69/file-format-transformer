<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# file-format-transformer Changelog

## [Unreleased]

## [2.2.0-SNAPSHOT]


## [2.1.0]

### Added

- new menu action for 'text pdf' files to transform to text files, pdf files that saves 'image pdf' are not supported 

### Fixed

- function getActionUpdateThread returns now ActionUpdateThread.BGT

## [2.0.0]

### Fixed

- overwrite of function getActionUpdateThread for not return ActionUpdateThread.OLD_EDT

### Added

- update to new project structure with toml file libs.versions.toml
- put all core dependencies to a new bundle 'core.dependencies'

## [1.8.0]

removed deprecated calls from the jetbrains api

## [1.6.0]

Added file transformations for:

- json to yaml
- json to xml
- xml to json
- properties to yaml
- properties to xml
- yaml to properties

## [1.0.0]

### Added

- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

[Unreleased]: https://github.com/astrapi69/file-format-transformer/compare/v2.1.0...HEAD
[2.1.0]: https://github.com/astrapi69/file-format-transformer/compare/v2.0.0...v2.1.0
[2.0.0]: https://github.com/astrapi69/file-format-transformer/compare/v1.8.0...v2.0.0
[1.8.0]: https://github.com/astrapi69/file-format-transformer/compare/v1.6.0...v1.8.0
[1.6.0]: https://github.com/astrapi69/file-format-transformer/compare/v1.0.0...v1.6.0
[1.0.0]: https://github.com/astrapi69/file-format-transformer/commits/v1.0.0
