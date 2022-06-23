package com.example.demo.mypage.cafe.controller;

import com.example.demo.mypage.cafe.dto.CafeMenuDto;
import com.example.demo.mypage.cafe.entity.CafeMenu;
import com.example.demo.mypage.cafe.service.menu.MenuService;
import com.example.demo.review.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class MenuController {

    @Autowired
    MenuService service;

    @ResponseBody
    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String menuRegister(@RequestPart(value = "info", required = false) CafeMenuDto info,
                             @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {
        log.info("menu info : " + info);
        log.info("file name: " + fileList);


        if(fileList != null) {
            try{
                for(MultipartFile multipartFile: fileList) {
                    String fileName = info.getCafe_no() + "." + multipartFile.getOriginalFilename();
                    log.info("requestUploadFile() - Make file: " +
                            multipartFile.getOriginalFilename());
                    FileOutputStream writer = new FileOutputStream(
                            "../../cafefront/around_cafe/src/assets/cafe/cafeMenu/"  + fileName);
                    log.info("save complete!");

                    writer.write(multipartFile.getBytes());
                    writer.close();
                    service.includeImgSave(info, fileName);
                }

            } catch (Exception e) {
                return "review fail!";
            }
        }else if(fileList == null) {
            service.exceptImgSave(info);
        }

        return "메뉴 등록을 성공했습니다!";
    }

    @ResponseBody
    @PutMapping(value = "/modify/{cafeNo}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String menuModify(@PathVariable("cafeNo") Integer cafeNo,
                               @RequestPart(value = "info", required = false) CafeMenu info,
                               @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        log.info("cafe name : " + cafeNo);
        log.info("cafe info : " +info);
        log.info("file name : " +fileList);

        if(fileList != null) {
            try{
                for(MultipartFile multipartFile : fileList) {
                    String fileName = cafeNo + "." + multipartFile.getOriginalFilename();

                    FileOutputStream writer = new FileOutputStream(
                            "../../cafefront/around_cafe/src/assets/cafe/cafeMenu/" + fileName);

                    writer.write(multipartFile.getBytes());
                    writer.close();
                    service.includeImgModify(info, fileName, cafeNo);
                }
            } catch (Exception e) {
                return "modify is failed.";
            }
        }else if (fileList == null) {
            service.exceptImgModify(info, cafeNo);
        }

        log.info("modify is complete");
        return "modify complete!!";
    }

    @GetMapping("/list/{membNo}")
    public List<CafeMenu> menuList(@PathVariable("membNo") Integer membNo) {
        log.info("get menu list, member no: " +membNo);

        return service.list(membNo);
    }

    @GetMapping("/list")
    public List<CafeMenu> menuList1() {
        log.info("get menu list");

        return service.list1();
    }

    @GetMapping("/signatureList")
    public List<CafeMenu> signatureList() {
        log.info("get menu list");

        return service.sigList();
    }

    @GetMapping("/soldOutList")
    public List<CafeMenu> soldOutList() {
        log.info("get menu list");

        return service.soldList();
    }

    @DeleteMapping("/delete/{menuNo}")
    public void deleteMenu (@PathVariable("menuNo") Integer menuNo) throws IOException {
        log.info("delete no : "+menuNo);

        service.delete(menuNo);
    }

    @PostMapping("/changeSignature/{menuNo}")
    public String registerSignature(@PathVariable("menuNo") Integer menuNo) {
        log.info("register signature");
        return service.changeSignature(menuNo);
    }

    @PostMapping("/delSignature/{menuNo}")
    public String deleteSignature(@PathVariable("menuNo") Integer menuNo) {
        log.info("delete signature");
        return service.deleteSignature(menuNo);
    }

    @PostMapping("/changeSoldOut/{menuNo}")
    public String registerSoldOut(@PathVariable("menuNo") Integer menuNo) {
        log.info("register sold out");
        return service.changeSoldOut(menuNo);
    }

    @PostMapping("/delSoldOut/{menuNo}")
    public String deleteSoldOut(@PathVariable("menuNo") Integer menuNo) {
        log.info("delete sold out");
        return service.deleteSoldOut(menuNo);
    }

    @GetMapping("/findMenu/{cafe_no}/{cafe_name}")
    public List<CafeMenu> findMenu(@PathVariable("cafe_no")Integer cafe_no,
                                   @PathVariable("cafe_name")String cafe_name){
        log.info("find menu cafeNo -> " + cafe_no +" cafe name _" + cafe_name);
        return service.findMenu(cafe_no, cafe_name);
    }

}
