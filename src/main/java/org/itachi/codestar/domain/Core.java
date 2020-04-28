package org.itachi.codestar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 柱状表
 * Created by itachi on 2018/3/5.
 * User: itachi
 * Date: 2018/3/5
 * Time: 19:38
 *
 * @author itachi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Core implements Serializable {
    private String name;
    private String type;
    private int[] data;

}
