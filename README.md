# CSC242-Project2

Collaborators: Shoham Shitrit and Viktoriia Shevchenko
NETIDS: sshitrit, vshevche

# Part 1
Our representation of clauses for this project is as an ArrayList<ArrayList<Integer>>.
Every arraylist is a set of integers that we are disjuncting(or) and then every arraylist as a whole is a clause we are conjuncting(and).
To only see the DIMACS cnf reader portion of the project:
Go to DimacsReader.java
Exectue the following commands to compile & run:
javac DimacsReader.java
java DimacsReader

Then, enter the file path as a string, and proceed to see the file proccessed and printed out as a set of clauses. 
We also left commented out versions of how the filepaths should be structured given our project directory. 

# Part 2
TO run: 
javac ModelChecker.java
java ModelChecker

The program will run the modelchecking on all 3 problems, and print out the result along with the knowledge base and some other tidbits about what is going on. In general, an output of true means that the KB entails the query, and an output of false means it does not entail.

# Part 3 

To run:
javac Satisfier.java
java Satisfier

You will get a REPL, with options on which file you want to run GSAT on, and what values you want for MAX_TRIES and MAX_FLIPS.
Further, you can choose whether you want tracing or not. Tracing may yield longer compute times, but its only really discernable for files such as nqueens_12 and aim-50-1_6-yes1-4.
nqueens_16 does not always work...you have to get pretty lucky with the random model that GSAT generates. It works sometimes though!
