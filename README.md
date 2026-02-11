# Puzzle

This is a Scala application for working with number fragments.

## Description

The application reads a text file containing numbers and finds the maximum length of a sequence based on fragments.  
Each fragment is represented by the first two and last two digits, and the sequence is built using a depth-first search (DFS) algorithm.

## Prerequisites

- **Scala** (tested with Scala 3)
- **SBT** (Scala Build Tool)

## How to Run

1. Open a terminal and navigate to the project folder:
2. Make sure you have your input file, for example numbers.txt, in the project folder. The file should contain numeric strings, one per line.

Run the application using SBT:



```bash
cd path_to_project

sbt run numbers.txt 
run numbers.txt 