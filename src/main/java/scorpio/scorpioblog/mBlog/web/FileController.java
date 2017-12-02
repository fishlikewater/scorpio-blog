package scorpio.scorpioblog.mBlog.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import scorpio.utils.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "文件操作类", description = "zhanx")
public class FileController {

    /**
     * 文件上传
     * @param file
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @ApiOperation(value = "文件上传接口", notes = "文件上传入口")
    @PostMapping("/admin/upload")
    public String sync(
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {


        if (request.getParameter("chunk") == null) {

            //this.getClass().getClassLoader().getResource("/upload").getPath();
            String realPath =  this.getClass().getResource("/static/images/randimage").getPath();

            String fileName = file.getOriginalFilename();
            fileName = UUIDUtils.get()+fileName.substring(fileName.lastIndexOf("."), fileName.length());
            File targetFile = new File(realPath, fileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            file.transferTo(targetFile); // 小文件，直接拷贝

            return "";
        } else {
            int chunk = Integer.parseInt(request.getParameter("chunk")); // 当前分片
            int chunks = Integer.parseInt(request.getParameter("chunks")); // 分片总计

            String realPath =  this.getClass().getResource("/static/images/randimage").getPath();


            String Ogfilename = file.getOriginalFilename();

            File tempFile = new File(realPath, Ogfilename);
            OutputStream outputStream = new FileOutputStream(tempFile, true);
            InputStream inputStream = file.getInputStream();

            byte buffer[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            return "";
        }
    }


    /**
     * 图片预览
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admin/files")
    public JSONObject getFiles(@RequestParam("page") Integer page,
                               @RequestParam(value = "limit", defaultValue = "10") Integer limit){

        String path = this.getClass().getResource("/static/images/randimage").getPath();
        File file = new File(path);
        String[] list = file.list();
        Integer count  = list.length;
        int totalPage = count%limit==0?count/limit:count/limit+1;
        int begin = (page-1)*limit;
        int end = (page-1)*limit+limit;
        if(end>count){
            end = count;
        }
        List returnList = new ArrayList();
        for (int i = end-1; i >= begin; i--) {
            returnList.add("/images/randimage/" + list[i]);

        }
        JSONObject obj = new JSONObject();
        obj.put("total", totalPage);
        obj.put("list", returnList);
        return obj;

    }

    @PostMapping("admin/file/del")
    public void delFile(String fileName){
        String path = this.getClass().getResource("/static/").getPath();
        File file = new File(path + fileName);
        if (file.exists()){
            file.delete();
        }else{
            throw new RuntimeException("文件不存在");
        }

    }

}
