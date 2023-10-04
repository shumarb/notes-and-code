# Linear Search

## What is Linear Search
Linear Search, also known as `Sequential Search`, is a search algorithm that works by iterating through the `data stucture (array or list)` in `linear progression` and checking whether an element is equal to the element being searched.

## How Linaer Search Works
1. The element to search for is denoted as `key`.
2. Iterate through the `data stucture (array or list)`, and check one-by-one whether the element of the data structure is equal to the key.
3. If the `current element == key`, the key is found.
4. If the `current element != key`, proceed to the next element.

## Worst-case Time Complexity
**O(N)**.

This is because the worst-case scenario is to compare all elements of the list to the key.

## How to use this folder
1. Ensure that your local machine contains a `Java Development Kit (JDK) version 8 or later`.
2. Open up `terminal`.
3. Compile the program by entering `javac LinearSearch.java`.
4. Run the program by entering `java LinearSearch`.
5. The program creates an array randomly. The elements are of type `integer`. The number of elements range from `5 to 12`, and the value elements range from `-10000 to 10000`. The program displays the array before the sort, the relevant steps during the sort, the array after the sort, and the side-by-side comparison of the array before and after the sort.