# Subsgredient

**Subsgredient** is a Java-based application for discovering and substituting ingredients quickly and efficiently. It uses a Red-Black Tree (with multi-key nodes) for optimized data lookup and insertion, powered by a CSV dataset of 2,000+ ingredients.

---

## Table of Contents

1. [Project Overview](#project-overview)  
2. [Features](#features)  
3. [Technologies & Tools](#technologies--tools)  
4. [Setup & Installation](#setup--installation)  
5. [Usage](#usage)  
6. [Testing](#testing)  
7. [Project Structure](#project-structure)  
8. [Contributors](#contributors)  
9. [License](#license)  

---

## Project Overview

This project allows users to:
- Insert and store large numbers of ingredients.
- Retrieve potential substitutions or related ingredient data.
- Enjoy fast search times due to an underlying Red-Black Tree implementation.

You’ll find additional logs and the `out/` directory included in this repository for reference and demonstration.

---

## Features

- **Ingredient Data Management**: Load ingredients from `ingredients.csv` and manage them in a Red-Black Tree.  
- **Multiple Duplicates Handling**: Stores duplicates within a single node (using a KeyList structure).  
- **Frontend & Backend Separation**: Clear distinction between the user interface (`FrontendDev`) and the core logic (`BackendImplementation`).  
- **Extensive Testing**: Includes JUnit-based test suites for both frontend and backend functionalities.

---

## Technologies & Tools

- **Java (11+)**  
- **JUnit 5** for testing  
- **Makefile** for builds and running tests  
- **CSV** for ingredient datasets  
- **Git** for version control  

---

## Setup & Installation

1. **Clone the repository**:

~~~~bash
git clone https://github.com/SrujayReddy/Subsgredient-App.git
cd Subsgredient-App
~~~~

2. **Ensure Java 11+** is installed.  
   If you plan to run tests locally, you may need the `junit5.jar` in your parent folder or configured in your IDE.

3. **Review the files** to confirm presence of `.java` files, `Makefile`, `ingredients.csv`, etc.

---

## Usage

You can compile and run using the **Makefile** or standard `javac`/`java` commands.

- **Compile & run the backend**:

~~~~bash
make run
~~~~

This compiles `BackendImplementation.java` and runs it.

Alternatively, compile everything manually:

~~~~bash
javac *.java
java BackendImplementation
~~~~

*(Adjust class name if you want to run the frontend instead.)*

---

## Testing

We have two main sets of tests: **`BackendDeveloperTests`** and **`FrontendTests`**.

- **Backend Tests**:

~~~~bash
make runBDTests
~~~~

Compiles and runs all JUnit 5 tests for the backend.

- **Frontend Tests**:

~~~~bash
make runFDTests
~~~~

Compiles and runs all JUnit 5 tests for the frontend.

---

## Project Structure

```
.
├── BackendDeveloperTests.java
├── BackendImplementation.java
├── BackendInterface.java
├── BinarySearchTree.java
├── FrontendDev.java
├── FrontendInterface.java
├── FrontendTests.java
├── Ingredient.java
├── IngredientInterface.java
├── IngredientPlaceholder.java
├── ingredients.csv
├── IterableMultiKeyRBT.java
├── KeyList.java
├── KeyListInterface.java
├── log
├── Makefile
├── out/
├── RedBlackTree.java
├── SortedCollectionInterface.java
└── TextUITester.java
```

- **`out/`** and **`log`** are included for demonstration logs, class outputs, etc.

---

## Contributors

- **Srujay Reddy Jakkidi** *(Backend)*
- **Katie** *(Frontend)*

---

## License

This project was developed as part of the **CS 537: Introduction to Operating Systems** course at the University of Wisconsin–Madison. It is shared strictly for educational and learning purposes only.

**Important Notes:**
- Redistribution or reuse of this code for academic submissions is prohibited and may violate academic integrity policies.
- The project is licensed under the [MIT License](https://opensource.org/licenses/MIT). Any usage outside academic purposes must include proper attribution.
