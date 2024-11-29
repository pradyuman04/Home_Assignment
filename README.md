# 🏠 Home Assignment
An Android App With Call Detection & Microphone Access Monitor Functionality


## 🔑 Key Functionality's:
- Get Incoming Call Alert/PopUp.
- Get List App with Microphone Access.
- Filtered App Using Fragments Like All Apps, Installed Apps, and Apps With Background Microphone Access.

## 📲 AddOn Functionalities:
  - You can also see the **Number of Incoming Calls**.

##  🎮 Application Testes On:

  - Android 10
  - Android 11
  - Android 14

### Details:

#### 📱 Feature 1: (Call Detection with Caller Identity Popup)
1. For detecting **Incoming Call**, I used Android inbuild functionality **BroadcastReceiver**.
2. With the help of BroadcastReceiver I can details about Incoming calls.
3. After getting details about the incoming calls, I used the **CallOverlayService** Service to display caller information.

#### 📱 Feature 2: (Microphone Access Monitor)
1. I used the **BottomNavigation Bar** with **Recycler View** for filter apps.
2. Created common **AppsAdapter** for displaying different app lists.
3. Used **Fragments** for filtering apps.

### 🛠 Tools, Language, and Libraries:
1. Android Studio
2. Kotlin
3. SDP & SSP Libraries
4. Animated Bottombar Library


### 🚧Permission Required(Asked):
1. Contact
2. Manage Calls
3. Foreground Services
4. Read Phone State


### Download The APK File For Testing On Your Device:
https://drive.google.com/file/d/1kNc2nCmYZwxXNt6Zt5WOBbbi4_J9ZsYa/view?usp=sharing
