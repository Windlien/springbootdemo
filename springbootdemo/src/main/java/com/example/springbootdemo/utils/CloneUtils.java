package com.example.springbootdemo.utils;

import org.apache.log4j.Logger;

import java.io.*;

public class CloneUtils {
    private static final Logger logger = Logger.getLogger(DateUtil.class);
     public static <T extends Serializable> T clone(T obj){
        T cloneObj = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            logger.error("CloneUtils.clone", e);
        }
        return cloneObj;
     }
}
