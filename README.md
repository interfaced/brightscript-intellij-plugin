# BrightScript plugin

## Info

[BrightScript](https://docs.brightsign.biz/display/DOC/Language+Reference) language support for IntelliJ Platform

## Features

- Syntax highlighting
- Code formatting (auto-indentation)
- Code folding
- Statements auto-completion
- GoTo declaration

## Build

### Requirements

* Java 8

### Instructions

1. Clone project repo 
```
git clone https://github.com/interfaced/brightscript-intellij-plugin.git
```
2. Enter to the project dir
```
cd brightscript-intellij-plugin
```
3. Run build task
```
./gradlew buildPlugin
```

## Test

* Run auto-tests
```
./gradlew test
```
* Run IDE instance with installed plugin
```
./gradlew runIde
```
