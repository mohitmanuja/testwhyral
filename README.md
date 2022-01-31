
# Whyral Android Library

## How to use

**Step 1.**  Add the dependency
Add it in your root build.gradle at the end of repositories:
```xml
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2.**  Add the dependency
```xml
dependencies {
    implementation 'com.github.mohitmanuja:testwhyral:1.0.4'
}
```

**Step 3.**  Start the Whral Sdk by following method if you want to open Activity
```xml
RewardUtils.startRewardFlow(context:Context, authToken:String, userId:String,isDev:Boolean)
```
OR if you want to open through fragment

```xml

RewardFragment.newInstance(authToken:String, userId:String,isDev:Boolean)

```

Set **isDev->true** to point staging server by default it's false. 


**That's it...**
