# KalisaIneza Java Project

**Student:** Kalisa Ineza Jovith

**Student ID:** 26259

## Overview
This repository contains Java solutions for assignment questions grouped under the `_26259` package root. Source files follow the Java package directory layout under `src/_26259`.

## Project layout

```
_26259/
	README.md
	q1/
		Stock_Management.java
	q2/
		Flight_Booking.java
	q3/
		Tax_Administration.java
	q4/
		Procurement_Management.java
	q5/
		Attendance_Management.java
	q6/
		Payroll_Management.java
```

Note: Each `.java` file should declare a package that matches its path (for example `package _26259.q1;` for files in `src/_26259/q1`).

## Build (compile)

Recommended: compile into an `out` directory so class files preserve package paths.

```powershell
javac -d out src/_26259/q1/*.java
javac -d out src/_26259/q2/*.java
javac -d out src/_26259/q3/*.java
javac -d out src/_26259/q4/*.java
javac -d out src/_26259/q5/*.java
javac -d out src/_26259/q6/*.java
```

Or compile everything at once:

```powershell
javac -d out src/_26259/**\*.java
```

## Run

Run compiled classes from the `out` directory using the full package name.

```powershell
java -cp out _26259.q1.Stock_Management
java -cp out _26259.q2.Flight_Booking
java -cp out _26259.q3.Tax_Administration
java -cp out _26259.q4.Procurement_Management
java -cp out _26259.q5.Attendance_Management
java -cp out _26259.q6.Payroll_Management
```

## Common issues & fixes

- Invalid package name errors often come from package identifiers that start with a digit (e.g., `package 26259.q1;`). Java package names must start with a letter or underscore — rename to `_26259` or `p26259` and update directories accordingly.
- Ensure the `package` declaration at the top of each `.java` file matches the directory structure under `src`.
- If you see `NoClassDefFoundError` or `ClassNotFoundException`, confirm that you used `-d out` when compiling and that `-cp out` is provided when running.

