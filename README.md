## Repo for Sarah Zhou's android apps

### CareBridge
CareBridge is an Android app designed to address the HealthCare accessibility gap, by providing underrepresented community with the tools and info to access basic healthcare. To see a 3.5-minute video demo of the CareBridge app, please see [here](https://youtu.be/SSBY2XK9Ivg). 
The source code for CareBridge is located [here](https://github.com/sarahf-zh/my-android/tree/main/CareBridge). The app features:
1) A doctor-like ChatBot powered by AI offering preliminary diagnosis.
2) A set of medical advice cards with speech-to-text capability.
3) An embedded web browser showing three online pharmacies.
4) A customized map that is easily accessible for visually impaired users to find nearby healthcare providers.

#### Why CareBridge?
At least half of the world's population lacks access to quality healthcare, and approximately one hundred million people cannot afford high healthcare costs. Furthermore, at least two billion people worldwide experience visual impairments, making everyday tasks like navigating online maps a significant challenge. CareBridge's creator, Sarah Zhou, believes that access to basic health care is a human right, regardless of someone's specific conditions.

#### Technologies Behind CareBridge
CareBridge leverages a variety of libraries and APIs to deliver its features:
<ins>Android SDK</ins>: Used for core application development, including UI design with Material Design components, map views, web views, speech to text, and view binding.

<ins>Third-Party Libraries</ins>: Utilizes libraries like Volley, Retrofit, Dagger, and Picasso for networking, dependency injection, and image loading.

<ins>Google Cloud Dialogflow ES API</ins>: Connects to a LLM (ChatGPT or Google Gemini) driven agent to power the "Doctor Bot" chat functionality.

<ins>Google Maps Places API</ins>: Enables the app to search for and display nearby health care providers based on the user's location.


#### Demo & DataFlow Diagrams
Below str CareBridge's Data Flow Diagrams:

1) Chat with Doctor Bot
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow1.png" width = "500" hight = "180" align="center">

2) Medical Advice Cards
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow2.png" width = "500" hight = "180" align="center">

3) Online Pharmacy 
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow3.png" width = "500" hight = "180" align="center">

4) Nearby Care Providers
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow4.png" width = "500" hight = "180" align="center">