ionic build --release
PUBLISHED_FILE_PATH="published/android/Assignment3.apk"
APK_PATH="platforms/android/build/outputs/apk/android-release-unsigned.apk"
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore assignment3.keystore $APK_PATH assignment3
rm $PUBLISHED_FILE_PATH
zipalign -v 4 $APK_PATH $PUBLISHED_FILE_PATH
