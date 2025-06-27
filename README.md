# Chess Game

This is a chess game application built using Jetpack Compose and Firebase. The purpose of this project is to provide a platform for users to play chess online with their friends or against the computer.

## Features

- Play chess online with friends
- Play against the computer
- Real-time updates using Firebase
- Invite players to join your game
- Accept invitations to join a game
- Track knocked-out pieces
- Promote pawns to other pieces
- Check and checkmate detection
- Stalemate detection

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/KKEK-041405/Chess.git
   cd chess-game
   ```

2. Open the project in Android Studio.

3. Add your `google-services.json` file to the `app` directory.

4. Build and run the project on an emulator or physical device.

## How to Contribute

We welcome contributions to improve the project. To contribute, follow these steps:

1. Fork the repository.

2. Create a new branch for your feature or bugfix:
   ```bash
   git checkout -b my-feature-branch
   ```

3. Make your changes and commit them:
   ```bash
   git commit -m "Add new feature"
   ```

4. Push your changes to your forked repository:
   ```bash
   git push origin my-feature-branch
   ```

5. Create a pull request to the main repository.

## How to Report Issues or Request Features

If you encounter any issues or have feature requests, please open an issue on the GitHub repository. Provide as much detail as possible to help us understand and address the issue or request.

## Firebase Setup Instructions

To set up Firebase for this project, follow these steps:

1. Go to the [Firebase Console](https://console.firebase.google.com/).

2. Click on "Add project" and follow the instructions to create a new Firebase project.

3. Once the project is created, click on "Add app" and select "Android".

4. Register your app with the package name `com.KKEK.chess`.

5. Download the `google-services.json` file and place it in the `app` directory of your project.

6. In the Firebase Console, go to the "Authentication" section and enable "Anonymous" sign-in method.

7. In the Firebase Console, go to the "Database" section and create a new Realtime Database. Set the database rules to allow read and write access for authenticated users.

8. Sync your project with Gradle files to ensure that the Firebase dependencies are added.

## Running the Project

To run the project, follow these steps:

1. Open the project in Android Studio.

2. Connect an Android device or start an emulator.

3. Click on the "Run" button in Android Studio to build and run the project.

4. The app should launch on the connected device or emulator, and you should be able to play chess online or against the computer.

## Contributing Guidelines

We welcome contributions to improve the project. To contribute, follow these steps:

1. Fork the repository.

2. Create a new branch for your feature or bugfix:
   ```bash
   git checkout -b my-feature-branch
   ```

3. Make your changes and commit them:
   ```bash
   git commit -m "Add new feature"
   ```

4. Push your changes to your forked repository:
   ```bash
   git push origin my-feature-branch
   ```

5. Create a pull request to the main repository.

6. Ensure that your code follows the project's coding style and conventions.

7. Write unit tests to cover your changes and ensure the robustness of the code.

8. Update the documentation if necessary.

9. Be responsive to feedback and make any requested changes promptly.

10. Once your pull request is approved, it will be merged into the main repository.

Thank you for contributing to the project!
