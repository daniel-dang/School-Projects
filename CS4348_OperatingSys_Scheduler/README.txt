This file contained information on how to compile the project and run 
when using unix or cmd in window. 

The project folder should have a list of java file in my case there are 9 files:

Driver.java 		- This is the interface for the project
Scheduler.java
Job.java
FIFO.java
FeedBack.java
HighestResR.java
RoundRobin.java
ShortestProcN.java
ShortestRemT.java

There are also a folder call "Res" this folder is important since the input file will be put into this file. The project is already config to run with this path so if additional input file that you have PLEASE PUT THEM IN THIS "Res" FOLDER.

Compile all the java file:

1) type the following command follow by the each different java file name

You can copy and paste this into the command window:

javac Driver.java FeedBack.java FIFO.java HighestResR.java Job.java RoundRobin.java Scheduler.java ShortestProcN.java ShortestRemT.java

**Incase some command window cannot be paste the pattern should be like below:

javac <file1.java> <file1.java> ... <fileN.java>


2) Once you run the above command, 9 class file will be created. All you have to do now is to run the Driver class.

Copy the following command:

java Driver

3) Follow the instruction of the driver interface and run the program. 



**Note: The res folder already has the input file from the project that the professor gave out in elearning. Unless there are other additional input files, just copy and paste those input file to the res folder.