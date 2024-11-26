# To run Matrix Calculator:

---

## MacOS:
Download javafx from https://gluonhq.com/products/javafx/  
run mvn clean compile  
run mvn javafx:run  

---

## Windows:
Download the Windows SDK for javafx from https://gluonhq.com/products/javafx/  
Extract to a known location  
In IntelliJ, Go to "Run" -> "Edit Configurations" and press ALT+V on the popup menu  
In the resulting text box enter the following:  

> --module-path "PATH_TO_JAVAFX_LIB" --add-modules=javafx.controls  

Make sure to replace PATH_TO_JAVAFX_LIB with the file path of the javaFx lib file, which is contained in the file that was extracted earlier. The file path should end in "\javafx-sdk-23.0.1\lib"  
This setting might not save automatically if you restart IntelliJ. In order to ensure it persists, check the box labeled "Store as a project file" located on the top right of the window.