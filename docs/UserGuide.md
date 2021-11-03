---
layout: page
title: User Guide
---

<img src = "https://github.com/AY2122S1-CS2103T-T10-1/tp/blob/master/docs/images/logo.jpeg?raw=true" align = "left" width="100" height="100" style="margin-right: 1.25em">
<div>
  
  <h1> CohortConnect v1.4 </h1>
  
  <b> Type. Explore. Connect. </b>
  
</div>

<br><br>

CohortConnect is an advanced desktop address book which facilitates networking among Computer Science (CS) students. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). 

With advanced features for managing large groups of contacts, CohortConnect is intended for use in a university setting. At the start of the semester, professors will distribute `csv` or `json` files containing a list of students. Instantly load them into CohortConnect with a single **Import** command. With data collected from students before the semester, our **Find A Buddy** feature helps you find potential groupmates by leveraging GitHub’s metadata using a proprietary algorithm. In the **Events** tab, you can identify events and hackathons that your peers will be attending.

## Table of Contents
* Table of Contents
{:toc}

## Purpose

This User Guide is intended for basic to advanced users of CohortConnect. It provides sufficient information for users to set up the application and learn all its features.

## Prerequisites

1. Basic knowledge about `csv` and `json` type file formats.
2. Basic knowledge about Technical Terms like `CLI`, `UI` and `JAR`.
3. `Java 11` Installed on the System.

## Quick Start

1. Ensure you have Java 11 or above installed in your computer.
2. Download the latest `CohortConnect.jar`  release from [here](https://github.com/AY2122S1-CS2103T-T10-1/tp/releases).
3. Place `CohortConnect.jar` in an empty folder.
4. Double-click the jar file to start the program.
5. The UI would look like this:

<p align="center">
<img src="https://github.com/AY2122S1-CS2103T-T10-1/tp/blob/master/docs/images/WelcomeSplashScreenUi.png?raw=true">
</p>

> * Welcome Splash Screen

<p align="center">
<img src="https://github.com/AY2122S1-CS2103T-T10-1/tp/blob/master/docs/images/ProfileSetUpWindow.png?raw=true">
</p>

> * This Window is only displayed once, when the Student launches the App for the first time.
1. Enter Your Name in the `Name Field`.
2. Enter Your __VALID__ Telegram Handle in the `Telegram Field`.
3. Enter Your __VALID__ GitHub Username in the `GitHub Field`.

> :bulb: TIP: 
> 1. Telegram Handle Conventions: 
> * Handles can use a-z, 0-9 and underscores. 
> * Handles are case-insensitive,  
> * Handles must be at least five characters long, and maximum is 39 characters.
> 
> 2. GitHub Username Conventions:
> * Github username may only contain alphanumeric characters or hyphens.
> * Github username cannot have multiple consecutive hyphens. 
> * Github username cannot begin or end with a hyphen. 
> * Maximum is 39 characters.


<p align="center">
<img src="https://github.com/AY2122S1-CS2103T-T10-1/tp/blob/master/docs/images/MainWindow.png?raw=true">
</p>

> * Main Window.

## Features

Notes about command format:

- `[x/xxx]` refers to an optional field

<div markdown="span" class="alert alert-primary">
:bulb: TIP: In the Command Box, use <kbd>↑</kbd> and <kbd>↓</kbd> arrow keys to navigate the command history.
</div>

### Navigating Between Tabs - <kbd>⌘</kbd> + NumKey

* <kbd>⌘</kbd> + 1: Contacts Tab
* <kbd>⌘</kbd> + 2: Favorites Tab
* <kbd>⌘</kbd> + 3: Events Tab
* <kbd>⌘</kbd> + 4: Find A Buddy Tab

### Adding a new Student - `add`

Adds a Student to the Address Book.

Format:

```
add n/<NAME> te/<TELEGRAM> g/<GITHUB> [p/<PHONE_NUMBER>] [e/<EMAIL>] [a/<ADDRESS>] [t/<TAG>]
```

Example:

```
add n/John Doe te/johndoe g/john-doe p/98765432 e/johnd@eg.com a/John Street, Blk 12, #01-01
```

### Delete a Student - `delete`

Deletes the specified Student Contact from the Address Book.

Format:

```
delete <INDEX>
```

Example:

```
delete 7
```

### Edit a Student - `edit`

Edits an existing Student Contact in the Address Book.

Format: 

```
edit <INDEX> [n/<NAME>] [te/<TELEGRAM>] [g/<GITHUB>] [p/<PHONE>] [e/<EMAIL>] [a/<ADDRESS>] [t/<TAG>]
```

Example:

```
edit 1 n/John te/john_123 t/TA
```

### Edit your Profile - `edit profile`

Edits the Profile linked to the Address Book.

Format:

```
edit profile [n/<NAME>] [te/<TELEGRAM>] [g/<GITHUB>]
```

Example:
```
edit profile te/bob_osum
```

### Find a Student - `find`

Finds an existing Student Contact by name(s), tag(s), or telegram handle(s).

Format 1: 

```
find <NAME>
```

Example 1:

```
find John
```

Format 2: 

```
find t/<TAG>
```

Example 2:

```
find t/friends
```

Format 3: 

```
find te/<TELEGRAM>
```

Example 3:

```
find te/alex_1 bobx2
```

Format 4:

```
find g/<GITHUB>
```

Example 4:

```
find g/david_boss
```

### Show a Student Contact - `show`

Shows detailed information of a Student Contact. This can be done using the name 
of the contact or the index.

Format 1: 

```
show <NAME>
```

Example 1:

```
show John Doe
```

If the exact name is not given, the show function will act similar to find and then you can pick out one contact from the given list.

Format 2: 

```
show <INDEX>
```

Example 2:

```
show 4
```

### Importing Student Contacts - `import`

Imports Student Contacts from the specified JSON or CSV file. File must be in the same folder as the application.

Format 1:

```
import <FILENAME>.json
```

Example 1:

```
import CS2103T.json
```

Format 2:

```
import <FILENAME>.csv
```

Example 2:

```
import CS2103T.csv
```

### Exporting Student Contacts - `export`

Exports the Student Contacts to the specified JSON or CSV file. Exported file will be in the same folder as the application.

Format 1:

```
export <FILENAME>.json
```

Example 1:

```
export Friends.json
```

Format 2:

```
export <FILENAME>.csv
```

Example 2:

```
export Friends.csv
```

### Setting a Contact as Favourite - `fav`

Helps to set a particular Student Contact as a Favourite.

Format:

```
fav <INDEX>
```

Example:

```
fav 1
```

### Setting a Contact as Unfavourite - `unfav`

Helps to set a particular Student Contact as an Unfavourite.

Format:

```
unfav <INDEX>
```

Example:

```
unfav 1
```

### Displaying the Help Window - `help`

Displays the Help Window, which consists of a list
of possible Commands, and a link to this UserGuide.

Format:

```
help
```

Example:

```
help
```

<p align="center">
<img src="https://github.com/AY2122S1-CS2103T-T10-1/tp/blob/master/docs/images/HelpWindowUi.png?raw=true">
</p>

### Listing All Student Contacts - `list`

Lists all the Student Contacts present.

Format:

```
list
```

Example:

```
list
```

### Deleting All Student Contacts - `clear`

Deletes all the Student Contacts present.

Format:

```
clear
```

Example:

```
clear
```

> :exclamation: WARNING: Use With Caution!

### Open Telegram of Current User

Format:

```
te
```

### Open GitHub of Current User

Format:

```
g
```

### Retrieve Command History - <kbd>↑</kbd> / <kbd>↓</kbd>

Similar to a Command Line Interface, <kbd>↑</kbd> retrieves the previous command, while <kbd>↓</kbd> retrieves the next command.

### Exiting the App - `exit`

Exits the App.

Format:

```
exit
```

Example:

```
exit
```

## Keyboard Mappings

1. <kbd>⌘</kbd> + <kbd>1</kbd>: To Switch to Contacts Tab.
2. <kbd>⌘</kbd> + <kbd>2</kbd>: To Switch to Favorites Tab.
3. <kbd>⌘</kbd> + <kbd>3</kbd>: To Switch to Events Tab.
4. <kbd>⌘</kbd> + <kbd>4</kbd>: To Switch to Find a Buddy Tab.
5. <kbd>⌘</kbd> + <kbd>P</kbd>: To Launch the Profile Window.
6. <kbd>↑</kbd>: To Retrieve the Last Entered Command (Similar to Terminal).
7. <kbd>↓</kbd>: To Retrieve the Next Entered Command (Similar to Terminal).
8. <kbd>F1</kbd>: To Launch the Help Window.

<div markdown="span" class="alert alert-primary">
:bulb: TIP: <kbd>⌘</kbd> for Mac = <kbd>Ctrl</kbd> for Windows
</div>

## FAQ

Q: On typing and running commands, the error message "XXXX" is shown, what shall I do?

A: Please **strictly** follow the command format to avoid such issues.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Feature | Command(s) |
| ------ | ------ |
| Add a new Student | `add n/<NAME> te/<TELEGRAM> g/<GITHUB> [p/<PHONE>] [e/<EMAIL>] [a/<ADDRESS>] [t/<TAG>]` |
| Delete All Student Contacts Present | `clear` |
| Delete a Student | `delete <INDEX> ` |
| Exit the App | `exit` |
| Edit a Student | `edit <INDEX> [n/<NAME>] [te/<TELEGRAM>] [g/<GITHUB>] [p/<PHONE>] [e/<EMAIL>] [a/<ADDRESS>] [t/<TAG>]` |
| Edit your Profile | `edit profile [n/<NAME>] [te/<TELEGRAM>] [g/<GITHUB>]` |
| Exporting Student Contacts | `export <FILENAME>.json` <br> `export <FILENAME>.csv` |
| Favourite a Student Contact | `fav <INDEX>` |
| Find a Student (by Name) | `find <STRING> ` |
| Find a Student (by Tag) | `find t/<TAG>` |
| Find a Student (by Telegram Handle) | `find te/<TELEGRAM>` |
| Import Student Contacts | `import <FILENAME>.json` <br> `import <FILENAME>.csv` |
| List all the Student Contacts Present | `list` |
| Show the Help Window | `help` |
| Show a Student Contact (by Name)| `show <NAME>` |
| Show a Student Contact (by Index) | `show <INDEX>` |
| Unfavourite a Student Contact | `unfav <INDEX>` |
