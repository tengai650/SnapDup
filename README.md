# SnapDup
A SnapRAID duplicate file utility to help remove duplicate files identified by SnapRAID.

SnapDup is designed to read the log file generated by command: 'snapraid dup -l dup.txt'
And once the dup log file is read the user can select between the two duplicate duplicate files for deletion. 
SnapDup will prevent both duplicate files from being checked. After all the files are checked for deletion, click 
"Generate Script" to create either Windows or Linix command script. This script can be copied to a text editor or saved
as a shell script for execution.

Commands:
    ctrl-a,  To select all

SnapDup requires the lastest release of java 1.8 (or OpenJDK) with javaFX which is included with a java Windows install.
Linux; however, may not include javaFX and will need to be installed manaually. If SnapDup doesn't run Linux and you get an 
error: can't find or load  main.SnapDup - then javaFX is not installed.


Verify java 1.8 installation:
-----------------------------

    java -version
 
    openjdk version "1.8.0_144"
    OpenJDK Runtime Environment (IcedTea 3.5.1) (suse-10.13.3-x86_64)
    OpenJDK 64-Bit Server VM (build 25.144-b01, mixed mode)

Windows - how to run 
--------------------
Create a .cmd file with the following commands:

    @setlocal
    @echo off

    set JAVA_HOME=D:\Java\jdk1.8.0_144\jre
    set PATH=%JAVA_HOME%\bin;%PATH%

    java -jar snapdup.jar
    endlocal

* Update JAVA_HOME to match your enviroment.
    
Linux - how to run
------------------
    java -jar snapdup.jar

    If your Linux installation of OpenJDK does not include javaFX you can download from: 
    https://chriswhocodes.com/downloads/openjfx-8-sdk-overlay-linux-amd64.zip 

    To install 'openjfx-8-sdk-overlay-linux-amd64.zip' use script (tested on OpenSUSE):
    
        !/bin/ksh
        JDK_HOME=/usr/lib64/jvm/java-1.8.0-openjdk-1.8.0
        sudo 7z x -o$JDK_HOME openjfx-8-sdk-overlay-linux-amd64.zip jre

    * Update JDK_HOME to match your enviroment.
    * This script requires 7zip to be installed.
    
    SnapDup was built with Eclipse.
