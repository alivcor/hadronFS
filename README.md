# hadronFS

An In-Memory FileSystem in Java (from scratch) <sub><b>just for fun</b></sub>


## See It In Action

<img src="https://github.com/alivcor/hadronFS/raw/main/assets/hadronfs.gif" style="max-width: 100px" width="500px"/>

## Usage

```java
FileSystem fs = new FileSystem();
fs.mkdir("/home/");
fs.ls("/");
fs.mkdir("/home/darwin");
fs.mkdir("/home/newton");
fs.mkdir("/home/einstein");
fs.ls("/");
fs.ls("/home");
fs.createFile("/home/einstein/", "relativity.txt", 1L, FileExtension.txt, "e = m*c**2");
```

<img src="https://github.com/alivcor/hadronFS/raw/main/assets/hadronfs.png" style="max-width: 100px" width="200px"/>
