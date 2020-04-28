package org.itachi.codestar.domain;

import java.io.Serializable;

/**
 * Created by itachi on 2018/1/15.
 * User: itachi
 * Date: 2018/1/15
 * Time: 16:05
 *
 * @author itachi
 */
public class Pager implements Serializable {
    private int page = 1;
    private int rows = 10;
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        if (page < 1) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(final int rows) {
        if (rows < 1) {
            this.rows = 10;
            return;
        }
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        if (total < 0) {
            this.total = 0;
            return;
        }
        this.total = total;
    }

    public int getSize() {
        if (rows < 1) {
            return 0;
        }
        return total / rows + (total % rows == 0 ? 0 : 1);
    }
}
