android-beutils
===============

```bash
  $ git submodule update --init
  ...
  $ ./gradlew build
  ...
```
When build is finished aar file will be located in `build/libs`.

Maven artifact in local repo
======================

```bash
  $ ./gradlew clean build uploadArchives
```