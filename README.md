[![](https://jitpack.io/v/WhiteDog-Apps/SplashScreen.svg)](https://jitpack.io/#WhiteDog-Apps/SplashScreen)

# SplashScreen
Android library that implements the logic of a splash screen.

It allows you to indicate the minimum duration of the screen and makes it easy to perform a task in the background by ensuring that the application will not launch the next screen until the task and the timer have ended.

It also prevents the timer from being affected by screen rotation (which causes the Activity to restart).

## Setup
#### 1. Add the JitPack repository to your build file if you didn't add before.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.WhiteDog-Apps:SplashScreen:1.0.1'
}
```

## Usage
#### 1.  Make your activity inherit from SplashScreenActivity
```kotlin
class MainActivity: SplashScreenActivity() {
    ...
}
```

#### 2.  Implements members
```kotlin
    override fun doInBackgroundSplashTask() {
        // Do task
    }

    override fun onSplashTaskFinished() {
        // Start next activity
    }
```

#### 3.  Call startSplashTask(minDuration) in your onCreate()
```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startSplashTask(2500)
    }
```

## Example
```kotlin
    // 1. Make your activity inherit from SplashScreenActivity
class MainActivity: SplashScreenActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3. Call startSplashTask(minDuration) in your onCreate()
        startSplashTask(2500)
    }

    // 2. Implements members
    override fun doInBackgroundSplashTask() {
        // Do task
        Thread.sleep(3500)
    }

    override fun onSplashTaskFinished() {
        // Start next activity
        startActivity(Intent(this, SecondActivity::class.java))

        finish()
    }
}
```
