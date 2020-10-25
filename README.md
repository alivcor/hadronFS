# hadronFS
An In-Memory FileSystem in Java

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
