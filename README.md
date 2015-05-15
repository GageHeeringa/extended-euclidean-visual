# extended-euclidean-visual

  This is a visualization program for 
  (1) finding the greatest common divisor of two numbers using the Extended Euclidean Algorithm and
  (2) finding the modular inverse of a number
  
  The file ExtendedEuclideanVisual.jar is compatible to be run as an executable, just double click it and it should work with JRE 1.8.0_11 and later.

  Alternatively, you may run the JAR file in your terminal like any other JAR file:
  java -jar ExtendedEuclideanVisual.jar

--------------------
  EXAMPLE of computing the greatest common divisor of two numbers
  using the Extended Euclidean Algorithm:
 
  Consider gcd(27,15).
 
  27 = 15(1) + 12        
  15 = 12(1) + 3 
  12 =  3(4) + 0 -> gcd = 3
  
--------------------
  EXAMPLE of using the Extended Euclidean Algorithm to compute the modular inverse:
  
  Consider the inverse of 5(mod 27).
  
  gcd(5,27) = 1 if and only if an inverse exists
  
  top to bottom    | bottom to top
 
  A     B            X      Y   	(1 = AX + BY)
  27 =  5(5) + 2    -2     11     1 = 27(-2) + 5(11) -> 5^-1 (mod 27) = 11
   5 =  2(2) + 1     1     -2     1 = 5(1) + 2(-2)
   2 =  1(2) + 0     0      1	  1 = 2(0) + 1(1)	
     -> gcd = 1
