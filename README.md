# FoodRecipesApp
Food Recipes app that users can view, fav recipes, and share jokes. I work on improving app architecture.
# Screenshots

<p align="center">
<img src="/previews/recipes_fragment.png" width="20%"/>
<img src="/previews/bottom_sheet.png" width="20%"/>
<img src="/previews/favorites_fragment.png" width="20%"/>
<img src="/previews/joke_fragment.png" width="20%"/>
</p>
<p align="center">
<img src="/previews/favorites_fragment_delete.png" width="20%"/>
<img src="/previews/overview_fragment.png" width="20%"/>
<img src="/previews/ingredients_fragment.png" width="20%"/>
<img src="/previews/instructions_fragment.png" width="20%"/>
</p>

# Tech Stack & Open Source Libraries
- Minimum SDK level 21
- %100 [Kotlin](https://kotlinlang.org/) based
- [Repository](https://developer.android.com/topic/architecture/data-layer) pattern is a design pattern that isolates the data layer from the rest of the app.
- [Coroutines](https://developer.android.com/kotlin/coroutines) for asynchronous programming on Android. 
- [Room](https://developer.android.com/training/data-storage/room) provides an abstract layer over the SQLite Database to save and perform the operations on persistent data locally.
- [Flow](https://developer.android.com/kotlin/flow) is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value In coroutines
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) is an observable data holder class.
- [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) is a class that holds the information about the lifecycle state of a component (like an activity or a fragment) and allows other objects to observe this state.
- [Navigation Component](https://developer.android.com/guide/navigation) refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within app
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Dagger Hilt](https://dagger.dev/hilt/) Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Jsoup](https://jsoup.org/) is a Java library for working with real-world HTML. It provides a very convenient API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM methods and CSS selectors.
- [Coil](https://coil-kt.github.io/coil/) An image loading library for Android backed by Kotlin Coroutines. 
- [Shimmer](https://facebook.github.io/shimmer-android/) Shimmer is an Android library that provides an easy way to add a shimmer effect to any view in your Android app.
- [MotionLayout](https://developer.android.com/develop/ui/views/animations/motionlayout) MotionLayout is a layout type that helps you manage motion and widget animation in your app.
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding) is a feature that allows you to more easily write code that interacts with views.
- [Extension Functions](https://kotlinlang.org/docs/extensions.html) Kotlin provides the ability to extend a class or an interface with new functionality without having to inherit from the class or use design patterns 
