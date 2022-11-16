# Run all tests

# For Linux and Mac

```shell
./gradlew cucumber
```

# For Windows

```shell
gradlew.bat cucumber
```

You may encounter character encoding issue in terminal. If so, please try to fix it by following this
link https://akr.am/blog/posts/using-utf-8-in-the-windows-terminal

# Run tests in Intellij

* Install Intellij IDEA (either Ultimate or Community version)
  * when installing the community version, please also install the "Cucumber for Java" plugin
* Open the repo root folder with Intellij and wait for this gradle project loaded completely
* Open the feature file at `src/test/resources/homework.feature` and run it by clicking the green run test gutter
  icon on the left bar and test should pass

# Additional Setup for Windows UI Automation

* Install the WinAppDriver at https://github.com/microsoft/WinAppDriver/releases/tag/v1.2.1
  * The default installation location is "C:\Program Files (x86)\Windows Application Driver"
* After installation, run `WinAppDriver.exe` in cmd as the automation server