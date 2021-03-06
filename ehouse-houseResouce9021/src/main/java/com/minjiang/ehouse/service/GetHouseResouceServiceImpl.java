package com.minjiang.ehouse.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minjiang.ehouse.dao.HouseResouceDao;
import com.minjiang.ehouse.dto.*;
import com.minjiang.ehouse.entities.Result;
import com.minjiang.ehouse.entities.ResultCode;
import com.minjiang.ehouse.entities.house.*;
import com.minjiang.ehouse.entities.user.User;
import com.minjiang.ehouse.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @auther guannw
 * @create 2021/3/21 15:22
 */

@Slf4j
@Service
@PropertySource(value = "classpath:ftp.properties")
public class GetHouseResouceServiceImpl implements GetHouseResouceService {

    @Autowired
    private HouseResouceDao houseResouceDao;

    @Autowired
    private UserService userService;

    @Value("#{'${ftpURL}'}")
    public String ftpURL;

    @Value("#{'${ftpPort}'}")
    public String ftpPort;

    @Value("#{'${ftpUserName}'}")
    public String ftpUserName;

    @Value("#{'${ftpPassword}'}")
    public String ftpPassword;

    @Value("#{'${uploadURL}'}")
    public String uploadURL;

    @Autowired
    private IdWorker idWorker;

    private String URLHTTP = "http://";


    private Result result;

    public Result getCityArea() {

        List<City> cityList = houseResouceDao.getAllCityArea();
        result = new Result(ResultCode.SUCCESS);
        result.setData(cityList);
        return result;
    }

    public Result getHouseType() {
        List<HouseType> houseTypeList = houseResouceDao.getAllHouseType();
        result = new Result(ResultCode.SUCCESS);
        result.setData(houseTypeList);
        return result;
    }

    public Result getEnvironment() {
        List<HouseEnvironment> houseEnvironmentList = houseResouceDao.getHouseEnvironment();
        result = new Result(ResultCode.SUCCESS);
        result.setData(houseEnvironmentList);
        return result;
    }

    @Transactional
    public Result houseRelease(HouseReleaseForm houseReleaseForm, MultipartFile file, MultipartFile[] files) throws IOException {
        Boolean flag = false;
        User user = new User();
        Map<String,Long> record = new HashMap();
        List<String> photos = new ArrayList<String>();
        String videoAddr="";
        user.setUsername(houseReleaseForm.getUsername());
        //查询用户信息，得到用户id
        result = userService.selectUserDetail(user);
        if(result.getCode() == 200 ){
            //查询成功
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            String jsonString = gson.toJson(result.getData());
            user  = gson.fromJson(jsonString,User.class);
            String id = user.getId();
            System.out.println(user.getId());
            FTPClient ftpClient = new FTPClient();

            ftpClient.connect(ftpURL,Integer.parseInt(ftpPort));//服务器地址和端口
            ftpClient.login(ftpUserName,ftpPassword);//登录的用户名和密码
            if (!flag) {//不存在文件夹
                ftpClient.makeDirectory(uploadURL + "/" + id);
                ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
                flag =true;
            }
            //读取本地文件，给出的是本地文件地址
            File fileTemp = null;
            for(MultipartFile multipartFile:files){
                try {
                    String fileNameTemp = multipartFile.getOriginalFilename();
                    String suffixNameTemp = fileNameTemp.substring(fileNameTemp.lastIndexOf("."));
                    fileTemp = transferToFile(multipartFile);
                    log.info("文件大小："+fileTemp.length());
                    FileInputStream inputStreamTemp = new FileInputStream(fileTemp);
                    if(!flag){//不存在文件夹
                        ftpClient.makeDirectory(uploadURL+"/"+id);
                        ftpClient.changeWorkingDirectory(uploadURL+"/"+id);
                        flag =true;
                    }
                    //设置上传路径
                    ftpClient.changeWorkingDirectory(uploadURL+"/"+id);
                    //设置文件类型
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    String filenameTemp = idWorker.nextId()+"";
                    photos.add(URLHTTP+ftpURL+"/"+id+"/"+filenameTemp+suffixNameTemp);
                    ftpClient.storeFile(filenameTemp+suffixNameTemp,inputStreamTemp);
                    record.put(filenameTemp+suffixNameTemp,fileTemp.length());
                    log.info("已经传输");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(file != null) {
                String fileName = file.getOriginalFilename();
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                try {
                    fileTemp = transferToFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FileInputStream inputStream = new FileInputStream(fileTemp);

                if (!flag) {//不存在文件夹
                    ftpClient.makeDirectory(uploadURL + "/" + id);
                    ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
                    flag =true;
                }
                //设置上传路径
                ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
                //设置文件类型
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                //1.服务器端保存的文件名，2.上传文件的inputstream
                String filename = idWorker.nextId() + "";
                try {
                    ftpClient.storeFile(filename + suffixName, inputStream);
                    record.put(filename+suffixName,fileTemp.length());
                    videoAddr = URLHTTP+ftpURL+"/"+id+"/"+filename+suffixName;
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
            log.info("已经传输");
            //判断上传文件大小是否符合
            boolean fileUpload = true;
            if(file !=null || files.length != 0){
                FTPFile[] fileList = null;
                fileList = ftpClient.listFiles(uploadURL+id);
                for (FTPFile ftpfile : fileList){
                    if(record.containsKey(ftpfile.getName())){
                        if(record.get(ftpfile.getName()) != ftpfile.getSize()){
                            ftpClient.logout();
                            return result =new Result(500,"发布失败，文件上传失败",false);
                        }
                    }
                }
            }
            ftpClient.logout();
            //保存发布信息

            //插入房源发布主表
            String mainId = idWorker.nextId()+"";
            HouseRelease houseRelease = new HouseRelease();
            houseRelease.setId(mainId);
            houseRelease.setUsername(houseReleaseForm.getUsername());
            houseRelease.setBiz_type(houseReleaseForm.getBizType());
            houseRelease.setResouce_title(houseReleaseForm.getHouse_title());
            houseRelease.setResouce_desc(houseReleaseForm.getHouse_desc());
            houseRelease.setCity(houseReleaseForm.getHouse_city());
            houseRelease.setArea(houseReleaseForm.getHouse_area());
            houseRelease.setHouse_type(houseReleaseForm.getHouse_type());
            houseRelease.setPrice(houseReleaseForm.getHouse_price());
            houseRelease.setCreate_time(new Date());
            houseRelease.setStatus(true);
            houseResouceDao.saveHouseRelease(houseRelease);
            //保存房源环境
            List<HouseEnvirs> houseEnvirsList = new ArrayList<HouseEnvirs>();
            for (String envir : houseReleaseForm.getHouse_envir()){
                HouseEnvirs houseEnvirs = new HouseEnvirs();
                houseEnvirs.setId(idWorker.nextId()+"");
                houseEnvirs.setEnvir_name(envir);
                houseEnvirs.setRelease_id(mainId);
                houseEnvirs.setUsername(houseReleaseForm.getUsername());
                houseEnvirsList.add(houseEnvirs);
            }
            if(houseEnvirsList.size() != 0){
                houseResouceDao.saveReleaseEnvirs(houseEnvirsList);
            }
            //保存房源图片信息
            List<ReleasePhotos> releasePhotosList = new ArrayList<ReleasePhotos>();
            for (String photoAddr : photos){
                ReleasePhotos releasePhotos = new ReleasePhotos();
                releasePhotos.setId(idWorker.nextId()+"");
                releasePhotos.setPhoto_addr(photoAddr);
                releasePhotos.setRelease_id(mainId);
                releasePhotos.setUsername(houseReleaseForm.getUsername());
                releasePhotosList.add(releasePhotos);
            }
            if(releasePhotosList.size() != 0){
                houseResouceDao.saveReleasePhotos(releasePhotosList);
            }
            //保存房源视频信息
            if(file != null){
                ReleaseVideo releaseVideo = new ReleaseVideo();
                releaseVideo.setId(idWorker.nextId()+"");
                releaseVideo.setRelease_id(mainId);
                releaseVideo.setVideo_addr(videoAddr);
                releaseVideo.setUsername(houseReleaseForm.getUsername());
                houseResouceDao.saveReleaseVideo(releaseVideo);
            }

            result = new Result(200,"发布成功",true);
            //上传成功
        }else {
            return  result = new Result(500,"用户不存在",false);
        }
        return result;
    }

    public Result getAllAreaByCity(GetAllAreaByCity getAllAreaByCity) {
        List<Area> areaList = new ArrayList<Area>();
        areaList = houseResouceDao.getAllAreaByCity(getAllAreaByCity);
        result = new Result(ResultCode.SUCCESS);
        result.setData(areaList);
        return result;
    }

    public Result getAllCity() {
        List<AllCity> cityList = houseResouceDao.getAllCity();
        result = new Result(ResultCode.SUCCESS);
        result.setData(cityList);
        return result;
    }

    public Result selectHouseByOption(SelectHouseOption selectHouseOption) {
        List<Object> objectList= houseResouceDao.selectHouseByOption(selectHouseOption);
        List<HouseResouceList> houseResouceLists = ((List<HouseResouceList>)objectList.get(0));
        Integer total = ((List<Integer>) objectList.get(1)).get(0);
        SelectHouseDTO selectHouseDTO = new SelectHouseDTO();
        selectHouseDTO.setCount(total);
        selectHouseDTO.setHouseResouceListList(houseResouceLists);
        result = new Result(ResultCode.SUCCESS);
        result.setData(selectHouseDTO);
        return result;
    }

    public Result getHouseResouceById(GetHouseResouceForm getHouseResouceForm) {
        HouseResouceList houseResouceList = houseResouceDao.getHouseResouceById(getHouseResouceForm);
        result = new Result(ResultCode.SUCCESS);
        result.setData(houseResouceList);
        return result;
    }

    //收藏
    @Transactional
    public Result houseIsCollection(HouseCollectionForm houseCollectionForm){
        //判断是否已经被收藏
        HouseCollectionForm houseCollectionFormTemp = houseResouceDao.getCollectionStatus(houseCollectionForm);
        //如果已经被收藏不做任何操作
        if(houseCollectionFormTemp == null){
            //如果没被收藏，数据库中没有保存信息，添加收藏信息
            houseCollectionForm.setId(idWorker.nextId()+"");
            houseCollectionForm.setCollection_time(new Date());
            houseResouceDao.saveHouseCollection(houseCollectionForm);
            result = new Result(ResultCode.SUCCESS);
            result.setData(houseCollectionForm);
            return result;
        }else {
            //数据库中有保存信息，修改保存信息
            if(houseCollectionFormTemp.getIsCollection()){
                //如果已经被收藏
                result = new Result(ResultCode.SUCCESS);
                result.setData(houseCollectionForm);
                return result;
            }else {
                //有信息，但是没被收藏修改收藏信息
                houseCollectionForm.setCollection_time(new Date());
                houseResouceDao.updateHouseCollection(houseCollectionForm);
                result = new Result(ResultCode.SUCCESS);
                result.setData(houseCollectionForm);
                return result;
            }
        }

    }


    //取消收藏
    @Transactional
    public Result cancleCollection(HouseCollectionForm houseCollectionForm){
        //修改收藏信息
        houseResouceDao.updateHouseCollection(houseCollectionForm);
        result = new Result(ResultCode.SUCCESS);
        result.setData(houseCollectionForm);
        return result;
    }
    //获取收藏状态
    @Transactional
    public Result getCollectionStatus(HouseCollectionForm houseCollectionForm){
        HouseCollectionForm houseCollectionFormTemp = houseResouceDao.getCollectionStatus(houseCollectionForm);
        if(houseCollectionFormTemp == null){
            houseCollectionForm.setIsCollection(false);
            result = new Result(ResultCode.SUCCESS);
            result.setData(houseCollectionForm);
            return result;
        }else {
            result = new Result(ResultCode.SUCCESS);
            result.setData(houseCollectionFormTemp);
            return result;
        }
    }

    public Result getMyReleaseHouse(HouseMyReleaseForm houseMyReleaseForm) {
        List<Object> objectList= houseResouceDao.getMyReleaseHouse(houseMyReleaseForm);
        List<HouseResouceList> houseResouceLists = ((List<HouseResouceList>)objectList.get(0));
        Integer total = ((List<Integer>) objectList.get(1)).get(0);
        SelectHouseDTO selectHouseDTO = new SelectHouseDTO();
        selectHouseDTO.setCount(total);
        selectHouseDTO.setHouseResouceListList(houseResouceLists);
        result = new Result(ResultCode.SUCCESS);
        result.setData(selectHouseDTO);
        return result;
    }
    //删除房源
    @Transactional
    public Result delMyReleaseHouse(DeleteHouseReleaseForm deleteHouseReleaseForm){
        int flag = houseResouceDao.delMyReleaseHouse(deleteHouseReleaseForm);
        if(flag != -1 ){
            result = new Result(ResultCode.SUCCESS);

        }else {
            result = new Result(ResultCode.FAIL);
        }
        return result;
    }

    public Result selectMyCollection(SelectMyCollectionForm selectMyCollectionForm){
        List<Object> objectList= houseResouceDao.selectMyCollection(selectMyCollectionForm);
        List<HouseResouceList> houseResouceLists = ((List<HouseResouceList>)objectList.get(0));
        Integer total = ((List<Integer>) objectList.get(1)).get(0);
        SelectHouseDTO selectHouseDTO = new SelectHouseDTO();
        selectHouseDTO.setCount(total);
        selectHouseDTO.setHouseResouceListList(houseResouceLists);
        result = new Result(ResultCode.SUCCESS);
        result.setData(selectHouseDTO);
        return result;
    }
    //修改房源信息
    @Transactional
    public Result editHouseRelease(HouseReleaseForm houseReleaseForm, MultipartFile file, MultipartFile[] files) throws IOException {
        Boolean flag = false;
        User user = new User();
        Map<String,Long> record = new HashMap();
        List<String> photos = new ArrayList<String>();
        List<String> filenameTemps = new ArrayList<String>();
        List<FileInputStream> fileInputStreams = new ArrayList<FileInputStream>();
        String videoAddr="";
        user.setUsername(houseReleaseForm.getUsername());

        //查询用户信息，得到用户id
        result = userService.selectUserDetail(user);
        if(result.getCode() == 200 ){
            //查询成功
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
            String jsonString = gson.toJson(result.getData());
            user  = gson.fromJson(jsonString,User.class);
            String id = user.getId();
            System.out.println(user.getId());
            FTPClient ftpClient = new FTPClient();

            ftpClient.connect(ftpURL,Integer.parseInt(ftpPort));//服务器地址和端口
            ftpClient.login(ftpUserName,ftpPassword);//登录的用户名和密码

            if (!flag) {//不存在文件夹
                ftpClient.makeDirectory(uploadURL + "/" + id);
                ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
                flag =true;
            }
            File fileTemp = null;
            for(MultipartFile multipartFile: files ){
                try {
                    String fileNameTemp = multipartFile.getOriginalFilename();
                    String suffixNameTemp = fileNameTemp.substring(fileNameTemp.lastIndexOf("."));
                    fileTemp = transferToFile(multipartFile);
                    FileInputStream inputStreamTemp = new FileInputStream(fileTemp);
                    fileInputStreams.add(inputStreamTemp);
                    log.info("文件大小："+fileTemp.length());
                    if(!flag){//不存在文件夹
                        ftpClient.makeDirectory(uploadURL+"/"+id);
                        ftpClient.changeWorkingDirectory(uploadURL+"/"+id);
                        flag =true;
                    }
                    String filenameTemp = idWorker.nextId()+"";
                    filenameTemps.add(filenameTemp);
                    photos.add(URLHTTP+ftpURL+"/"+id+"/"+filenameTemp+suffixNameTemp);
                    record.put(filenameTemp+suffixNameTemp,fileTemp.length());
                    log.info("已经传输");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //开始传输图片文件
            Future<String> photoTrans = photoTransmission(ftpClient,id,filenameTemps,fileInputStreams,files);
            //开始传输视频文件
            Future<String> videoTrans = null;
            if(file != null) {
                String fileName = file.getOriginalFilename();
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                try {
                    fileTemp = transferToFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!flag) {//不存在文件夹
                    ftpClient.makeDirectory(uploadURL + "/" + id);
                    ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
                    flag =true;
                }
                //1.服务器端保存的文件名，2.上传文件的inputstream
                String filename = idWorker.nextId() + "";
                record.put(filename+suffixName,fileTemp.length());
                videoAddr = URLHTTP+ftpURL+"/"+id+"/"+filename+suffixName;
                FileInputStream inputStream = new FileInputStream(fileTemp);
                videoTrans = videoTransmission(ftpClient,id,filename,inputStream,file);
            }
            //是否有传输视频
            if(file != null){
                //有传输视频
                for (;;){
                    if(photoTrans.isDone() && videoTrans.isDone()){
                        break;
                    }
                }

            }else {
                //没有传输视频
                for (;;){
                    if(photoTrans.isDone()){
                        break;
                    }
                }
            }
            log.info("所有文件传输完毕");
            boolean fileUpload = true;
            if(file !=null || files.length != 0){
                FTPFile[] fileList = null;
                fileList = ftpClient.listFiles(uploadURL+id);
                for (FTPFile ftpfile : fileList){
                    if(record.containsKey(ftpfile.getName())){
                        if(record.get(ftpfile.getName()) != ftpfile.getSize()){
                            ftpClient.logout();
                            return result =new Result(500,"发布失败，文件上传失败",false);
                        }
                    }
                }
            }
            ftpClient.logout();
            //保存发布信息

            //插入房源发布主表
            String mainId = idWorker.nextId()+"";
            HouseRelease houseRelease = new HouseRelease();
            houseRelease.setId(mainId);
            houseRelease.setUsername(houseReleaseForm.getUsername());
            houseRelease.setBiz_type(houseReleaseForm.getBizType());
            houseRelease.setResouce_title(houseReleaseForm.getHouse_title());
            houseRelease.setResouce_desc(houseReleaseForm.getHouse_desc());
            houseRelease.setCity(houseReleaseForm.getHouse_city());
            houseRelease.setArea(houseReleaseForm.getHouse_area());
            houseRelease.setHouse_type(houseReleaseForm.getHouse_type());
            houseRelease.setPrice(houseReleaseForm.getHouse_price());
            houseRelease.setCreate_time(new Date());
            houseRelease.setStatus(true);
            houseResouceDao.saveHouseRelease(houseRelease);
            //保存房源环境
            List<HouseEnvirs> houseEnvirsList = new ArrayList<HouseEnvirs>();
            for (String envir : houseReleaseForm.getHouse_envir()){
                HouseEnvirs houseEnvirs = new HouseEnvirs();
                houseEnvirs.setId(idWorker.nextId()+"");
                houseEnvirs.setEnvir_name(envir);
                houseEnvirs.setRelease_id(mainId);
                houseEnvirs.setUsername(houseReleaseForm.getUsername());
                houseEnvirsList.add(houseEnvirs);
            }
            if(houseEnvirsList.size() != 0){
                houseResouceDao.saveReleaseEnvirs(houseEnvirsList);
            }
            //保存房源图片信息
            List<ReleasePhotos> releasePhotosList = new ArrayList<ReleasePhotos>();
            for (String photoAddr : photos){
                ReleasePhotos releasePhotos = new ReleasePhotos();
                releasePhotos.setId(idWorker.nextId()+"");
                releasePhotos.setPhoto_addr(photoAddr);
                releasePhotos.setRelease_id(mainId);
                releasePhotos.setUsername(houseReleaseForm.getUsername());
                releasePhotosList.add(releasePhotos);
            }
            //使用原始图片数据
            if(houseReleaseForm.getRelease_photos() != null){
                for (String photoAddr : houseReleaseForm.getRelease_photos()){
                    ReleasePhotos releasePhotos = new ReleasePhotos();
                    releasePhotos.setId(idWorker.nextId()+"");
                    releasePhotos.setPhoto_addr(photoAddr);
                    releasePhotos.setRelease_id(mainId);
                    releasePhotos.setUsername(houseReleaseForm.getUsername());
                    releasePhotosList.add(releasePhotos);
                }
            }

            if(releasePhotosList.size() != 0){
                houseResouceDao.saveReleasePhotos(releasePhotosList);
            }
            //保存房源视频信息
            if(file != null){
                ReleaseVideo releaseVideo = new ReleaseVideo();
                releaseVideo.setId(idWorker.nextId()+"");
                releaseVideo.setRelease_id(mainId);
                releaseVideo.setVideo_addr(videoAddr);
                releaseVideo.setUsername(houseReleaseForm.getUsername());
                houseResouceDao.saveReleaseVideo(releaseVideo);
            }
            //依旧使用原始视频
            if (houseReleaseForm.getVideo_addr() != null){
                ReleaseVideo releaseVideo = new ReleaseVideo();
                releaseVideo.setId(idWorker.nextId()+"");
                releaseVideo.setRelease_id(mainId);
                releaseVideo.setVideo_addr(houseReleaseForm.getVideo_addr());
                releaseVideo.setUsername(houseReleaseForm.getUsername());
                houseResouceDao.saveReleaseVideo(releaseVideo);
            }
            //删除原有房源信息表
            DeleteHouseReleaseForm deleteHouseReleaseForm = new DeleteHouseReleaseForm();
            deleteHouseReleaseForm.setRelease_id(houseReleaseForm.getRelease_id());
            deleteHouseReleaseForm.setUsername(houseReleaseForm.getUsername());
            houseResouceDao.delMyReleaseHouse(deleteHouseReleaseForm);

            result = new Result(200,"发布成功",true);
            //上传成功

        }else {
            return  result = new Result(500,"用户不存在",false);
        }

        return result;
    }


    @Async //异步传输图片文件
    public Future<String> photoTransmission(FTPClient ftpClient,String id,List<String> filenameTemps,List<FileInputStream> fileInputStreams,MultipartFile[] files){

        int index =0;
        for(MultipartFile multipartFile: files ){
            try {
                String fileNameTemp = multipartFile.getOriginalFilename();
                String suffixNameTemp = fileNameTemp.substring(fileNameTemp.lastIndexOf("."));

                //设置上传路径
                ftpClient.changeWorkingDirectory(uploadURL+"/"+id);
                //设置文件类型
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                String filenameTemp = filenameTemps.get(index);
                ftpClient.storeFile(filenameTemp+suffixNameTemp,fileInputStreams.get(index));
                index++;
                log.info("已经传输");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new AsyncResult<String>(String.valueOf("已经传输"));
    }
    @Async //异步传输视频文件
    public Future<String> videoTransmission(FTPClient ftpClient,String id,String filename,FileInputStream inputStream,MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置上传路径
        ftpClient.changeWorkingDirectory(uploadURL + "/" + id);
        //设置文件类型
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //1.服务器端保存的文件名，2.上传文件的inputstream
        try {
            ftpClient.storeFile(filename + suffixName, inputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new AsyncResult<String>("已经传输");
    }

    private File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {

            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            filename[0] = filename[0]+ UUID.randomUUID();
            file=File.createTempFile(filename[0],filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
