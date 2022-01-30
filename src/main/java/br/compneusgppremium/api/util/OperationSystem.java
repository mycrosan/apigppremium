package br.compneusgppremium.api.util;

public class OperationSystem {
    private String getOperatingSystem() {
        String os = System.getProperty("os.name");
        System.out.println("Using System Property: " + os);
        return os;
    }

    public String placeImageSystem() {
        var os = this.getOperatingSystem();
        String caminho;
        if("Mac OS X".equals(os)){
             caminho = "/Users/sandy/Pictures/carcaca/";
        }else if ("Windows".equals(os)){
             caminho = "C://";
        }else {
             caminho = "/home/sandy/imagens/carcaca/";
        }
        return caminho;
    }
}
