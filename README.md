# android-repo-09

### What's In My Github - Kotlin

|     Sample     | Description |
| ------------- | ------------- |
| [main](https://github.com/woowa-techcamp-2022/android-repo-09) | This branch |
| [service-locator](https://github.com/woowa-techcamp-2022/android-repo-09/tree/service-locator) | Uses Service Locator pattern |

### How to build
Add these lines in your local.properties and run app
> github_client_id = "bfdaf2a8000c0e9f1575" <br>
> github_secrets = "663d36c26c96bac88e3e80453eab4c5da4476e39" <br>
> github_oauth_base_url = "https://github.com/" <br>
> github_api_base_url = "https://api.github.com/" <br>

### Tech Stack

- Minumum SDK 26
- Kotlin
- Coroutines
- **Dagger-Hilt (Only in main branch)**
- AndroidX Jetpack
    - LiveData
- Retrofit2 & OkHttp3 + Gson
- Coil
- Material Components
- Timber

### Package

```
📂app
 ┣ 📂application
 ┣ 📂data
 ┃ ┣ 📂interceptor
 ┃ ┣ 📂model
 ┃ ┣ 📂service
 ┃ ┗ 📂repository
 ┣ 📂di (in main branch)
 ┣ 📂domain
 ┃ ┣ 📂enitity
 ┃ ┗ 📂repository
 ┣ 📂presentaion
 ┃ ┣ 📂ui
 ┃ ┗ 📂viewmodel
 ┗ 📂util
```
