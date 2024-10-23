

## CareBridge - An android app bridging the gaps in healthcare accessibility

CareBridge is an Android application developed by Sarah Zhou, who studies at the Castilleja school. The app is designed to empower underserved populations with the tools and information to access healthcare, regardless of their conditions. This includes individuals in areas with limited healthcare facilities, those who cannot afford high medication costs or health insurance, and people with visual impairments who struggle with text query-based map searches.


<table>
  <tr>
<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen1.png" width = "200" hight = "427" align="left"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen2.png" width = "200" hight = "427" align="center"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen3.png" width = "200" hight = "427" align="right"></td>
  </tr>

  <tr>
<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen4.png" width = "200" hight = "427" align="left"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen5.png" width = "200" hight = "427" align="center"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen6.png" width = "200" hight = "427" align="right"></td>
  </tr>

  </tr>
<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen7.png" width = "200" hight = "427" align="left"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen8.png" width = "200" hight = "427" align="center"></td>

<td><img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen9.png" align="right"></td>
 </tr>
</table>

## Features

CareBridge offers three core functionalities through its main tabs:

#### 1.Medical Help:

<ins>Doctor Bot</ins>: Consult with an AI-powered "Doctor Bot" for medical diagnosis and advice. This feature is powered by Gemini, a group of advanced language models trained on a vast dataset including medical information.

<ins>Medical Advice Cards</ins>: Access easily digestible information and advice on common health conditions such as fever, influenza, diabetes, and tuberculosis. Clicking the card open up the card detail screen, where speech to text functionality is available.

#### 2.Online Pharmacy:

<ins>Access to Affordable Medication</ins>: Purchase medication without insurance through an embedded webpage of three different online pharmacies via menu options. The three online pharmacies are: www.healthwarehouse.com, www.alldaychemist.com and pharmacy.amazon.com

#### 3.Nearby Care Providers:

<ins>Accessible Map View</ins>: As a user opens up the embedded map, it automatically displays healthcare providers near his/her current location within a 3000-meter radius via interactive markers, without needing the user to type a search query.

<ins>Detailed Provider Information</ins>: View details about each provider by clicking on the corresponding marker.

<ins>Dynamic Updates</ins>: As the user navigates the map and taps on any location, nearby healthcare providers show up without requiring manual searches. This is particularly helpful for users with visual impairments.


## Technology Stack

CareBridge leverages a variety of libraries and APIs to deliver its features:

<ins>Android SDK</ins>: Used for core application development, including UI design with Material Design components, map views, web views, speech to text, and view binding.

<ins>Third-Party Libraries</ins>: Utilizes libraries like Volley, Retrofit, Dagger, and Picasso for networking, dependency injection, and image loading.

<ins>Google Cloud Dialogflow ES API</ins>: Connects to a LLM (Google Gemini) driven agent to power the "Doctor Bot" chat functionality.

<ins>Google Maps Places API</ins>: Enables the app to search for and display nearby health care providers based on the user's location.


## Data Flow Diagram
1) Chat with Doctor Bot
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow1.png" width = "500" hight = "180" align="center">

2) Medical Advice Cards
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow2.png" width = "500" hight = "180" align="center">

3) Online Pharmacy 
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow3.png" width = "500" hight = "180" align="center">

4) Nearby Care Providers
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Flow4.png" width = "500" hight = "180" align="center">

## Disclaimer
After you check out the code, the app will compile and run, assuming you have set up the Android development environment. However, the "Chat with Doctor Bot" and "Nearby Care Providers" features will not work until you set up your own Google cloud projects and API key. The reason is that Google Cloud projects and API keys are developer private data that can not be shared with others, due to security risks and potential abuse, as explained [here](https://support.google.com/googleapi/answer/6310037?hl=en#:~:text=When%20you%20use%20API%20keys,unexpected%20charges%20on%20your%20account).

Once you correctly configure the Dialog ES API credentials and the Places API key as explained below, those two features will work as expected:
1) <ins>Chat with Doctor Bot</ins>: you need to set up your Dialog ES agent, its associated Google Cloud Project and generate Dialog ES API key as a json file, aka credentials.json. Please use the generated json file to replace the dummy version in CareBridge/app/src/main/res/raw/credentials.json

2) <ins>Nearby Care Providers</ins>: you need to set up your Google Maps Platform Places API project and copy the API key into CareBridge/app/src/main/java/com/example/carebridge/utils/Constants.kt


