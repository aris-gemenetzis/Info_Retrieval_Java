Information Retrival Project in Java

Program: ir_fin.java

Input: app.config file

Output: 10 .txt files named "IR_iteration_i" where i = no. of each iteration
- each .txt file contains the randomly generated x% density matrix & MAE values for all six similarities
- output files are stored on the same path as the runnable .jar file (but that can be modified by editing the static OUTPUT_FILEPATH variable)
- the app.config file is required to successfully run the program. place it on the same folder as the runnable .jar file
- instead of having separate functions for user-to-user & item-to-item similarity operations, the original matrix is transposed & used as input
- all error values are rounded off at two (2) decimal points
- graphs of all experiment results are included in a .docx file, allowing for detailed data value inspection
- the source code includes multiple print commands used for debugging purposes in the comments



Program functions:

• int[][] generateRandomMatrix(int marks[][], int dense)
    
    Input: empty integer matrix, density percentage
    
    Output: x% dense matrix with values from 1-5 following a normal distribution with s.d.=1 & mean=(1+2+3+4+5)/5=3
    
    - any 'empty' places on the matrix are given the integer value '0' instead of null

• double[][] createJaccard(int marks[][])
    Input: integer matrix
    Output: Jaccard similarity matrix

• double[][] createCosine(int marks[][])
    Input: integer matrix
    Output: Cosine similarity matrix
    - missing values from integer matrix are treated as zeros

• double[][] createPearson(int marks[][])
    Input: integer matrix
    Output: Pearson similarity matrix
    - counter variables are used to determine the number of valid (non-missing) values available
    - mean averages for each variable are rounded off after repeated testing showed it improved result accuracy

• int[][] sortNeighbours(double similarity[][])
    Input: real number similarity matrix 
    Output: sorted integer matrix with the indexes of the closest neighbours for each user in descending similarity order
    - sorts similarity matrix for each row in descending order & stores the corresponding indexes

• int[][] fillZeros(int marks[][], double similarity[][], int places[][], int k)
    Input: original integer matrix with x% density, similarity matrix, 
        matrix of indexes of closest neighbours in descending similarity order, 
        number of k required closest neighbours
    Output: integer matrix with filled estimate values for that given similarity & k closest neighbours 
    - depending on the original matrix, if there aren't enough neighbour values to calculate each estimate, 
        some of the elements of the 'filled' matrix may remain empty (value equal to 0)

• double[][] generateEstimates(int filledmarks[][], double similarity[][], int places[][], int k)
    Input: original integer matrix with x% density, similarity matrix, 
        matrix of indexes of closest neighbours in descending similarity order, 
        number of k required closest neighbours
    Output: real number matrix with filled estimate values for that given similarity
    - uses the same algorithm as fillZeros, except not limited to only missing (zero) values

•  double[] calculateMAE(int filledmarks[][], double estimates[][])
    Input: filled integer matrix, real estimate matrix
    Output: real Mean Average Error matrix
    - computes Mean Average Error by comparing estimates to filled matrix values

•  void writeFile (String fileName, String toPrint)
    Input: filename for writing, single string containing all of the results
    - prints string to file using the PrintWriter class

• int[][] transposeMatrix(int marks[][])
    Input: original integer matrix
    Output: transposed integer matrix 
    - the transposed matrix is used for any object-to-object calculations without having to rewrite any of the previous functions

• double[] calculateErrors (int marks[][], double similarity[][], int k)
    Input: original integer matrix, given similarity matrix, number of k closest neighbours
    Output: MAE matrix 
    - calculateErrors calls all the functions required to calculate the MAE for each similarity
    - was intended to improve the overall program structure, 
        making it so the same lines of code wouldn't have to be repeatedly used in main()
