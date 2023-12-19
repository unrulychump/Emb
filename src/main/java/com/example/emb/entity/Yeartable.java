package com.example.emb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Worktable对象", description="")
public class Yeartable {
    private static final long serialVersionUID = 1L;
    @TableField("years")
    private String years;
}
