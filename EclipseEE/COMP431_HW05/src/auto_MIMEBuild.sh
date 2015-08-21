#! /bin/bash

clear

echo "==================================================================================="
echo "Hi, $USER!"
date
echo "The $0 starts now." # print out current file name
echo "==================================================================================="

echo
echo "================================="
echo "Compile and run MIMEBuild"
echo "================================="
javac MIMEBuild.java
java MIMEBuild outgoing img.gif < recipients

echo
echo "================================="
echo "Finished running script MIMEBuild.java"
echo "================================="


echo "==================================================================================="
echo "Test Completed."
date
echo "Retunring to the Command Line prompt..."
echo "==================================================================================="