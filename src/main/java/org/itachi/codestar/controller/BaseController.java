package org.itachi.codestar.controller;

import org.itachi.codestar.domain.Pager;
import org.itachi.codestar.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by kyo on 2017/5/6.
 *
 * @author itachi
 */
public abstract class BaseController {
    private static final String ENCODE_UTF8 = "UTF-8";

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpServletRequest request;

    protected Map<String, String> getQueryParameters() {
        return Utils.getQueryParameters(request);
    }

    Pager buildPager(Integer page, Integer rows) {
        Pager pager = new Pager();
        pager.setPage(page == null ? 1 : page);
        pager.setRows(rows == null ? 10 : rows);
        return pager;
    }
}
