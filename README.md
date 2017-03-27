# xcalendar  
swt calendar widget  

# 1. Install  
# 1.1. Requirements  
jdk 1.8+  
maven-3.2.3+  
swt 4.2.1+  

# 1.2. Install from source code  

``` 
    #windows
    $mvn clean install -Pwin_x86_64 -Dmaven.test.skip=true
    
    #mac
    $mvn clean install -Pmac_x86_64 -Dmaven.test.skip=true
```  

# 2. Simple usage  
# 2.1 DateTime  

```java  
XCalendar x = new XCalendar(text.getParent(), SWT.TIME);
x.setup(v -> true, true);
x.show(text.getParent(), text.getBounds());
```

(date)[./doc/date.png]  