
# Seagull - Android Application for Budgeting

Seagull is a budgeting android app that. It allows you to input cash inflows and outflows and find nearby banks using Google Maps integration.
Users can click on the bank markers to view more information about the banks, including their name, address, phone number, and website.

![](seagull-demo-main.mp4)

## Table of Contents

1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Setup](#setup)
4. [Usage](#usage)
5. [Contributing](#contributing)
6. [License](#license)


## Features

- **Budget Tracking**: Add expenses and earnings and then analyze by seeing your cash inflows/outflows.
- **Find Nearby Banks**: Search for nearby banks within a specified radius from the user's current location.
- **Interactive Map**: Displays the user's current location and nearby banks on a Google Map. 
- **Bank Details**: Click on markers to view detailed information about the bank, including address, contact information, and website. 
- **Permission Handling**: Requests and handles location permissions to provide a seamless user experience.


## Prerequisites
* Android Studio: Ensure you have Android Studio installed on your machine. 
* Google Maps API Key: Obtain an API key from the Google Cloud Console and enable the Maps SDK for Android and Places API. 
* Gradle: Make sure your project is set up with the latest Gradle version. 
* The app requires the following permissions:
```bash
ACCESS_FINE_LOCATION: To access the device's precise location for finding nearby banks.
```
    
## Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Ajit-Mehrotra/Seagull-Personal-Finance-Android-App.git
   cd Seagull-Personal-Finance-Android-App
    ```
2. **Open in Android Studio**
   * Open Android Studio.
   * Click on Open an existing project and select the cloned repository folder.
   
3. **Add Google Maps API Key**
   * In your Android project, navigate to res/values/strings.xml.
   * Add your Google Maps API key:
```xml
<string name="google_maps_key">YOUR_GOOGLE_MAPS_API_KEY</string>
```
  * Replace `YOUR_GOOGLE_MAPS_API_KEY` with your actual API key.
5. **Sync Project with Gradle Files**
   * Click on `File > Sync Project with Gradle Files` to ensure all dependencies are properly set up.

## Usage
1. Run the Application

    * Connect your Android device or start an emulator.
    * Click the Run button in Android Studio to install and launch the app on your device.

2. Grant Location Permission

    * The app will prompt you to grant location permission. Allow the app to access your device's location to find nearby banks.

3. Enter Cash Inflows & Outflows
   * Switch to the `Submission Form` tab and enter relevant information

4. View Expenses & Earnings
   * Switch back to the `Expenses/Earnings` tab to analyze your cash movement 

5. Find Banks

    * Switch to the `ATM Maps` tab
    * Click on the `Bank` button to search for nearby banks.
    * Banks will be displayed as markers on the map.

6. View Bank Details

    * Tap on a bank marker to view its details, including name, address, phone number, and website.


## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.
