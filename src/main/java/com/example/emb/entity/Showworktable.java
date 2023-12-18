package com.example.emb.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author KLE1NBLUE&LackOxy
 * @since 2023-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Showworktable对象", description="")
public class Showworktable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("workName")
    private String workName;

    @TableField("workId")
    private Integer workId;

    @TableField("modifiedTime")
    private Date modifiedTime;

    @TableField("classId")
    private Integer classId;

    @TableField("className")
    private String className;

    @TableField("isFinal")
    private Integer isFinal;

    private String source;

    private String video;

    @TableField("workDoc")
    private String workDoc;


}
