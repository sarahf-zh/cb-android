

## CareBridge - An android app bridging the gaps in healthcare accessibility

CareBridge is an Android application developed by Sarah Zhou. It is designed to improve access to medical care for underserved populations. This includes individuals in areas with limited healthcare facilities, those who cannot afford health insurance, and people with visual impairments who may struggle with traditional map-based searches.

<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen1.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen2.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen3.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen4.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen5.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen6.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen7.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen8.png" width="300" height="640">
<img src="https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screen9.png" width="300" height="640">

## Features

CareBridge offers three core functionalities through its main tabs:

### 1.Home:

<ins>Doctor Bot</ins>: Consult with an AI-powered "Doctor Bot" for medical diagnosis and advice. This feature is powered by Med-Gemini, a group of advanced language models trained on a vast dataset of medical information.

<ins>Medical Advice Cards</ins>: Access easily digestible information and advice on common health conditions such as fever, influenza, diabetes, and tuberculosis. Clicking the card open up the card detail screen, where speech to text functionality is available.

### 2.Online Pharmacy:

<ins>Access to Affordable Medication</ins>: Purchase medication without insurance through an embedded webpage for three different online pharmacies via menu options. The three online pharmacies are: www.healthwarehouse.com, www.alldaychemist.com and pharmacy.amazon.com.

### 3.Nearby Hospitals:

<ins>Accessible Map View</ins>: Locate nearby medical care providers within a 3000-meter radius using an embedded map with interactive markers.

<ins>Detailed Provider Information</ins>: View comprehensive information about each provider by clicking on the corresponding marker.

<ins>Dynamic Updates</ins>: As the user navigates the map, nearby medical providers are automatically updated without requiring manual searches. This is particularly helpful for users with visual impairments.


## Technology Stack

CareBridge leverages a variety of technologies to deliver its features:

<ins>Android SDK</ins>: Used for core application development, including UI design with Material Design components, map views, web views, speech to text, and view binding.

<ins>Third-Party Libraries</ins>: Utilizes libraries like Volley, Retrofit, Dagger, and Picasso for networking, dependency injection, and image loading.

<ins>Google Cloud Dialogflow ES API</ins>: Connects to a large language model (LLM) agent to power the "Doctor Bot" chat functionality.

<ins>Google Maps Places API</ins>: Enables the app to search for and display nearby medical care providers based on the user's location.

