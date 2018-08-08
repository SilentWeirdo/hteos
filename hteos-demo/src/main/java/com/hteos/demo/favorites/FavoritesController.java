package com.hteos.demo.favorites;

import com.hteos.framework.core.protocol.Result;
import com.hteos.framework.web.controller.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIQIU
 * @date 2018-7-17
 **/
@RestController("/favorite")
public class FavoritesController extends BaseController {

    public Result<String> add(String id) {
        return this.success();
    }

    public Result<String> remove(String id) {
        return this.success();
    }
}
