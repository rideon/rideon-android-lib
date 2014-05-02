This project can't be built unelss com.tapum.rideon.broker and com.tapum.rideon.wheels packages are checked in.

- local.properties and ant.properties need to be created manually on local system.
- The above 2 properties files need to be created in ../properties folder. If someone decides to choose another folder for properties file, build.xml needs to be updated accordingly.
- This project depends on android-sdk-macosx/extras/android/support/v7/appcompat and android-sdk-macosx/extras/google/google_play_services/libproject/google-play-services_lib library projects.

local.properties
```sh
sdk.dir=PATH_TO_android-sdk-macosx
#sdk.dir=/opt/android-sdk-macosx
```

ant.properties
```sh
source.dir=src
# points to the android source code
gen.dir=gen
# points to the android gen directory
resource.dir=res
# points to the res directory
asset.dir=assets
# points to the assets directory
external.libs.dir=lib
# points to the lib directory
native.libs.dir=lib
# points to the native libs
# points to a place where your classes are compiled and the Android packages are placed
key.store=PATH_TO_KEYSTORE #Edit this value to provide proper keystore path
key.alias=EDIT_THIS_VALUE
key.store.password=EDIT_THIS_VALUE
key.alias.password=EDIT_THIS_VALUE
```


project.properties in the project needs be added, here is the template.
```sh
target=android-19
android.library=true
android.library.reference.1=PATH_TO_android-sdk-macosx/extras/android/support/v7/appcompat
android.library.reference.2=PATH_TO_android-sdk-macosx/extras/google/google_play_services/libproject/google-play-services_lib
```
