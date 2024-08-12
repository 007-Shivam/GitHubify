# GitHubify

## Overview

GitHubify is an Android application built using Jetpack Compose and Kotlin. The app allows users to search for GitHub profiles and view detailed information about users and their repositories. It features a clean, modern UI and leverages the power of Retrofit, Coroutines, and other modern Android development tools.

## Table of Content
- [Features](#features)
- [Technologies Used](#technologiesused)
- [Installation](#installation)
- [Usage](#usage)
- [Future Features](#futurefeatures)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Search**: Search for GitHub users by username.
- **User Details**: View detailed information about a GitHub user including their avatar, name, location, bio, repositories, followers, and following.
- **Repository List**: Display a list of repositories for a user, including details like description, languages used, stars, and forks.
- **More features to be added soon**

## Technologies Used

- **Jetpack Compose**: Modern toolkit for building native Android UI.
- **Kotlin**: Official language for Android development.
- **Retrofit**: Type-safe HTTP client for Android.
- **Coroutines**: Concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- **Flow**: A reactive stream library to handle data streams asynchronously.
- **ViewModel**: Designed to store and manage UI-related data in a lifecycle-conscious way.
- **Material Design 3**: UI components and design system.

## Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/007-Shivam/Githubify.git
   cd Githubify
    ```

2. **Open the project in Android Studio**:
- Open Android Studio.
- Select "Open an existing Android Studio project".
- Choose the cloned directory.

3. **Setup GitHub API Token**:
- Create a file named local.properties in the root directory of the project.
- Add your GitHub API token: (without quotes [""])
    ```sh
    PERSONAL_ACCESS_TOKEN=your_github_personal_access_token
    ```

How to generate your PERSONAL ACCESS TOKEN: [click here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token)

## Usage
1. **Search for a User**    :
- Enter a GitHub username in the search bar and press the search button.
- The app fetches and displays the user's profile information.

2. **View User Details**:
- Tap on a user from the search results to view detailed information about their profile and repositories.

3. **Explore Repositories**:
- View a list of repositories for the selected user.
- Tap on a repository to see more details, including the languages used, stars, and forks

## Future Features
- **Sign in with Google Auth**: Implement authentication using Google Sign-In via Firebase.
- **Storing Starred Repositories**: Save users' starred repositories in Firestore.
- **User Search History**: Store and display search history in Firestore, with an option to clear the history.
- **Caching Search Results**: Cache search results locally until the app is relaunched, improving the speed of subsequent searches.
- **User Following & Followers**: View the list of users followed by or following the searched user.
- **Repository Sorting and Filtering**: Sort and filter repositories by various criteria.
- **Dark Mode**: Support for dark theme.
- **Responsive Layout**: Ensure the app layout adapts smoothly across different screen sizes and orientations.
- **More features will be added soon.**

## Contributing
Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.

2. Create a new branch for your feature or bugfix.
    ```sh
    git checkout -b <branch_name>
    ```

3. Add & Commit your changes.
    ```sh
    git commit -m 'Add some feature'
    ```

4. Push to the branch.
    ```sh
    git push origin <branch_name>
    ```

5. Create a Pull Request.

## License
This project is licensed under the [MIT License](https://github.com/007-Shivam/Githubify/blob/main/LICENSE).

---

Stay tuned for more updates!