

## CareBridge - A mobile app intends to bridge the gaps in medical care

CareBridge is an Android application developed by Sarah Zhou. It is designed to improve access to medical care for underserved populations. This includes individuals in areas with limited healthcare facilities, those who cannot afford health insurance, and people with visual impairments who may struggle with traditional map-based searches.

![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.26.25%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.27.59%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.28.17%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.28.33%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.29.04%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.29.17%E2%80%AFPM.png)
![alt-text](https://github.com/sarahf-zh/my-android/blob/main/CareBridge/screen_snapshots/Screenshot%202024-10-16%20at%207.29.38%E2%80%AFPM.png)

Features
CareBridge offers three core functionalities through its main tabs:

1. Home:

Doctor Bot: Consult with an AI-powered "Doctor Bot" for medical diagnosis and advice. This feature is powered by Med-Gemini, a group of advanced language models trained on a vast dataset of medical information.
Medical Advice Cards: Access easily digestible information and advice on common health conditions such as fever, influenza, diabetes, and tuberculosis.
2. Online Pharmacy:

Access to Affordable Medication: Purchase medication without insurance through an embedded webpage for the online pharmacy, www.alldaychemist.com.
3. Nearby Hospitals:

Accessible Map View: Locate nearby medical care providers within a 3000-meter radius using an embedded map with interactive markers.
Detailed Provider Information: View comprehensive information about each provider by clicking on the corresponding marker.
Dynamic Updates: As the user navigates the map, nearby medical providers are automatically updated without requiring manual searches. This is particularly helpful for users with visual impairments.
Technology Stack
CareBridge leverages a variety of technologies to deliver its features:

Android SDK: Used for core application development, including UI design with Material Design components, map views, web views, and view binding.
Third-Party Libraries: Utilizes libraries like Volley, Retrofit, Dagger, and Picasso for networking, dependency injection, and image loading.
Google Cloud Dialogflow API: Connects to a large language model (LLM) agent to power the "Doctor Bot" chat functionality.
Google Maps Places API: Enables the app to search for and display nearby medical care providers based on the user's location.

