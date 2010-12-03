package net.pshared.sbmcounter.util;

public class MD5 extends Digest {

    private static MD5 instance = new MD5();

    protected MD5() {
        super("MD5");
    }

    public static MD5 getInstance() {
        return instance;
    }

}
