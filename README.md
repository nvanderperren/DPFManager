DPFManager
==========

DPF Manager: Digital Preservation Formats Manager (Image files)

DPF Manager is an open source modular TIFF conformance checker that is extremely easy to use, to integrate with existing and new projects, and to deploy in a multitude of different scenarios. It is designed to help archivists and digital content producers ensure that TIFF files are fit for long term preservation, and is able to automatically suggest improvements and correct preservation issues. The team developing it has decades of experience working with image formats and digital preservation, and has leveraged the support of 60+ memory institutions to draft a new ISO standard proposal (TIFF/A) specifically designed for long term preservation of still-images. An open source community will be created and grown through the project lifetime to ensure its continuous development and success. Additional commercial services will be offered to make DPF Manager self-sustainable and increase its adoption.

Licensing
---------
The DPF Manager is dual-licensed:

 - [GPLv3+](LICENSE.GPL "GNU General Public License, version 3")
 - [MPLv2+](LICENSE.MPL "Mozilla Public License, version 2.0")

CI Status
---------
- [![Build Status](https://travis-ci.org/EasyinnovaSL/DPFManager.svg?branch=master)](https://travis-ci.org/EasyinnovaSL/DPFManager "DPFManager Travis-CI master branch build") Travis-CI: `master`

- [![Build Status](https://travis-ci.org/EasyinnovaSL/DPFManager.svg?branch=develop)](https://travis-ci.org/EasyinnovaSL/DPFManager "DPFManager Travis-CI develop build") Travis-CI: `develop`

Getting DPF Manager software
------------------------
###Pre-requisites
In order to use the GUI you'll need:

 * Java 8, which can be downloaded [from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

###DPF Manager GUI
####Download release version
You can download an installer for the latest DPF Manager GUI release [from our download site](http://dpfmanager.org/#download). The current installation process requires Java 1.8 to be pre-installed.

####Download latest development version
If you want to try the latest development version you can obtain it from our [development download site](http://dpfmanager.org/community.html).

####DPF Manager GUI manual
A manual for the GUI can be found in [our download site](http://dpfmanager.org/Downloads/User%20Manual.pdf).

Building the DPF Manager from Source
----------------------------------------
###Pre-requisites
If you want to build the code from source you'll require:

 * Java 8, which can be downloaded [from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
 * [Maven v3+](https://maven.apache.org/)

####Downloading the latest release source
You can use [Git](https://git-scm.com/) to download the source code.
```
git clone https://github.com/EasyinnovaSL/DPFManager.git
```
or download the latest release from [GitHub] (https://github.com/EasyinnovaSL/Tiff-Library-4J/releases).

####Use Maven to compile the source
Move to the downloaded project directory and call Maven install:

    cd DPFManager
    mvn clean install

####Run DPF Manager
You can run the DPF Manager in two modes, GUI and CLI. To start the software in GUI mode just double-click the executable. For using the CLI use the terminal, the following command explains the available parameters.

    dpfmanager -help

