package com.bonree.brfs.schedulers.utils;

import com.bonree.brfs.common.files.impl.BRFSDogFoodFilter;
import com.bonree.brfs.common.utils.BRFSFileUtil;
import com.bonree.brfs.common.utils.BRFSPath;
import com.bonree.brfs.duplication.storageregion.StorageRegion;
import com.bonree.brfs.rebalance.route.SecondIDParser;
import com.bonree.brfs.server.identification.ServerIDManager;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BRFSDogFoodsFilter extends BRFSDogFoodFilter{
    private ServerIDManager sim = null;
    private SecondIDParser parser = null;
    private StorageRegion  region = null;
    private long lastTime = 0;

    public BRFSDogFoodsFilter(ServerIDManager sim, SecondIDParser parser, StorageRegion storageRegion, long lastTime){
        super();
        this.sim = sim;
        this.parser = parser;
        this.parser.updateRoute();
        this.region = storageRegion;
        this.lastTime = lastTime;
    }
    @Override
    public String getKey(int index){
        return keyMap.get(index);
    }

    @Override
    public boolean isDeep(int index, Map<String, String> values){
        if(isBug(values, false)){
           return false;
        }
        if(values.size() == keyMap.size() -1){
            long tmp = BRFSPath.convertTime(values);
            return tmp < lastTime;
        }
        return true;
    }

    @Override
    public boolean isAdd(String root, Map<String, String> values, boolean isFile){

        if(isBug(values, isFile)){
            return true;
        }
        if(values.size() != keyMap.size()){
            return false;
        }
        //判断是否为本机该存在的
        String tmpRegion = values.get(BRFSPath.STORAGEREGION);
        if(!region.getName().equals(tmpRegion)){
            return false;
        }
        String fileName = values.get(BRFSPath.FILE);
        if(fileName.contains(".")){
            return true;
        }
        String secondStorage = sim.getSecondServerID(region.getId());
        List<String> secondIds = FileCollection.analyseServices(fileName, parser);
        return FileCollection.crimeFile(secondIds,secondStorage);
    }

}
