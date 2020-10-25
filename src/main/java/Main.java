// "static void main" must be defined in a public class.


abstract class HadronMemoryNode {
    String name;
    HadronMemoryNode parent;
    Long size;
    Map<String, HadronMemoryNode> children;
    abstract boolean isDirectory();  
    HadronMemoryNode(String name, HadronMemoryNode parent, Long size){
        this.name = name;
        this.parent = parent;
        this.size = size;
    }
}

enum FileExtension {
    txt,
    jpeg,
    png,
    mp4
}

class File extends HadronMemoryNode {
    FileExtension ext;
    String content;
    File(String name, HadronMemoryNode parent, Long size, FileExtension ext, String content){
        super(name, parent, size);
        this.ext = ext;
        this.content = content;
    }
    @Override
    boolean isDirectory(){
        return false;
    }
}

class Directory extends HadronMemoryNode {
    Directory(String name, HadronMemoryNode parent){
        super(name, parent, 0L);
        this.children = new HashMap();
    }

    void addChild(HadronMemoryNode node) throws IllegalArgumentException {
        System.out.println("Creating node " + node.name + " inside " + this.name);
        if(!this.children.containsKey(node.name)){
            this.children.put(node.name, node);
            this.size += node.size;
            return;
        }
        throw new IllegalArgumentException(node.name + " already exists!");
    }

    @Override
    boolean isDirectory(){
        return true;
    }
}

class FilterParams {
    List<FileExtension> extensions;
    Long size;
} 

interface FilterCondition {
    boolean apply(FilterParams params, HadronMemoryNode node);
}

class ImageFilter<T extends File> implements FilterCondition {
    public boolean apply(FilterParams params, HadronMemoryNode node){
        if(!node.isDirectory()){
            for(FileExtension ext : params.extensions)
            if(((File) node).ext == ext || ((File) node).ext == ext){
                return true;
            }
        }
        return false;
    }
}


class MinSizeFilter<T extends HadronMemoryNode> implements FilterCondition {
    public boolean apply(FilterParams params, HadronMemoryNode node){
        return params.size == null || params.size >= node.size;
    }
}



class FileFilter {
    private final List<FilterCondition> filters = new ArrayList<>();

    public FileFilter() {
      filters.add(new ImageFilter());
      filters.add(new MinSizeFilter());
    }

    public boolean isValid(FilterParams params, HadronMemoryNode node) {
      for (FilterCondition filter : filters) {
        if (!filter.apply(params, node)) {
          return false;
        }
      }
      return true;
    }
}


class HadronFS {
    HadronMemoryNode root;
    HadronFS() {
        this.root = new Directory("", null);
    }

    boolean exists(String path) {
        // /home/john/
        if (path == null) return true;
        String[] nodes = path.split("/");
        HadronMemoryNode curr = root;
        for(int i = 0; i < nodes.length; i++){
            if(!nodes[i].equals("")){
                if(curr!=null && curr.children != null && curr.children.containsKey(nodes[i])){
                    curr = curr.children.get(nodes[i]);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    void mkdir(String path) throws IllegalArgumentException {
        System.out.println("mkdir " + path);
        if(path == null) return;
        String[] nodes = path.split("/"); // /home
        HadronMemoryNode curr = root;
        int i = 0;
        while(curr!= null && curr.isDirectory() && i < nodes.length) {
            if(!nodes[i].equals("")) {
                if(curr.children.containsKey(nodes[i])){
                    curr = curr.children.get(nodes[i]);
                } else {
                    break;
                }
            }
            i++;
        }

        if(i == nodes.length) return;
        
        if(i < nodes.length && !curr.isDirectory()){
            throw new IllegalArgumentException(curr.name + " already exists!");
        } 
        
        
        while(i < nodes.length){
            ((Directory) curr).addChild(new Directory(nodes[i++], curr));
            
        }
    }


    Directory getDir(String path) throws IllegalArgumentException {
        if(!exists(path)){
            throw new IllegalArgumentException(path + " doesn't exist!"); 
        }
        String[] nodes = path.split("/");
        HadronMemoryNode curr = root;

        for(int i = 0; i < nodes.length; i++){
            if(!nodes[i].equals("")){
                if(curr!=null && curr.children != null && curr.children.containsKey(nodes[i])){
                    curr = curr.children.get(nodes[i]);
                }
            }

        }
        return (Directory) curr;
    }

    void createFile(String filePath, String fileName, Long fileSize, FileExtension ext, String content) throws IllegalArgumentException {

        Directory node = getDir(filePath);
        node.addChild(new File(fileName, node, fileSize, ext, content));
    }

    void ls(String path){
        System.out.println("ls " + path);
        if(path == null) return;
        Directory node = getDir(path);
        // System.out.println();
        for(String child: node.children.keySet()){
            System.out.print(child + " ");
        }
        System.out.println();
    }

}


public class Main {
    public static void main(String[] args) {
        
        HadronFS fs = new HadronFS();
        fs.mkdir("/home/");
        fs.ls("/");
        fs.mkdir("/home/darwin");
        fs.mkdir("/home/newton");
        fs.mkdir("/home/einstein");
        fs.ls("/");
        fs.ls("/home");
        fs.createFile("/home/einstein/", "sample.txt", 1L, FileExtension.txt, "Hello World");
        
    }
}
