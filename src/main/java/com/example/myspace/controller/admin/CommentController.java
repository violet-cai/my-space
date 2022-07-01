package com.example.myspace.controller.admin;

import com.example.myspace.service.CommentService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.ResultGeneratorUtil;
import com.example.myspace.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/comments/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGeneratorUtil.genSuccessResult(commentService.getCommentPage(pageUtil));
    }

    @PostMapping("/comments/checkDone")
    @ResponseBody
    public ResultUtil checkDone(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (commentService.checkDone(ids)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("审核失败");
        }
    }

    @PostMapping("/comments/reply")
    @ResponseBody
    public ResultUtil checkDone(@RequestParam("commentId") Long commentId, @RequestParam("replyBody") String replyBody) {
        if (commentId == null || commentId < 1 || StringUtils.isEmpty(replyBody)) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (commentService.reply(commentId, replyBody)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("回复失败");
        }
    }

    @PostMapping("/comments/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (commentService.deleteBatch(ids)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("刪除失败");
        }
    }

    @GetMapping("/comments")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "comments");
        return "admin/comment";
    }
}
