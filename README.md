# digiwallet
Digiwallet is a small prototype application for digital wallet use
# How to?
1. Clone the repository
2. Open repository folder
3. Do not forget to clean/rebuild or install required SDKs if not already available in your IDE
# Used Libraries and Why
## Hilt
Using hilt will simplify the implementation of the DI (https://dagger.dev/hilt)
## Retrofit2
Used by the app for REST consumption. This is chosen because it is very compatible on MVVM architecture applications.
## Room Database
Used for persistence to be able to show the cached list for both wallets and history when server returns an error. Room Database was used also because of its compatibility to MVVM architecture.
## Coil
Coil is used as opposed to Glide or Picasso because it works nicely with Hilt and loads on the UI thread.
# Build Path
## data
This directory houses both local and remote directories, the models, and the repositories. Local directory hold the DAOs, the database, and the enum. Basically, all classes pertaining to local persistence for the application. Remote Directory, on the other hand, holds the service directory and responses. The service directory are the service classes used by Retrofit to access API endpoints. The Repository Directory holds the repository classes which is being used by the viewmodels to access both local and remote classes.
## di
This holds the Dependency Injection modules. For this app, it is merely separated into three. ApplicationModule which includes all dependencies used app-wide, LocalModule which will be used for Local Persistence access, and RemoteModule which includes dependencies for Remote access. 
## ui
This holds the View and ViewModel classes including adapters and viewholders.
